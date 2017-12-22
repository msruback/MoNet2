package com.mattrubacky.monet2.notifications;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public abstract class Notification {
    protected Context context;
    protected String name;

    @SerializedName("start_time")
    protected Long startTime;
    @SerializedName("end_time")
    protected Long endTime;

    public Notification(){}

    public Notification(Context context,Long startTime,Long endTime){
        this.context = context;
        this.startTime =  startTime;
        this.endTime = endTime;
    }
    public abstract void show();

    public String writeJSON(){
        StringBuilder builder = new StringBuilder();
        builder.append("\"start_time\":");
        builder.append(startTime);
        builder.append(",\"end_time\":");
        builder.append(endTime);
        return builder.toString();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public boolean equals(Notification toCompare){
        if(toCompare.endTime.equals(endTime)){
            if(isUnique(toCompare)){
                return false;
            }
            return true;
        }
        return false;
    }
    protected boolean isUnique(Notification unique){
        return false;
    }

    public boolean isValid(){
        long now = new Date().getTime();
        if((endTime)<now){
            return false;
        }
        return true;
    }
    public Long getTime(){
        return startTime;
    }
}
