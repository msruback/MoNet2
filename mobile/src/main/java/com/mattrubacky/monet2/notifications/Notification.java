package com.mattrubacky.monet2.notifications;

import android.content.Context;

import java.util.Date;

/**
 * Created by mattr on 12/19/2017.
 */

public abstract class Notification {
    protected Context context;
    protected Long startTime,endTime;
    public Notification(Context context,Long startTime,Long endTime){
        this.context = context;
        this.startTime =  startTime;
        this.endTime = endTime;
    }
    public abstract void show();
    public boolean equals(Notification toCompare){
        if(toCompare.endTime.equals(endTime)&&isUnique(toCompare)){
            return false;
        }
        return true;
    }
    protected boolean isUnique(Notification unique){
        return true;
    }

    public boolean isValid(){
        long now = new Date().getTime();
        if((endTime*1000)<now){
            return false;
        }
        return true;
    }
    public Long getTime(){
        return startTime*1000;
    }
}
