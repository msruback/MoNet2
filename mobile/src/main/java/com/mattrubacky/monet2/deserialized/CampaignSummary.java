package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignSummary {
    public CampaignSummary(){}

    @SerializedName("clear_rate")
    double clear;
    @SerializedName("honor")
    Honor honor;
    @SerializedName("weapon_cleared_info")
    Map<Integer,Boolean> cleared;
}
