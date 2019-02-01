package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;

import com.mattrubacky.monet2.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.splatoon.Schedules;

/**
 * Created by mattr on 9/30/2017.
 */

public class WearLink implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private boolean nodeConnected = false;
    Context context;
    String schedules,salmonSchedule,currentSplatfest;

    public WearLink(Context context){
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        this.context = context;
    }

    @Override
    public void onConnected(Bundle bundle) {
        nodeConnected = true;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(schedules==null){
            schedules = settings.getString("rotationState","");
        }
        if(salmonSchedule==null){
            salmonSchedule = settings.getString("salmonRunSchedule","");
        }
        if(currentSplatfest==null){
            currentSplatfest = settings.getString("currentSplatfest","");
        }

        Gson gson = new Gson();
        Schedules scheduleObj=gson.fromJson(schedules,Schedules.class);

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/splatnet");
        putDataMapReq.getDataMap().putString("schedule",schedules);
        putDataMapReq.getDataMap().putString("salmonRunSchedule",salmonSchedule);
        putDataMapReq.getDataMap().putString("currentSplatfest",currentSplatfest);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, putDataReq);
    }

    @Override
    public void onConnectionSuspended(int i) {
        nodeConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        nodeConnected = false;
    }

    public void sendRotation(Schedules schedules){
        Gson gson = new Gson();
        this.schedules = gson.toJson(schedules);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(nodeConnected){
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/splatnet");
            putDataMapReq.getDataMap().putString("schedule",this.schedules);
            putDataMapReq.getDataMap().putString("currentSplatfest",settings.getString("currentSplatfest",""));
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            Wearable.DataApi.putDataItem(googleApiClient, putDataReq);
        }
    }

    public void sendSalmon(SalmonSchedule salmonSchedule){
        Gson gson = new Gson();
        this.salmonSchedule = gson.toJson(salmonSchedule);
        if(nodeConnected){
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/splatnet");
            putDataMapReq.getDataMap().putString("salmonRunSchedule",this.salmonSchedule);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            Wearable.DataApi.putDataItem(googleApiClient, putDataReq);
        }
    }

    public void openConnection(){
        googleApiClient.connect();
    }

    public void closeConnection(){
        googleApiClient.disconnect();
    }

}