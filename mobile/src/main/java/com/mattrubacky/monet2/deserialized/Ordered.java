package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the gear the user ordered but has yet to pick up from Merch
 */
public class Ordered{
    public Ordered(){}

    //The gear ordered
    @SerializedName("gear")
    public Gear gear;

    //The price
    @SerializedName("price")
    public String price;

    //The main ability
    @SerializedName("skill")
    public Skill skill;

}
