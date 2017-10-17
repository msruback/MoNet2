package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the break down of which side won which category
 * 0 is an alpha win, 1 is a bravo win
 * Part of the SplatfestResult object
 */
public class SplatfestSummary{
    public SplatfestSummary(){}

    //The result in the Team Battle category
    @SerializedName("team")
    public int team;

    //The result in the Solo Battle category
    @SerializedName("solo")
    public int solo;

    //The result of the Vote
    @SerializedName("vote")
    public int vote;

    //The result of the Splatfest Overall
    @SerializedName("total")
    public int total;
}
