package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;

import androidx.room.Embedded;

public class GearBrand {
    @Embedded
    public Gear gear;

    @Embedded
    public BrandSkill brandSkill;

    public Gear toDeserialized(){
        gear.brand = brandSkill.toDeserialized();
        return gear;
    }
}
