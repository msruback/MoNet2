package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;
import com.mattrubacky.monet2.deserialized.splatoon.Timeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mattr on 12/20/2017.
 */

public class GrizzCoRewardNotificationFactory extends NotificationFactory {

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
        calendar.add(Calendar.DAY_OF_MONTH,3);
        rewardGear.available = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH,1);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss");
        String time = sdf.format(calendar.getTime());

        notifications.add(new GrizzCoRewardNotification(context,rewardGear,calendar.getTimeInMillis()));
        return notifications;
    }
    @Override
    public String getName() {
        return "GrizzCoRewardNotificationFactory";
    }
}
