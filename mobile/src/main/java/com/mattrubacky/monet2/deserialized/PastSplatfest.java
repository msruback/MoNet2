package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response of the festivals/pasts endpoint
 */
public class PastSplatfest{
    public PastSplatfest(){}

    //Past Festivals
    @SerializedName("festivals")
    ArrayList<Splatfest> splatfests;

    //Festival Results
    //IMPORTANT: The order of this array is not necessarily, and probably isn't the same as the order of the festival array
    @SerializedName("results")
    ArrayList<SplatfestResult> results;
}
