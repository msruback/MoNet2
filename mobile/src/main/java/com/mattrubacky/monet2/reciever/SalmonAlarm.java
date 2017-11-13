package com.mattrubacky.monet2.reciever;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.gson.Gson;

import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mattr on 10/6/2017.
 */

public class SalmonAlarm extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        SalmonRunDetail run = schedule.details.get(0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent rotationIntent = new Intent(context, MainActivity.class);
        rotationIntent.putExtra("fragment",0);
        PendingIntent rotationIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), rotationIntent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d h a");
        String time = sdf.format(schedule.details.get(0).end*1000);
        String title = "Grizz Co. Now Hiring!";
        String content;
        StringBuilder contentBuilder = new StringBuilder();

        if(run.weapons.get(0)==null&&run.weapons.get(1)==null&&run.weapons.get(2)==null&&run.weapons.get(3)==null){
            content = "Grizz Co. is now hiring until "+time+" for shifts at "+run.stage.name+".\n Weapons to be provided on location.";
        }else {
            contentBuilder.append("Grizz Co. is now hiring until " + time + " for shifts at " + run.stage.name + ".\n Workers will be provided ");
            if(run.weapons.get(0)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(0).name+", ");
            }
            if(run.weapons.get(1)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(1).name+", ");
            }
            if(run.weapons.get(2)==null){
                contentBuilder.append("a Mystery Weapon, and ");
            }else{
                contentBuilder.append("the "+run.weapons.get(2).name+", and ");
            }
            if(run.weapons.get(3)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(3).name+".");
            }
            content = contentBuilder.toString();
        }
        Notification notification  = new Notification.Builder(context)
                .setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.char_mr_grizz)
                .setContentIntent(rotationIntentPending)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .build();
        notification.defaults = Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
        wl.release();
    }

    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        int day = 0;
        Calendar calendar = Calendar.getInstance();
        if(schedule.details!=null&&schedule.details.size()>0) {
            SalmonRunDetail run = schedule.details.get(0);
            calendar.setTimeInMillis(run.start*1000);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            if(day==settings.getInt("salmonDay",-1)){
                run = schedule.details.get(1);
                calendar.setTimeInMillis(run.start*1000);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, SalmonAlarm.class);
            PendingIntent intentPending = PendingIntent.getBroadcast(context, 1, intent, 0);
            am.set(AlarmManager.RTC_WAKEUP,run.start*1000, intentPending);
            SharedPreferences.Editor edit = settings.edit();
            edit.putInt("salmonDay",day);
            edit.commit();
        }
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, SalmonAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt("salmonDay",0);
        edit.commit();
    }
    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, SalmonAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
