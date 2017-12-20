package com.mattrubacky.monet2.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Schedules;
import com.mattrubacky.monet2.deserialized.StageNotifications;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 12/19/2017.
 */

public class StageNotifitcationFactory extends NotificationFactory {
    @Override
    public ArrayList<Notification> findNotifications() {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        Schedules schedules = gson.fromJson(settings.getString("rotationState","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);
        StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications",""),StageNotifications.class);

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.addAll(getFromTimePeriod(schedules.regular,stageNotifications.notifications));
        notifications.addAll(getFromTimePeriod(schedules.ranked,stageNotifications.notifications));
        notifications.addAll(getFromTimePeriod(schedules.league,stageNotifications.notifications));
        notifications.addAll(getFromTimePeriod(schedules.splatfest,stageNotifications.notifications));

        return notifications;
    }
    private ArrayList<Notification> getFromTimePeriod(ArrayList<TimePeriod> timePeriods, ArrayList<com.mattrubacky.monet2.deserialized.StageNotification> toNotify){
        ArrayList<Notification> notifications = new ArrayList<>();
        for(int i=0;i<timePeriods.size();i++){
            TimePeriod timePeriod = timePeriods.get(i);
            for(int j=0;j<toNotify.size();j++){
                com.mattrubacky.monet2.deserialized.StageNotification notification = toNotify.get(i);
                if(timePeriod.gamemode.key.equals(notification.type)||notification.type.equals("any")) {
                    if (timePeriod.rule.key.equals(notification.rule) || notification.rule.equals("any")) {
                        if (notification.stage.id == timePeriod.a.id || notification.stage.id == timePeriod.b.id || notification.stage.id == -1) {
                            notifications.add(new StageNotification(context,timePeriod,notification.stage));
                        }
                    }
                }
            }
        }
        return notifications;
    }
}
