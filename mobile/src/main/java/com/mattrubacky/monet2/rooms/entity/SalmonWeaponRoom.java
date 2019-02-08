package com.mattrubacky.monet2.rooms.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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
    @ColumnInfo(name = "weapon_id")
    int weaponId;
    @ColumnInfo(name = "shift_id")
    int shiftId;

    public SalmonWeaponRoom(int weaponId, int shiftId){
        this.weaponId = weaponId;
        this.shiftId = shiftId;
    }
}
