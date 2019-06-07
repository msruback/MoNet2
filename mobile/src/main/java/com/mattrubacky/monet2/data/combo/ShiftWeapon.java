package com.mattrubacky.monet2.data.combo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.mattrubacky.monet2.data.deserialized_entities.Weapon;

public class ShiftWeapon {
    @ColumnInfo(name="weapon_shift_id")
    public Integer id;
    @Embedded
    public Weapon weapon;

    public ShiftWeapon(int id, Weapon weapon){
        this.id = id;
        this.weapon = weapon;
    }
}
