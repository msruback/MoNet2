package com.mattrubacky.monet2.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.MainActivity;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Product;
import com.mattrubacky.monet2.service.OrderGear;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public class ShopNotification extends Notification{
    @SerializedName("product")
    private Product product;

    public ShopNotification(){}

    public ShopNotification(Context context,Product product){
        super(context,new Date().getTime(),product.endTime*1000);
        this.product = product;
    }

    @Override
    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent shopIntent = new Intent(context, MainActivity.class);
        shopIntent.putExtra("fragment",1);
        PendingIntent shopIntentPending = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), shopIntent, 0);

        Intent orderIntent = new Intent(context,OrderGear.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product",product);
        orderIntent.putExtras(bundle);
        PendingIntent orderIntentPending = PendingIntent.getService(context, (int) System.currentTimeMillis(), orderIntent, 0);


        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

        String time = sdf.format((product.endTime*1000));
        String title = "New "+product.gear.name+" Available!";
        String content = product.gear.name + " with " + product.skill.name + " is now available until "+time+"!";

        android.app.Notification notification  = new android.app.Notification.Builder(context)
                .setContentTitle(title)
                .setStyle(new android.app.Notification.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.char_annie)
                .setContentIntent(shopIntentPending)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .addAction(R.drawable.char_annie,"Order",orderIntentPending)
                .build();
        notification.defaults = android.app.Notification.DEFAULT_ALL;
        notificationManager.notify((int) (new Date().getTime()%10000), notification);
    }

}
