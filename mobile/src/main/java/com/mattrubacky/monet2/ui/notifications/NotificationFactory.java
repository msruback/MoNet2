package com.mattrubacky.monet2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public abstract class NotificationFactory {
    protected Context context;
    @SerializedName("notifications")
    protected ArrayList<Notification> notifications;
    @SerializedName("notified")
    protected ArrayList<Notification>notified;

    protected Gson gson;

    public NotificationFactory(){}

    public NotificationFactory(Context context){
        this.context = context;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(NotificationFactory.class, new NotificationFactoryAdapter());
        gsonBilder.registerTypeAdapter(Notification.class, new NotificationAdapter());
        Gson gson = gsonBilder.create();
        NotificationStorage storage = gson.fromJson(settings.getString(getName(),"{\"notifications\":[],\"notified\":[]}"),NotificationStorage.class);
        notifications = storage.notifications;
        notified = storage.notified;
    }

    public abstract ArrayList<Notification> findNotifications();

    public abstract String getName();

    public void setContext(Context context){
        this.context = context;
        for(int i=0;i<notifications.size();i++){
            notifications.get(i).setContext(context);
        }
        for (int i=0;i<notified.size();i++){
            notified.get(i).setContext(context);
        }
    }

    public void manageNotifications(){
        Notification notification;
        long now = new Date().getTime();

        notifications = findNotifications();

        for(int i=0;i<notified.size();i++){
            notification = notified.get(i);
            if(!notification.isValid()){
                notified.remove(i);
                i--;
            }else {
                for (int k = 0; k < notifications.size(); k++) {
                    if (notification.equals(notifications.get(k))){
                        notifications.remove(k);
                        k--;
                    }
                }
            }
        }

        for(int i=0;i<notifications.size();i++){
            notification = notifications.get(i);
            if(notification.getTime()<now&&notification.isValid()){
                notification.show();
                notifications.remove(i);
                i--;
                notified.add(notification);
            }else if(!notification.isValid()){
                notifications.remove(i);
                i--;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{\"notifications\":[");
        for(int i=0;i<notifications.size();i++){
            builder.append("{");
            builder.append("\"type\":\"");
            builder.append(notifications.get(i).name);
            builder.append("\",\"properties\":{");
            builder.append(notifications.get(i).writeJSON());
            builder.append("}}");
            if(i!=(notifications.size()-1)){
                builder.append(",");
            }
        }
        builder.append("],\"notified\":[");
        for(int i=0;i<notified.size();i++){
            builder.append("{");
            builder.append("\"type\":\"");
            builder.append(notified.get(i).name);
            builder.append("\",\"properties\":{");
            builder.append(notified.get(i).writeJSON());
            builder.append("}}");
            if(i!=(notified.size()-1)){
                builder.append(",");
            }
        }
        builder.append("]}");

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        String json = builder.toString();
        edit.putString(getName(),json);
        edit.commit();

    }

}
