package com.mattrubacky.monet2.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import com.mattrubacky.monet2.api.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.RecordsRequest;
import com.mattrubacky.monet2.api.splatnet.ResultsRequest;
import com.mattrubacky.monet2.api.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.api.splatnet.ShopRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.helper.WearLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.legacy.content.WakefulBroadcastReceiver;

/**
 * Created by mattr on 9/26/2017.
 */

public class DataUpdateAlarm extends WakefulBroadcastReceiver implements SplatnetConnected{

    Context context;
    PowerManager.WakeLock wl;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context =context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "monet:dataupdatealarm");
        wl.acquire();

        SplatnetConnector splatnetConnector = new SplatnetConnector(this,context);
        splatnetConnector.addRequest(new SchedulesRequest(context));
        splatnetConnector.addRequest(new CoopSchedulesRequest(context,false));
        splatnetConnector.addRequest(new ShopRequest(context));
        splatnetConnector.addRequest(new RecordsRequest(context));
        splatnetConnector.addRequest(new ResultsRequest(context));
        //splatnetConnector.addRequest(new CoopResultsRequest(context));
        splatnetConnector.execute();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();

        int lastUpdate = settings.getInt("last_update",0);
        switch(settings.getInt("updateInterval",0)){
            case 0:
                lastUpdate += 1;
                break;
            case 1:
                lastUpdate += 2;
                break;
            case 2:
                lastUpdate += 4;
                break;
            case 3:
                lastUpdate += 6;
                break;
            case 4:
                lastUpdate += 8;
                break;
            case 5:
                lastUpdate += 10;
                break;
            case 6:
                lastUpdate += 12;
                break;
            case 7:
                lastUpdate += 24;
                break;
            default:
                lastUpdate += 1;
                break;
        }
        edit.putInt("last_update",lastUpdate);

    }

    public void setAlarmDelayed(Context context){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long alarmTime, alarmSpacing;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        int hour;
        switch(settings.getInt("updateInterval",0)){
            case 0:
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
            case 1:
                hour = 2 - calendar.get(Calendar.HOUR_OF_DAY)%2;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//2 Hour
                alarmSpacing = Long.valueOf(1000*60*60*2);
                break;
            case 2:
                hour = 4 - calendar.get(Calendar.HOUR_OF_DAY)%4;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//4 Hour
                alarmSpacing = Long.valueOf(1000*60*60*4);
                break;
            case 3:
                hour = 6 - calendar.get(Calendar.HOUR_OF_DAY)%6;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//6 Hour
                alarmSpacing = Long.valueOf(1000*60*60*6);
                break;
            case 4:
                hour = 8 - calendar.get(Calendar.HOUR_OF_DAY)%8;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//8 Hour
                alarmSpacing = Long.valueOf(1000*60*60*8);
                break;
            case 5:
                hour = 10 - calendar.get(Calendar.HOUR_OF_DAY)%10;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//10 Hour
                alarmSpacing = Long.valueOf(1000*60*60*10);
                break;
            case 6:
                hour = 12 - calendar.get(Calendar.HOUR_OF_DAY)%12;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//12 Hour
                alarmSpacing = Long.valueOf(1000*60*60*12);
                break;
            case 7:
                hour = 24 - calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());//24 Hour
                alarmSpacing = Long.valueOf(1000*60*60*24);
                break;
            default:
                calendar.set(Calendar.MINUTE,0);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmTime = calendar.getTimeInMillis()-(new Date().getTime());
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
        }
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DataUpdateAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+alarmTime,alarmSpacing, pi); // Millisec * Second * Minute
    }

    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long alarmSpacing;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        int hour;
        switch(settings.getInt("updateInterval",0)){
            case 0:
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
            case 1:
                hour = 2 - calendar.get(Calendar.HOUR_OF_DAY)%2;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*2);
                break;
            case 2:
                hour = 4 - calendar.get(Calendar.HOUR_OF_DAY)%4;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*4);
                break;
            case 3:
                hour = 6 - calendar.get(Calendar.HOUR_OF_DAY)%6;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*6);
                break;
            case 4:
                hour = 8 - calendar.get(Calendar.HOUR_OF_DAY)%8;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*8);
                break;
            case 5:
                hour = 10 - calendar.get(Calendar.HOUR_OF_DAY)%10;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*10);
                break;
            case 6:
                hour = 12 - calendar.get(Calendar.HOUR_OF_DAY)%12;
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*12);
                break;
            case 7:
                hour = 24 - calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY,hour);
                alarmSpacing = Long.valueOf(1000*60*60*24);
                break;
            default:
                calendar.set(Calendar.MINUTE,0);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                alarmSpacing = Long.valueOf(1000*60*60);
                break;
        }
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DataUpdateAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),alarmSpacing, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    @Override
    public void update(Bundle bundle) {
        ArrayList<Battle> battles = bundle.getParcelableArrayList("battles");
        BattleAlarm battleAlarm = new BattleAlarm();
        if(battles.size()>0){
            battleAlarm.setAlarm(context);
        }
        WearLink wearLink = new WearLink(context);
    }
}
