package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.RewardGear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mattr on 12/20/2017.
 */

public class GrizzCoRewardNotification extends Notification {
    @SerializedName("reward")
    private RewardGear rewardGear;

    public GrizzCoRewardNotification(){}
    public GrizzCoRewardNotification(Context context, RewardGear rewardGear,Long nextMonth){
        super(context,rewardGear.available*1000,nextMonth);
        this.rewardGear = rewardGear;
    }

    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent rotationIntent = new Intent(context, MainActivity.class);
        rotationIntent.putExtra("fragment",0);
        PendingIntent rotationIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), rotationIntent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        String time = sdf.format(rewardGear.available*1000);
        String title = "New GrizzCo. Rewards";
        String content = "GrizzCo. is offering the new "+rewardGear.gear.name+" for shifts in "+time;

        android.app.Notification notification  = new android.app.Notification.Builder(context)
                .setContentTitle(title)
                .setStyle(new android.app.Notification.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.char_mr_grizz)
                .setContentIntent(rotationIntentPending)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .build();
        notification.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }
}
