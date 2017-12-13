package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.deserialized.PastSplatfest;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class PastSplatfestRequest extends SplatnetRequest {

    private Context context;
    private ArrayList<SplatfestDatabase> splatfests;
    private ActiveSplatfestRequest activeSplatfestRequest;

    public PastSplatfestRequest(Context context){
        this.context = context;
        activeSplatfestRequest = new ActiveSplatfestRequest(context);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        splatfests = gson.fromJson(settings.getString("allsplatfests","[]"),new TypeToken<ArrayList<SplatfestDatabase>>(){}.getType());
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException,SplatnetMaintenanceException {
        PastSplatfest pastSplatfest = (PastSplatfest) response.body();

        activeSplatfestRequest.run();

        SplatnetSQLManager database = new SplatnetSQLManager(context);
        database.insertSplatfests(pastSplatfest.splatfests,pastSplatfest.results);
        splatfests = database.getSplatfests();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();

        String json = gson.toJson(splatfests);
        edit.putString("allsplatfests",json);
        edit.commit();

    }

    @Override
    public boolean shouldUpdate(){

        long now = new Date().getTime();
        if(splatfests.size()>0){
            for(int i=0;i<splatfests.size();i++){
                if(splatfests.get(i).result.participants.alpha==0&&(splatfests.get(i).splatfest.times.end*1000)<now){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getPastSplatfests(cookie,uniqueID);
        activeSplatfestRequest.setup(splatnet,cookie,uniqueID);
    }



    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelableArrayList("splatfests",splatfests);
        return bundle;
    }
}
