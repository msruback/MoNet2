package com.mattrubacky.monet2.connection;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Schedules;

/**
 * Created by mattr on 1/9/2018.
 */

public abstract class WatchConnection {
    public String name;
    private GoogleApiClient googleApiClient;
    protected Context context;

    public WatchConnection(GoogleApiClient googleApiClient,String name,Context context){
        this.googleApiClient = googleApiClient;
        this.name = name;
        this.context = context;
    }

    public abstract void saveData(String data);

    public void getData(){
        if(shouldUpdate()) {
            DataItemBuffer dataItemBuffer = Wearable.DataApi.getDataItems(googleApiClient).await();
            for (DataItem dataItem : dataItemBuffer) {
                if (dataItem.getUri().getPath().compareTo("/splatnet") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                    saveData(dataMap.getString(name));
                    System.out.println(name);
                }
            }
            dataItemBuffer.release();
        }
    }

    public boolean shouldUpdate(){
        return true;
    }


    public Bundle getResult(Bundle bundle){
        return bundle;
    }

}
