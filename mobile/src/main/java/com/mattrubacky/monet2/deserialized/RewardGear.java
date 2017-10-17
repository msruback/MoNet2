package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class RewardGear{
    public RewardGear(){}

    @SerializedName("available_time")
    long available;

    @SerializedName("gear")
    Gear gear;
}
