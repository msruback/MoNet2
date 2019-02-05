package com.mattrubacky.monet2.rooms.entity;

import java.sql.Date;
import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift",
        foreignKeys = {
                @ForeignKey(entity = SalmonStageRoom.class,
                            parentColumns = "id",
                            childColumns = "stage")
        })
public class SalmonShiftRoom {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "start_time")
    public long startTime;
    @ColumnInfo(name = "end_time")
    public long endTime;
    public int stage;

    public SalmonShiftRoom(int id, long startTime,long endTime,int stage){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
    }
    public SalmonShiftRoom(long startTime,long endTime,int stage){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startTime));
        id = cal.get(Calendar.YEAR)-2017;
        id *= 1000;
        id += cal.get(Calendar.DAY_OF_YEAR);
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
    }
}
