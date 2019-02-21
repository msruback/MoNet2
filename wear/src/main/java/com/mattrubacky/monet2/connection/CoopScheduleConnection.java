package com.mattrubacky.monet2.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.SalmonSchedule;

/**
 * Created by mattr on 1/9/2018.
 */

public class CoopScheduleConnection extends WatchConnection {
    private SalmonSchedule salmonSchedule;

    public CoopScheduleConnection(GoogleApiClient googleApiClient,Context context) {
        super(googleApiClient,"salmonRunSchedule",context);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedules\":[],\"details\":[]}"),SalmonSchedule.class);
        if(salmonSchedule== null){
            salmonSchedule = new SalmonSchedule();
        }
    }

    @Override
    public void saveData(String data) {
        Gson gson = new Gson();
        if(data!=null){
            salmonSchedule = gson.fromJson(data,SalmonSchedule.class);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = settings.edit();

            String json = gson.toJson(salmonSchedule);
            edit.putString("salmonRunSchedule", json);
            edit.commit();
        }
    }


    @Override
    public Bundle getResult(Bundle bundle){
        bundle.putParcelable("salmon",salmonSchedule);
        return bundle;
    }
}
