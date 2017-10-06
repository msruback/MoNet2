package com.mattrubacky.monet2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

/**
 * Created by mattr on 10/6/2017.
 */

public class SalmonAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        SalmonRun run = intent.getExtras().getParcelable("run");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent rotationIntent = new Intent(context, MainActivity.class);
        rotationIntent.putExtra("fragment",0);
        PendingIntent rotationIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), rotationIntent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");

        String time = sdf.format(run.endTime);
        String title = "Grizz Co. Now Hiring!";
        String content;

        if(!run.stage.equals("")){
            if(run.weapons.get(0)!=null&&run.weapons.get(1)!=null&&run.weapons.get(2)!=null&&run.weapons.get(3)!=null){
                if(run.weapons.get(0).id==-1){
                    content = "Grizz Co. is now hiring until "+time+" for shifts at "+run.stage+
                            ".\n Weapons to be provided on location.";
                }else {
                    content = "Grizz Co. is now hiring until " + time + " for shifts at " + run.stage + ".\n Workers will be provided the "+
                            run.weapons.get(0).name+", "+run.weapons.get(1).name+", "+run.weapons.get(2).name+", and "+run.weapons.get(3).name+".";
                }
            }else{
                content = "Grizz Co. is now hiring until "+time+" for shifts at "+run.stage+".";
            }
        }else{
            content = "Grizz Co. is now hiring until "+time+".";
        }
        Notification notification  = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.char_mr_grizz)
                .setContentIntent(rotationIntentPending)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(0, notification);
        wl.release();
    }

    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        if(schedule.schedule.size()>0) {
            SalmonRun run = schedule.schedule.get(0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, DataUpdateAlarm.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("run",run);
            intent.putExtras(bundle);
            PendingIntent intentPending = PendingIntent.getBroadcast(context, 0, intent, 0);
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(run.startTime*1000), intentPending);
        }
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
