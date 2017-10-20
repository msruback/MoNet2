package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/19/2017.
 * A Class to encompass both the Splatfest and SplatfestResult
 */

public class SplatfestDatabase {
    public SplatfestDatabase(){}

    @SerializedName("splatfest")
    public Splatfest splatfest;

    @SerializedName("result")
    public SplatfestResult result;
}
