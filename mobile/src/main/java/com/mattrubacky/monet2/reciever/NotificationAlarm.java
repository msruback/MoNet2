package com.mattrubacky.monet2.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.notifications.BattleGearNotificationFactory;
import com.mattrubacky.monet2.notifications.Notification;
import com.mattrubacky.monet2.notifications.NotificationAdapter;
import com.mattrubacky.monet2.notifications.NotificationFactory;
import com.mattrubacky.monet2.notifications.NotificationFactoryAdapter;
import com.mattrubacky.monet2.notifications.SalmonRunNotificationFactory;
import com.mattrubacky.monet2.notifications.ShopNotificationFactory;
import com.mattrubacky.monet2.notifications.StageNotifitcationFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class NotificationAlarm extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(NotificationFactory.class, new NotificationFactoryAdapter());
        gsonBilder.registerTypeAdapter(Notification.class, new NotificationAdapter());
        Gson gson = gsonBilder.create();

        ArrayList<NotificationFactory> factories = gson.fromJson(settings.getString("notificationFactories","[]"),new TypeToken<ArrayList<NotificationFactory>>(){}.getType());
        for(int i=0;i<factories.size();i++){
            factories.get(i).setContext(context);
        }
        switch(factories.size()){
            case 0:
                factories.add(new ShopNotificationFactory(context));
                factories.add(new StageNotifitcationFactory(context));
                factories.add(new SalmonRunNotificationFactory(context));
                factories.add(new BattleGearNotificationFactory(context));
        }
        for(int i=0;i<factories.size();i++){
            factories.get(i).manageNotifications();
        }

        SharedPreferences.Editor edit = settings.edit();

        String json = gson.toJson(factories);
        edit.putString("notificationFactories",json);
        edit.commit();

        wl.release();
    }

    public void setAlarm(Context context)
    {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SalmonAlarm.class);
        PendingIntent intentPending = PendingIntent.getBroadcast(context, 1, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTime().getTime(),(60)*(60)*(1000), intentPending);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, SalmonAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, SalmonAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
