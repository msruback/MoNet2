package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the current run in Salmon Run from the Splatnet API
 * I'm only using it to grab the monthly reward gear
 */
public class GrizzCo{
    public GrizzCo(){}

    @SerializedName("reward_gear")
    RewardGear rewardGear;
}
