package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.GrizzCo;

/**
 * Created by mattr on 10/17/2017.
 */
public class Timeline {
    public Timeline(){}
    @SerializedName("unique_id")
    String uniqueID;

    @SerializedName("coop")
    GrizzCo currentRun;
}
