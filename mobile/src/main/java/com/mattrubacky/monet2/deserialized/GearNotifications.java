package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * The class just wraps the GearNotification ArrayList to make deserialization easier
 */
public class GearNotifications{
    public GearNotifications(){}
    @SerializedName("notifications")
    public ArrayList<GearNotification> notifications;
}
