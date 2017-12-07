package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignSummary {
    public CampaignSummary(){}

    @SerializedName("clear_rate")
    public double clear;
    @SerializedName("honor")
    public Honor honor;
    @SerializedName("weapon_cleared_info")
    public Map<Integer,Boolean> cleared;
}
