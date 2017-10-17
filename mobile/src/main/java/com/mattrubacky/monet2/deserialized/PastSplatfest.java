package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.SplatfestResult;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
class PastSplatfest{
    public PastSplatfest(){}

    @SerializedName("festivals")
    ArrayList<Splatfest> splatfests;
    @SerializedName("results")
    ArrayList<SplatfestResult> results;
}
