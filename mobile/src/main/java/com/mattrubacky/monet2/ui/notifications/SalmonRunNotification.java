package com.mattrubacky.monet2.ui.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.ui.activities.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class SalmonRunNotification extends Notification {
    @SerializedName("run")
    private SalmonRunDetail run;

    public SalmonRunNotification(){
        name = "SalmonRunNotification";
    }
    public SalmonRunNotification(Context context, SalmonRunDetail run){
        super(context,run.start*1000,run.end*1000);
        this.run = run;
        name = "SalmonRunNotification";
    }
    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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
                contentBuilder.append("the "+run.weapons.get(0).weapon.name+", ");
            }
            if(run.weapons.get(1)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(1).weapon.name+", ");
            }
            if(run.weapons.get(2)==null){
                contentBuilder.append("a Mystery Weapon, and ");
            }else{
                contentBuilder.append("the "+run.weapons.get(2).weapon.name+", and ");
            }
            if(run.weapons.get(3)==null){
                contentBuilder.append("a Mystery Weapon, ");
            }else{
                contentBuilder.append("the "+run.weapons.get(3).weapon.name+".");
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

    @Override
    public String writeJSON() {
        StringBuilder builder = new StringBuilder();
        Gson gson = new Gson();
        builder.append(super.writeJSON());
        builder.append(",\"run\":");
        builder.append(gson.toJson(run));
        return builder.toString();
    }

    @Override
    protected boolean isUnique(Notification unique) {
        SalmonRunNotification salmonRunNotification = (SalmonRunNotification) unique;
        return false;
    }
}
