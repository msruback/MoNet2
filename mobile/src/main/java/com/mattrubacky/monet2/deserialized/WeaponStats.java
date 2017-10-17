package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Weapon;

/**
 * Created by mattr on 10/17/2017.
 * This class contains Stats on a Weapon
 * For use in the Weapon Locker
 */

public class WeaponStats{
    public WeaponStats(){}

    //The casual mode "Freshness" meter current level
    @SerializedName("win_meter")
    public Float winMeter;

    //The number of wins using this weapon
    @SerializedName("win_count")
    public int wins;

    //The number of losses using this weapon
    @SerializedName("lose_count")
    public int losses;

    //The last time this weapon was used
    @SerializedName("last_use_time")
    public Long lastUsed;

    //The max value of the "Freshness" meter, used to properly display the flag achieved
    @SerializedName("max_win_meter")
    public Float maxWinMeter;

    //The total amount inked with this weapon
    @SerializedName("total_paint_point")
    public Long totalPaintPoint;

    //The weapon itself
    @SerializedName("weapon")
    public Weapon weapon;
}


