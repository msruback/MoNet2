package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/1/2017.
 */

public class WeaponAvailabilities {
    public WeaponAvailabilities(){}

    @SerializedName("availablities")
    public ArrayList<WeaponAvailabilty> newWeapons;
}
