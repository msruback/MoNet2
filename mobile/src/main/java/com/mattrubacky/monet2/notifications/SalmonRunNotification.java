package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.SalmonRun;
import com.mattrubacky.monet2.deserialized.SalmonRunDetail;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class SalmonRunNotification extends Notification {
    @SerializedName("run")
    private SalmonRunDetail run;

    public SalmonRunNotification(){}
    public SalmonRunNotification(Context context, SalmonRunDetail run){
        super(context,run.start,run.end);
        this.run = run;
    }
    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent rotationIntent = new Intent(context, MainActivity.class);
        rotationIntent.putExtra("fragment",0);
        PendingIntent rotationIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), rotationIntent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d h a");
        String time = sdf.format(run.end*1000);
        String title = "Grizz Co. Now Hiring!";
        String content;
        StringBuilder contentBuilder = new StringBuilder();

        if(run.weapons.get(0)==null&&run.weapons.get(1)==null&&run.weapons.get(2)==null&&run.weapons.get(3)==null){
            content = "Grizz Co. is now hiring until "+time+" for shifts at "+run.stage.name+".\n Weapons to be provided on location.";
        }else {
            contentBuilder.append("Grizz Co. is now hiring until " + time + " for shifts at " + run.stage.name + ".\n Workers will be provided ");
            if(run.weapons.get(0)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(0).name+", ");
            }
            if(run.weapons.get(1)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(1).name+", ");
            }
            if(run.weapons.get(2)==null){
                contentBuilder.append("a Mystery Weapon, and ");
            }else{
                contentBuilder.append("the "+run.weapons.get(2).name+", and ");
            }
            if(run.weapons.get(3)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(3).name+".");
            }
            content = contentBuilder.toString();
        }
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
