package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.SplatfestRecords;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.StageStats;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.User;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.WeaponStats;

import java.util.Map;

/**
 * Created by mattr on 10/17/2017.
 */
class Records{
    public Records(){}

    @SerializedName("stage_stats")
    Map<Integer,StageStats> stageStats;
    @SerializedName("fes_results")
    Map<Integer,SplatfestRecords> splatfestRecords;
    @SerializedName("player")
    User user;
    @SerializedName("unique_id")
    String unique_id;
    @SerializedName("weapon_stats")
    Map<Integer,WeaponStats> weaponStats;

}
