package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.GrizzCo;
import com.mattrubacky.monet2.deserialized.RewardGear;
import com.mattrubacky.monet2.deserialized.Timeline;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/20/2017.
 */

public class GrizzCoRewardNotificationFactory extends NotificationFactory {

    public GrizzCoRewardNotificationFactory(){}
    public GrizzCoRewardNotificationFactory(Context context){
        super(context);
    }

    @Override
    public ArrayList<Notification> findNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        Timeline timeline = gson.fromJson(settings.getString("timeline","{}"),Timeline.class);
        RewardGear rewardGear = timeline.currentRun.rewardGear;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(rewardGear.available*1000);
        calendar.add(Calendar.MONTH,1);
        notifications.add(new GrizzCoRewardNotification(context,rewardGear,calendar.getTimeInMillis()));
        return notifications;
    }
}
