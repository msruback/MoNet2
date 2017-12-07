package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignRecords {
    public CampaignRecords(){}
    @SerializedName("weapon_map")
    Map<Integer,CampaignWeapon> weaponMap;
    @SerializedName("summary")
    CampaignSummary summary;
    @SerializedName("stage_infos")
    ArrayList<CampaignStageInfo> info;
}
