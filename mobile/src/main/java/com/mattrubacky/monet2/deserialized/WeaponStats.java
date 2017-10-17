package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Weapon;


class WeaponStats{
    public WeaponStats(){}

    @SerializedName("win_meter")
    Float winMeter;
    @SerializedName("win_count")
    int wins;
    @SerializedName("lose_count")
    int losses;
    @SerializedName("last_use_time")
    Long lastUsed;
    @SerializedName("max_win_meter")
    Float maxWinMeter;
    @SerializedName("total_paint_point")
    Long totalPaintPoint;
    @SerializedName("weapon")
    Weapon weapon;
}


