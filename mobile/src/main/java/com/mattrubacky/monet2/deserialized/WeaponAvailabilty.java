package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/1/2017.
 * This is part of the timeline and will say when new weapons are out
 */

public class WeaponAvailabilty {
    public WeaponAvailabilty(){}

    @SerializedName("release_time")
    Long release;
    @SerializedName("weapon")
    Weapon weapon;
}
