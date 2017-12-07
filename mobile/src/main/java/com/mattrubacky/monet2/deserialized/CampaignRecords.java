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
    public Map<Integer,CampaignWeapon> weaponMap;
    @SerializedName("summary")
    public CampaignSummary summary;
    @SerializedName("stage_infos")
    public ArrayList<CampaignStageInfo> info;
}
