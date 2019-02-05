package com.mattrubacky.monet2.rooms.entity;


import com.mattrubacky.monet2.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_period",
        foreignKeys = {
        @ForeignKey(entity = StageRoom.class,
            parentColumns = "id",
            childColumns = "a"),
        @ForeignKey(entity = StageRoom.class,
            parentColumns = "id",
            childColumns = "b")
})
public class TimePeriodRoom {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "start_time")
    public long start;

    @ColumnInfo(name = "end_time")
    public long end;

    public String rule;

    public String mode;

    public int a;

    public int b;

    public TimePeriodRoom(int id,long start,long end, String rule, String mode,int a,int b){
        this.id = id;
        this.start = start;
        this.end = end;
        this.rule = rule;
        this.mode = mode;
        this.a = a;
        this.b = b;
    }

    public TimePeriodRoom(long start,long end, String rule, String mode,int a,int b){
        int bias = 0;
        switch (mode){
            case "regular":
                bias=0;
                break;
            case "gachi":
                bias = 100;
                break;
            case "league":
                bias = 200;
                break;
            default:
                bias = 300;
                break;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(start));
        id = cal.get(Calendar.HOUR_OF_DAY)+bias;
        this.start = start;
        this.end = end;
        this.rule = rule;
        this.mode = mode;
        this.a = a;
        this.b = b;
    }

    public TimePeriod toDeserialized(List<StageRoom> stages){
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.start = start;
        timePeriod.end = end;
        timePeriod.gamemode = new KeyName();
        timePeriod.gamemode.key = mode;
        timePeriod.rule = new KeyName();
        timePeriod.rule.key = rule;
        for(StageRoom stageRoom : stages){
            if(stageRoom.id==a){
                timePeriod.a = stageRoom.toDeserialized();
            }else if (stageRoom.id==b){
                timePeriod.b = stageRoom.toDeserialized();
            }
        }
        return timePeriod;
    }
}
