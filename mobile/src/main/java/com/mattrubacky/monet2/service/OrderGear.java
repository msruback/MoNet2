package com.mattrubacky.monet2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.mattrubacky.monet2.api.splatnet.OrderRequest;
import com.mattrubacky.monet2.api.splatnet.ShopRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.deserialized.splatoon.Product;

import androidx.annotation.Nullable;

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