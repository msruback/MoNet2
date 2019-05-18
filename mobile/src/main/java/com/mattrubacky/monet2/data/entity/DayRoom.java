package com.mattrubacky.monet2.data.entity;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "day")
public class DayRoom {

    public DayRoom(long day){
        this.day = day;
    }

    @PrimaryKey
    public long day;

    public static long generateId(long time){
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTimeInMillis(time);
        timeCal.set(Calendar.MILLISECOND,0);
        timeCal.set(Calendar.SECOND,0);
        timeCal.set(Calendar.MINUTE,0);
        timeCal.set(Calendar.HOUR_OF_DAY,0);
        return timeCal.getTimeInMillis();
    }

    public static long generateEnd(long time){
        return generateId(time)+86400000;
    }

}
