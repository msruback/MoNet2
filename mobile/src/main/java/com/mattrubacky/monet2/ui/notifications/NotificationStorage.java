package com.mattrubacky.monet2.ui.notifications;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/21/2017.
 */

public class NotificationStorage {
    public NotificationStorage(){}

    @SerializedName("notifications")
    protected ArrayList<Notification> notifications;
    @SerializedName("notified")
    protected ArrayList<Notification>notified;
}
