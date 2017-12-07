package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStage {
    public CampaignStage(){}

    @SerializedName("area")
    public int area;
    @SerializedName("id")
    public int id;
    @SerializedName("is_boss")
    public boolean isBoss;
}
