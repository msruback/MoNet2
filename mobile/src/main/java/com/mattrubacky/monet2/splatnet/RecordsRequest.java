package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Record;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class RecordsRequest extends SplatnetRequest {

    private Record records;
    private Context context;

    public RecordsRequest(Context context){
        this.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        records = gson.fromJson(settings.getString("records",""),Record.class);
    }

    @Override
    protected void manageResponse(Response response){
        records = (Record) response.body();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(records);
        edit.putString("records",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getRecords(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("records",records);
        return bundle;
    }
}
