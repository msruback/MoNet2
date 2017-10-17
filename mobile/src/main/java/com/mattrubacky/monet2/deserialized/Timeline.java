package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.GrizzCo;

/**
 * Created by mattr on 10/17/2017.
 * The root of the timeline endpoint
 */
public class Timeline {
    public Timeline(){}

    //The User's unique id
    @SerializedName("unique_id")
    String uniqueID;

    //The info on Salmon Run
    //Note: coop is only full when Salmon Run is available, otherwise it is empty
    @SerializedName("coop")
    GrizzCo currentRun;
}
