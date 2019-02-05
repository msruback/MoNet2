package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "salmon_weapons",
        primaryKeys = {"weaponId","shiftId"},
        foreignKeys = {
                @ForeignKey(entity = SalmonShiftRoom.class,
                            parentColumns = "id",
                            childColumns = "shiftId"),
                @ForeignKey(entity = WeaponRoom.class,
                            parentColumns = "id",
                            childColumns = "weaponId")
        })
public class SalmonWeaponRoom {
    int weaponId;
    int shiftId;

    public SalmonWeaponRoom(int weaponId, int shiftId){
        this.weaponId = weaponId;
        this.shiftId = shiftId;
    }
}
