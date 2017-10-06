package com.mattrubacky.monet2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by mattr on 10/5/2017.
 */

public class OrderGear extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        super.onStartCommand(intent,flags,startId);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        return START_NOT_STICKY;
    }
}
