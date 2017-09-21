package com.mattrubacky.monet2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 9/20/2017.
 */

public class Timeline {
    public Timeline(){};
    @SerializedName("unique_id")
    String uniqueID;

    @SerializedName("coop")
    GrizzCo currentRun;
}
class GrizzCo{
    public GrizzCo(){};
}
