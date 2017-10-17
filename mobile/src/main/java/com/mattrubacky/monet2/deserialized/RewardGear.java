package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the gear of the month at GrizzCo
 */
public class RewardGear{
    public RewardGear(){}

    //The first date the gear is available
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("available_time")
    public long available;

    //The gear itself
    @SerializedName("gear")
    public Gear gear;
}
