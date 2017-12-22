package com.mattrubacky.monet2.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Annie;
import com.mattrubacky.monet2.deserialized.GearNotification;
import com.mattrubacky.monet2.deserialized.GearNotifications;
import com.mattrubacky.monet2.deserialized.Product;

import java.util.ArrayList;

/**
 * Created by mattr on 12/19/2017.
 */

public class ShopNotificationFactory extends NotificationFactory {


    public ShopNotificationFactory(Context context){
        super(context);
    }

    @Override
    public ArrayList<Notification> findNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications",""),GearNotifications.class);

        Annie shop = gson.fromJson(settings.getString("shopState",""),Annie.class);

        for(int i = 0 ;i<shop.merch.size();i++){
            Product product = shop.merch.get(i);
            for(int j=0;j<gearNotifications.notifications.size();j++){
                GearNotification notification = gearNotifications.notifications.get(j);
                if(notification.gear.id == product.gear.id&&notification.gear.kind.equals(product.gear.kind)){
                    notifications.add(new ShopNotification(context,product));
                }
            }
        }
        return notifications;
    }
    @Override
    public String getName() {
        return "ShopNotificationFactory";
    }


}
