package com.mattrubacky.monet2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by mattr on 9/26/2017.
 */

public class DataUpdateAlarm extends BroadcastReceiver {

    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Intent serviceIntent = new Intent(context, UpdateData.class);
        context.startService(serviceIntent);


        wl.release();
    }

    public void setAlarm(Context context)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Long alarmSpacing;
        switch(settings.getInt("updateInterval",0)){
            case 0:
                alarmSpacing = Long.valueOf((1000*60*60));//1 Hour
                break;
            case 1:
                alarmSpacing = Long.valueOf((1000*60*60)*2);//2 Hour
                break;
            case 2:
                alarmSpacing = Long.valueOf((1000*60*60)*4);//4 Hour
                break;
            case 3:
                alarmSpacing = Long.valueOf((1000*60*60)*6);//6 Hour
                break;
            case 4:
                alarmSpacing = Long.valueOf((1000*60*60)*8);//8 Hour
                break;
            case 5:
                alarmSpacing = Long.valueOf((1000*60*60)*10);//10 Hour
                break;
            case 6:
                alarmSpacing = Long.valueOf((1000*60*60)*12);//12 Hour
                break;
            case 7:
                alarmSpacing = Long.valueOf((1000*60*60)*24);//24 Hour
                break;
            default:
                alarmSpacing = Long.valueOf((1000*60*60));
                break;
        }
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DataUpdateAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmSpacing, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, DataUpdateAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
