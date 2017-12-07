package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStageInfo {
    public CampaignStageInfo(){}

    @SerializedName("clear_weapons")
    Map<Integer,CampaignWeapon> weapons;
    @SerializedName("stage")
    CampaignStage stage;
}
