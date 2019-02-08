package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "salmon_gear",
        foreignKeys = {
                @ForeignKey(entity = GearRoom.class,
                        parentColumns = "id",
                        childColumns = "gear")
        }
)
public class SalmonGearRoom {
    @PrimaryKey
    public long month;
    public int gear;

    public SalmonGearRoom(long month,int gear){
        this.month = month;
        this.gear = gear;
    }
}
