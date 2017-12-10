package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Schedules;
import com.mattrubacky.monet2.deserialized.Timeline;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class TimelineRequest extends SplatnetRequest{

    private Context context;
    protected Timeline timeline;

    public TimelineRequest(Context context){
        this.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        timeline = gson.fromJson(settings.getString("timeline",""),Timeline.class);
    }

    @Override
    protected void manageResponse(Response response) {
        timeline = (Timeline) response.body();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SplatnetSQLManager database = new SplatnetSQLManager(context);
        SharedPreferences.Editor edit = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(timeline);
        edit.putString("timeline", json);
        edit.commit();

        //Handle Salmon Run gear
        if (timeline.currentRun.rewardGear != null) {
            ArrayList<Gear> gear = new ArrayList<>();
            gear.add(timeline.currentRun.rewardGear.gear);
            database.insertGear(gear);
        }

        //Handle New Weapons
        if(timeline.sheldon.newWeapons.size()>0){
            //push notification after notification overhaul
        }
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getTimeline(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("timeline",timeline);
        return bundle;
    }
}
