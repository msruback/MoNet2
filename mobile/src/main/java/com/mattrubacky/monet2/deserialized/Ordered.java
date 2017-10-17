package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class Ordered{
    public Ordered(){}

    @SerializedName("gear")
    Gear gear;
    @SerializedName("price")
    String price;
    @SerializedName("skill")
    Skill skill;

}
