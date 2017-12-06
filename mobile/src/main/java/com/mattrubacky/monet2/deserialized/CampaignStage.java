package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStage {
    public CampaignStage(){}

    @SerializedName("area")
    int area;
    @SerializedName("id")
    int id;
    @SerializedName("is_boss")
    boolean isBoss;
}
