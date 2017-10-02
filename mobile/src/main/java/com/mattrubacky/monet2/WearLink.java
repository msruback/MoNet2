package com.mattrubacky.monet2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by mattr on 9/30/2017.
 */

public class WearLink
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private boolean nodeConnected = false;
    Context context;
    String schedules;

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
        if(schedules==null){
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            schedules = settings.getString("rotationState","");
        }

        Gson gson = new Gson();
        Schedules scheduleObj=gson.fromJson(schedules,Schedules.class);;

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/schedules");
        putDataMapReq.getDataMap().putString("schedule",schedules);
        putDataMapReq.getDataMap().putLong("time",new Date().getTime());
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
        if(nodeConnected){
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/schedules");
            putDataMapReq.getDataMap().putString("schedule",this.schedules);
            putDataMapReq.getDataMap().putLong("time",new Date().getTime());
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