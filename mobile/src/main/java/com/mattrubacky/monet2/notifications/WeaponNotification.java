package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.WeaponAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class WeaponNotification extends Notification {

    @SerializedName("availability")
    private WeaponAvailability availabilty;

    public WeaponNotification(){}
    public WeaponNotification(Context context, WeaponAvailability availability){
        super(context,new Date().getTime(),availability.release*1000);
        this.availabilty = availability;
    }
    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        String time;
        String title;
        String content;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context);


        time = sdf.format(availabilty.release*1000);

        title = availabilty.weapon.name+" available soon!";
        content = "I'm expecting a shipment of "+availabilty.weapon.name+" around "+time+" today! I think you might like the "+availabilty.weapon.sub+" sub weapon, or maybe the "+availabilty.weapon.special+" special weapon!";

        builder.setContentTitle(title)
                .setSmallIcon(R.drawable.char_sheldon)
                .setStyle(new android.app.Notification.BigTextStyle().bigText(content))
                .setColor(context.getResources().getColor(R.color.colorPrimary));
        android.app.Notification notification = builder.build();
        notification.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }
}
