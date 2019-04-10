package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "salmon_weapons",
        primaryKeys = {"weapon_id","shift_id"},
        foreignKeys = {
                @ForeignKey(entity = SalmonShiftRoom.class,
                            parentColumns = "id",
                            childColumns = "shift_id")
        })
public class SalmonWeaponRoom {
    @ColumnInfo(name = "weapon_id")
    public int weaponId;
    @ColumnInfo(name = "shift_id")
    public int shiftId;

    public SalmonWeaponRoom(int weaponId, int shiftId){
        this.weaponId = weaponId;
        this.shiftId = shiftId;
    }
}
