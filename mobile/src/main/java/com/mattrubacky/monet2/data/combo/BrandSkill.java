package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized_entities.Brand;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;

import androidx.room.Embedded;

public class BrandSkill {
    @Embedded
    public Brand brand;
    @Embedded
    public Skill skill;
    public Brand toDeserialized(){
        brand.skill = skill;
        return brand;
    }
}
