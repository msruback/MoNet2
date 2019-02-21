package com.mattrubacky.monet2.backend.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.CurrentSplatfest;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ActiveSplatfestRequest extends SplatnetRequest{

    private Context context;
    private CurrentSplatfest currentSplatfest;

    public ActiveSplatfestRequest(Context context){
        this.context = context;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
    }

    @Override
    protected void manageResponse(Response response){
        currentSplatfest = (CurrentSplatfest) response.body();

        SplatnetSQLManager database = new SplatnetSQLManager(context);
        database.insertSplatfests(currentSplatfest.splatfests);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        String json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getActiveSplatfests(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("currentSplatfest",currentSplatfest);
        return bundle;
    }
}
