package com.mattrubacky.monet2.deserialized.splatoon;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * A container for the notifications ArrayList for simpler serialization
 */
public class StageNotifications{
    public StageNotifications(){
    }
    @SerializedName("notifications")
    public ArrayList<StageNotification> notifications;
}
