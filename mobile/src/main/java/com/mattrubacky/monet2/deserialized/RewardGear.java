package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the gear of the month at GrizzCo
 */
public class RewardGear{
    public RewardGear(){}

    //The first date the gear is available
    @SerializedName("available_time")
    long available;

    //The gear itself
    @SerializedName("gear")
    Gear gear;
}
