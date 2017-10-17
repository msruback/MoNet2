package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 10/17/2017.
 * This class represents records of the user
 */
public class Records{
    public Records(){}

    //The stats the user has on various stages
    @SerializedName("stage_stats")
    Map<Integer,StageStats> stageStats;

    //Past Splatfest performance
    @SerializedName("fes_results")
    Map<Integer,SplatfestRecords> splatfestRecords;

    //The Player currently
    @SerializedName("player")
    User user;

    //The player's id
    @SerializedName("unique_id")
    String unique_id;

    //The stats the user has on their weapons
    @SerializedName("weapon_stats")
    Map<Integer,WeaponStats> weaponStats;

}
