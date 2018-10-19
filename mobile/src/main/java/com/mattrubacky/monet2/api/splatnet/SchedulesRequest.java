package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Stage;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class SchedulesRequest extends SplatnetRequest {

    private Context context;
    private ActiveSplatfestRequest splatfestRequest;
    private Schedules schedules;

    public SchedulesRequest(Context context){
        this.context = context;
        splatfestRequest = new ActiveSplatfestRequest(context);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException,SplatnetMaintenanceException {
        schedules = (Schedules) response.body();
        SplatnetSQLManager database = new SplatnetSQLManager(context);
        ArrayList<Stage> stages = new ArrayList<>();
        for(int i=0;i<schedules.regular.size();i++){
            stages.add(schedules.regular.get(i).a);
            stages.add(schedules.regular.get(i).b);

            stages.add(schedules.ranked.get(i).a);
            stages.add(schedules.ranked.get(i).b);

            stages.add(schedules.league.get(i).a);
            stages.add(schedules.league.get(i).b);
        }
        database.insertStages(stages);

        splatfestRequest.run();
        CurrentSplatfest currentSplatfest = splatfestRequest.result(new Bundle()).getParcelable("currentSplatfest");
        if(currentSplatfest.splatfests.size()>0){
            schedules.setSplatfest(currentSplatfest.splatfests.get(0));
        }

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        edit.commit();
    }

    @Override
    public boolean shouldUpdate(){
        long now = new Date().getTime();
        boolean toReturn = true;
        if(schedules.regular!=null&&schedules.regular.size()>0&&schedules.splatfest!=null&&schedules.splatfest.size()>0){
            if(schedules.regular.get(0).end>schedules.splatfest.get(0).end){
                if((schedules.splatfest.get(0).end*1000)>=now){
                    toReturn = false;
                }
            }else{
                if((schedules.regular.get(0).end*1000)>=now){
                    toReturn = false;
                }
            }
        }else if(schedules.regular!=null&&schedules.regular.size()>0){
            if((schedules.regular.get(0).end*1000)>=now){
                toReturn = false;
            }
        }else if(schedules.splatfest!=null&&schedules.splatfest.size()>0){
            if((schedules.splatfest.get(0).end*1000)>=now){
                toReturn = false;
            }
        }
        return toReturn;
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSchedules(cookie,uniqueID);
        splatfestRequest.setup(splatnet,cookie,uniqueID);
    }



    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("schedules",schedules);
        return splatfestRequest.result(bundle);
    }
}
