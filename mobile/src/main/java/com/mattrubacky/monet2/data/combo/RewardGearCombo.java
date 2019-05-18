package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;

import androidx.room.Embedded;

public class RewardGearCombo {
    @Embedded
    public RewardGear rewardGear;

    @Embedded
    public GearBrand gear;

    public RewardGear toDeserialized(){
        rewardGear.gear = gear.toDeserialized();
        return rewardGear;
    }
}
