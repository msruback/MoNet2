package com.mattrubacky.monet2.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import com.mattrubacky.monet2.splatnet.OrderRequest;
import com.mattrubacky.monet2.splatnet.ShopRequest;
import com.mattrubacky.monet2.splatnet.Splatnet;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 10/5/2017.
 */

public class OrderGear extends Service implements SplatnetConnected {

    Product buying;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        super.onStartCommand(intent,flags,startId);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        buying = intent.getExtras().getParcelable("product");
        SplatnetConnector splatnetConnector = new SplatnetConnector(this,getApplicationContext());
        splatnetConnector.addRequest(new OrderRequest(buying.id));
        splatnetConnector.addRequest(new ShopRequest(getApplicationContext(),true));
        splatnetConnector.execute();
        return START_NOT_STICKY;
    }

    @Override
    public void update(Bundle bundle) {
    }
}