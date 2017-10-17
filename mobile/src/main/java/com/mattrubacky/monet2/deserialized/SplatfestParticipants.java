package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class contains the amount of participants on each side in a splatfest
 * Part of the Splatfest Result object
 */
public class SplatfestParticipants{
    public SplatfestParticipants(){}

    //Side Alpha's participants
    @SerializedName("alpha")
    public int alpha;

    //Side Bravo's participants
    @SerializedName("bravo")
    public int bravo;
}
