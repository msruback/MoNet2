package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestParticipants{
    public SplatfestParticipants(){}

    @SerializedName("alpha")
    int alpha;
    @SerializedName("bravo")
    int bravo;
}
