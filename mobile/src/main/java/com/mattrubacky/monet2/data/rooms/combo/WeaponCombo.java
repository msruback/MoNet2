package com.mattrubacky.monet2.data.rooms.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Special;
import com.mattrubacky.monet2.data.deserialized.splatoon.Sub;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

public class WeaponCombo {
    public Weapon weapon;
    public Sub sub;
    public Special special;

    public Weapon toDeserialized(){
        weapon.sub = sub;
        weapon.special = special;
        return weapon;
    }
}
