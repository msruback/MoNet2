package com.mattrubacky.monet2.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.CurrentSplatfest;

/**
 * Created by mattr on 1/9/2018.
 */

public class CurrentSplatfestConnection extends WatchConnection {
    private CurrentSplatfest currentSplatfest;

    public CurrentSplatfestConnection(GoogleApiClient googleApiClient, Context context) {
        super(googleApiClient, "currentSplatfest",context);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        if(currentSplatfest==null){
            currentSplatfest = new CurrentSplatfest();
        }
    }

    @Override
    public void saveData(String data) {
        Gson gson = new Gson();
        if(data!=null){
            currentSplatfest = gson.fromJson(data,CurrentSplatfest.class);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = settings.edit();

            String json = gson.toJson(currentSplatfest);
            edit.putString("currentSplatfest", json);
            edit.commit();
        }
    }

    @Override
    public Bundle getResult(Bundle bundle){
        bundle.putParcelable("splatfest",currentSplatfest);
        return bundle;
    }
}
