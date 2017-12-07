package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Gear;
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
    private Timeline timeline;

    public TimelineRequest(Context context){
        this.context = context;
    }

    @Override
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        Call<Timeline> getTimeline = splatnet.getTimeline(cookie,uniqueID);
        Response response = getTimeline.execute();

        if(response.isSuccessful()) {
            timeline = (Timeline) response.body();
            SharedPreferences.Editor edit = settings.edit();

            //Handle Salmon Run gear
            if (timeline.currentRun.rewardGear != null) {
                Gson gson = new Gson();
                String json = gson.toJson(timeline.currentRun.rewardGear.gear);
                edit.putString("reward_gear", json);
                edit.commit();
                ArrayList<Gear> gear = new ArrayList<>();
                gear.add(timeline.currentRun.rewardGear.gear);
                database.insertGear(gear);
            }

            //Handle New Weapons
            if(timeline.sheldon.newWeapons.size()>0){
                //push notification after notification overhaul
            }
        }

    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("timeline",timeline);
        return bundle;
    }
}
