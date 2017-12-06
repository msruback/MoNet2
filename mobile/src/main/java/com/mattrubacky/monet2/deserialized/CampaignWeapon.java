package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignWeapon {
    public CampaignWeapon(){}

    @SerializedName("category")
    int category;
    @SerializedName("image")
    String url;
    @SerializedName("weapon_level")
    int level;
    @SerializedName("clear_time")
    long time;
}
