package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.ResultIds;
import com.mattrubacky.monet2.deserialized.splatoon.Timeline;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class TimelineRequest extends SplatnetRequest{

    private Context context;
    protected Timeline timeline;
    protected ResultsRequest resultsRequest;
    protected Splatnet splatnet;
    protected String cookie,uniqueID;

    public TimelineRequest(Context context){
        this.context = context;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        timeline = gson.fromJson(settings.getString("timeline","{}"),Timeline.class);
        resultsRequest = new ResultsRequest(context);
    }

    @Override
    protected void manageResponse(Response response) throws SplatnetMaintenanceException, SplatnetUnauthorizedException, IOException {
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
            database.insertRewardGear(timeline.currentRun.rewardGear);
        }

        int maxID = settings.getInt("lastBattle",-1);
        ArrayList<ResultIds> needToUpdate = new ArrayList<>();
        for(int i=0;i<timeline.battles.battles.size();i++){
            if(timeline.battles.battles.get(i).id>maxID){
                needToUpdate.add(timeline.battles.battles.get(i));
            }
        }
        if(timeline.battles.battles.size()==needToUpdate.size()){
            resultsRequest.run();
        }else{
            ResultRequest resultRequest;
            for(int i=0;i<needToUpdate.size();i++){
                resultRequest = new ResultRequest(needToUpdate.get(i).id);
                resultRequest.setup(splatnet,cookie,uniqueID);
                resultRequest.run();
            }
        }
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getTimeline(cookie,uniqueID);
        this.splatnet = splatnet;
        this.cookie = cookie;
        this.uniqueID = uniqueID;

        resultsRequest.setup(splatnet,cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("timeline",timeline);
        return bundle;
    }
}
