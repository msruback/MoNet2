package com.mattrubacky.monet2.backend.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;


import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;

import java.util.ArrayList;

import androidx.legacy.content.WakefulBroadcastReceiver;

/**
 * Created by mattr on 12/22/2017.
 */

public class BattleAlarm extends WakefulBroadcastReceiver implements SplatnetConnected {

    Context context;
    PowerManager.WakeLock wl;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context =context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "monet:battlealarm");
        wl.acquire();

//        SplatnetConnector splatnetConnector = new SplatnetConnector(this,context);
//        splatnetConnector.addRequest(new RecordsRequest(context));
//        splatnetConnector.addRequest(new ResultsRequest(context));
//        splatnetConnector.execute();

    }
    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BattleAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, (3)*(60)*(1000), pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, BattleAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, BattleAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    @Override
    public void update(Bundle bundle) {
        ArrayList<Battle> battles = bundle.getParcelableArrayList("battles");
        if(battles.size()>0){
            setAlarm(context);
        }
    }
}
