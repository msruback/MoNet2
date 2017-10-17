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
    public Map<Integer,StageStats> stageStats;

    //Past Splatfest performance
    @SerializedName("fes_results")
    public Map<Integer,SplatfestRecords> splatfestRecords;

    //The Player currently
    @SerializedName("player")
    public User user;

    //The player's id
    @SerializedName("unique_id")
    public String unique_id;

    //The stats the user has on their weapons
    @SerializedName("weapon_stats")
    public Map<Integer,WeaponStats> weaponStats;

}
