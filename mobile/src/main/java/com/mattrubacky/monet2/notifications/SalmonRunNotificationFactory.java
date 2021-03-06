package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonSchedule;

import java.util.ArrayList;

/**
 * Created by mattr on 12/19/2017.
 */

public class SalmonRunNotificationFactory extends NotificationFactory {


    public SalmonRunNotificationFactory(Context context){
        super(context);
    }

    @Override
    public ArrayList<Notification> findNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        for(int i=0;i<schedule.details.size();i++){
            notifications.add(new SalmonRunNotification(context,schedule.details.get(i)));
        }
        return notifications;
    }
    @Override
    public String getName() {
        return "SalmonRunNotificationFactory";
    }
}
