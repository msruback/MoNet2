package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class CoopSchedulesRequest implements SplatnetRequest {
    private Splatnet splatnet;
    private String cookie,uniqueID;
    private Context context;

    public CoopSchedulesRequest(Context context){
        this.context = context;
    }

    @Override
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {
        Call<SalmonSchedule> salmonGet = splatnet.getSalmonSchedule(cookie,uniqueID);
        Response response = salmonGet.execute();
        if(response.isSuccessful()) {
            SalmonSchedule salmonSchedule = (SalmonSchedule) response.body();

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = settings.edit();
            Gson gson = new Gson();

            String json = gson.toJson(salmonSchedule);
            edit.putString("salmonRunSchedule",json);
            edit.commit();
        }
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        this.splatnet = splatnet;
        this.cookie = cookie;
        this.uniqueID = uniqueID;
    }
}
