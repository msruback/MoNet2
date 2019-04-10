package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;

import java.sql.Date;
import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "salmon_gear",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "gear")
        },
        indices = {
                @Index(name="monthly_gear",
                        value = "gear")
        }
)
public class SalmonGearRoom {
    @PrimaryKey
    public int month;
    public int gear;

    public SalmonGearRoom(long now, int gear){
        this.month = generateId(now);
        this.gear = gear;
    }

    public SalmonGearRoom(int month,int gear){
        this.month = month;
        this.gear = gear;
    }

    public static int generateId(long now){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));
        int id = cal.get(Calendar.YEAR)-2017;
        id *= 100;
        id += cal.get(Calendar.MONTH);
        return id;
    }
}
