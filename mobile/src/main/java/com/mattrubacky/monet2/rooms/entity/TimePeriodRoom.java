package com.mattrubacky.monet2.rooms.entity;


import com.mattrubacky.monet2.deserialized.splatoon.KeyName;

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

    public KeyName rule;

    public KeyName mode;

    public int a;

    public int b;

    public TimePeriodRoom(int id,long start,long end, String rule, String mode,int a,int b){
        this.id = id;
        this.start = start;
        this.end = end;
        this.rule = new KeyName();
        this.rule.key = rule;
        this.mode = new KeyName();
        this.mode.key = mode;
        this.a = a;
        this.b = b;
    }
}
