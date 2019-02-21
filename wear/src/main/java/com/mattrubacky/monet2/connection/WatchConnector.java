package com.mattrubacky.monet2.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by mattr on 1/9/2018.
 */

public class WatchConnector extends AsyncTask<Void,Void,Void> implements DataApi.DataListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient googleApiClient;
    private Context context;
    private ArrayList<WatchConnection> watchConnections;
    private WatchConnected caller;
    private Bundle bundle;

    public WatchConnector(Context context,WatchConnected caller){
        this.context = context;
        this.caller = caller;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        watchConnections = new ArrayList<>();
        watchConnections.add(new ScheduleConnection(googleApiClient,context));
        watchConnections.add(new CoopScheduleConnection(googleApiClient,context));
        watchConnections.add(new CurrentSplatfestConnection(googleApiClient,context));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/splatnet") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    bundle = new Bundle();
                    for(int i=0;i<watchConnections.size();i++){
                        WatchConnection watchConnection = watchConnections.get(i);
                        watchConnection.saveData(dataMap.getString(watchConnection.name));
                        bundle = watchConnection.getResult(bundle);
                    }
                    caller.update(bundle);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    public void getData(){
        bundle = new Bundle();
        for(int i=0;i<watchConnections.size();i++){
            WatchConnection watchConnection = watchConnections.get(i);
            watchConnection.getData();
            bundle = watchConnection.getResult(bundle);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        getData();
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        caller.update(bundle);
    }
}
