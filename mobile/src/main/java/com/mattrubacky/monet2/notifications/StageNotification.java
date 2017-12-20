package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by mattr on 12/19/2017.
 */

public class StageNotification extends Notification {

    @SerializedName("stage")
    private Stage stage;
    @SerializedName("period")
    private TimePeriod period;

    public StageNotification(){}

    public StageNotification(Context context, TimePeriod period, Stage stage){
        super(context,period.start,period.end);
        this.period = period;
        this.stage = stage;
    }

    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent stageIntent = new Intent(context, MainActivity.class);
        stageIntent.putExtra("fragment",0);
        PendingIntent stageIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), stageIntent, 0);

        String time;
        String title;
        String content;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context);
        if(stage.id==-1){
            if((period.start *1000)<new Date().getTime()){
                title = period.rule.name + " available now in " + period.gamemode.name + "!";
                time = sdf.format((period.end*1000));
                content = "Play "+period.rule.name+ " on "+period.a.name + " and "+ period.b.name +" until "+time+"!";
            }else{
                title = period.rule.name + " available soon in " + period.gamemode.name + "!";
                time = sdf.format((period.start*1000));
                content = "Play "+period.rule.name + " on "+period.a.name + " and " + period.b.name+ " at "+time+"!";
            }
        }else{
            if((period.start *1000)<new Date().getTime()){
                title = stage.name + " available now in " + period.gamemode.name + "!";
                time = sdf.format((period.end*1000));
                content = "Play " + period.rule.name + " on "+ stage.name +" now until "+time+"!";
            }else{
                title = stage.name + " available soon in " + period.gamemode.name + "!";
                time = sdf.format((period.start*1000));
                content = "Play " + period.rule.name + " on "+ stage.name + " at "+time+"!";
            }
        }

        Random random = new Random();
        if(random.nextInt(2)==1){
            builder.setSmallIcon(R.drawable.char_marina);
        }else{
            builder.setSmallIcon(R.drawable.char_pearl);
        }
        builder.setContentTitle(title)
                .setStyle(new android.app.Notification.BigTextStyle().bigText(content))
                .setContentIntent(stageIntentPending)
                .setColor(context.getResources().getColor(R.color.colorPrimary));
        android.app.Notification notification = builder.build();
        notification.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }
}
