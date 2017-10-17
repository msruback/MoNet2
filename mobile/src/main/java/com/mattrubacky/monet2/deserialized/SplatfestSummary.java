package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestSummary{
    public SplatfestSummary(){}

    @SerializedName("team")
    int team;
    @SerializedName("solo")
    int solo;
    @SerializedName("vote")
    int vote;
    @SerializedName("total")
    int total;
}
