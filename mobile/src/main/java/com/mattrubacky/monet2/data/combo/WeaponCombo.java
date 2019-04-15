package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized_entities.Special;
import com.mattrubacky.monet2.data.deserialized_entities.Sub;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;

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
