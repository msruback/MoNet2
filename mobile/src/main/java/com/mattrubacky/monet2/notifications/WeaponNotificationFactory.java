package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.Timeline;

import java.util.ArrayList;

/**
 * Created by mattr on 12/20/2017.
 */

public class WeaponNotificationFactory extends NotificationFactory {

    protected static String name = "WeaponNotificationFactory";
    public WeaponNotificationFactory(){
    }
    public WeaponNotificationFactory(Context context){
        super(context);
    }

    @Override
    public ArrayList<Notification> findNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        Timeline timeline = gson.fromJson(settings.getString("timeline","{}"),Timeline.class);
        for(int i=0;i<timeline.sheldon.newWeapons.size();i++){
            notifications.add(new WeaponNotification(context,timeline.sheldon.newWeapons.get(i)));
        }
        return notifications;
    }
}
