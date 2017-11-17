package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Part of the Records Endpoint
 * The user's performance in a specific Splatfest
 */
public class SplatfestRecords{
    public SplatfestRecords(){}

    @SerializedName("fes_id")
    public int id;

    //The number of points progressed to the next grade
    @SerializedName("fes_point")
    public int points;

    //The power the user is at
    @SerializedName("fes_power")
    public int power;

    //The splatfest grade the user is at
    @SerializedName("fes_grade")
    public SplatfestGrade grade;
}
