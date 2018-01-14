package com.mattrubacky.monet2.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Schedules;

import java.util.Date;

/**
 * Created by mattr on 1/9/2018.
 */

public class ScheduleConnection extends WatchConnection {
    private Schedules schedules;

    public ScheduleConnection(GoogleApiClient googleApiClient, Context context){
        super(googleApiClient,"schedule",context);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("schedule","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);

    }
    @Override
    public void saveData(String data) {
        Gson gson = new Gson();
        if(data!=null){
            schedules = gson.fromJson(data,Schedules.class);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = settings.edit();

            edit.putString("schedule", gson.toJson(schedules));
            edit.commit();
        }
    }

    @Override
    public boolean shouldUpdate(){
        if(schedules!=null){
                if(schedules.regular!=null&&schedules.regular.size()>0){
                    if((schedules.regular.get(0).end*1000)>new Date().getTime()){
                        return false;
                    }
                }
                if(schedules.splatfest!=null&&schedules.splatfest.size()>0){
                    if((schedules.splatfest.get(0).end*1000)>new Date().getTime()){
                        return false;
                    }
                }
        }
        return true;
    }

    @Override
    public Bundle getResult(Bundle bundle){
        bundle.putParcelable("rotation",schedules);
        return bundle;
    }
}
