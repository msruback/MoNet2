package com.mattrubacky.monet2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by mattr on 10/3/2017.
 */

public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            DataUpdateAlarm dataUpdateAlarm = new DataUpdateAlarm();
            dataUpdateAlarm.setAlarm(context);
        }
    }
}
