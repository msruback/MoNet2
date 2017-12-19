package com.mattrubacky.monet2.notifications;

import android.content.Context;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public abstract class NotificationFactory {
    protected Context context;
    protected ArrayList<Notification> notifications,notified;

    public NotificationFactory(Context context){
        this.context = context;
        notifications = new ArrayList<>();
        notified = new ArrayList<>();
    }

    public abstract ArrayList<Notification> findNotifications();

    public void addNotifications(){
        ArrayList<Notification> toDetermine = findNotifications();
        Notification notification;

        for(int i=0;i<toDetermine.size();i++){
            notification = toDetermine.get(i);
            boolean isUnique = true;
            for(int j=0;j<notifications.size();j++){
                if(notifications.get(j).equals(notification)){
                    isUnique = false;
                }
            }
            for(int j=0;j<notified.size();j++){
                if(notified.get(i).equals(notification)){
                    isUnique = false;
                }
            }
            if(isUnique){
                notifications.add(notification);
            }
        }
    }

    public void manageNotifications(){
        Notification notification;
        long now = new Date().getTime();

        addNotifications();

        for(int i=0;i<notifications.size();i++){
            notification = notifications.get(i);
            if(notification.getTime()<now){
                notification.show();
                notifications.remove(i);
                i--;
                notified.add(notification);
            }
        }
        for(int i=0;i<notified.size();i++){
            notification = notified.get(i);
            if(!notification.isValid()){
                notified.remove(i);
                i--;
            }
        }
    }
}
