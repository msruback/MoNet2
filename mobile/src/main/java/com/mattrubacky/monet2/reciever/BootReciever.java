package com.mattrubacky.monet2.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by mattr on 10/3/2017.
 */

public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long alarmSpacing;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        switch(settings.getInt("updateInterval",0)){
            case 0:
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
            case 1:
                alarmSpacing = Long.valueOf(1000*60*60*2);
                break;
            case 2:
                alarmSpacing = Long.valueOf(1000*60*60*4);
                break;
            case 3:
                alarmSpacing = Long.valueOf(1000*60*60*6);
                break;
            case 4:
                alarmSpacing = Long.valueOf(1000*60*60*8);
                break;
            case 5:
                alarmSpacing = Long.valueOf(1000*60*60*10);
                break;
            case 6:
                alarmSpacing = Long.valueOf(1000*60*60*12);
                break;
            case 7:
                alarmSpacing = Long.valueOf(1000*60*60*24);
                break;
            default:
                calendar.set(Calendar.MINUTE,0);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
        }
        AlarmManager am =( AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DataUpdateAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),alarmSpacing, pi);
        SalmonAlarm salmonAlarm = new SalmonAlarm();
        salmonAlarm.setAlarm(context);
    }
}
