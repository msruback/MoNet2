package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignWeapon {
    public CampaignWeapon(){}

    @SerializedName("category")
    public int category;
    @SerializedName("image")
    public String url;
    @SerializedName("weapon_level")
    public int level;
    @SerializedName("clear_time")
    public long time;
}
