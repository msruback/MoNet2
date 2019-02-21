package com.mattrubacky.monet2.backend.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.mattrubacky.monet2.ui.notifications.BattleGearNotificationFactory;
import com.mattrubacky.monet2.ui.notifications.GrizzCoRewardNotificationFactory;
import com.mattrubacky.monet2.ui.notifications.SalmonRunNotificationFactory;
import com.mattrubacky.monet2.ui.notifications.ShopNotificationFactory;
import com.mattrubacky.monet2.ui.notifications.StageNotificationFactory;
import com.mattrubacky.monet2.ui.notifications.WeaponNotificationFactory;

import androidx.legacy.content.WakefulBroadcastReceiver;

/**
 * Created by mattr on 12/19/2017.
 */

public class NotificationAlarm extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "monet:notificationalarm");
        wl.acquire();

        new ShopNotificationFactory(context).manageNotifications();
        new StageNotificationFactory(context).manageNotifications();
        new SalmonRunNotificationFactory(context).manageNotifications();
        new BattleGearNotificationFactory(context).manageNotifications();
        new WeaponNotificationFactory(context).manageNotifications();
        new GrizzCoRewardNotificationFactory(context).manageNotifications();

        wl.release();
    }

    public void setAlarm(Context context)
    {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationAlarm.class);
        PendingIntent intentPending = PendingIntent.getBroadcast(context, 1, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP,0,(60)*(60)*(1000), intentPending);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NotificationAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public boolean isAlarmSet(Context context){
        Intent intent = new Intent(context, NotificationAlarm.class);
        return (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
