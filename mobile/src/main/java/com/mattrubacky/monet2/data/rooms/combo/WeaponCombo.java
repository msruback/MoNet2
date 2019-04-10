package com.mattrubacky.monet2.data.rooms.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Special;
import com.mattrubacky.monet2.data.deserialized.splatoon.Sub;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import androidx.room.Embedded;

public class WeaponCombo {
    @Embedded
    public Weapon weapon;
    @Embedded
    public Sub sub;
    @Embedded
    public Special special;

    public Weapon toDeserialized(){
        weapon.sub = sub;
        weapon.special = special;
        return weapon;
    }
}
