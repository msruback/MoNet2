package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestRecords{
    public SplatfestRecords(){}

    @SerializedName("fes_point")
    int points;
    @SerializedName("fes_power")
    int power;
    @SerializedName("fes_id")
    int id;
    @SerializedName("fes_grade")
    SplatfestGrade grade;
}
