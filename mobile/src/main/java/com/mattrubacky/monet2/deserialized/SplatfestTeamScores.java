package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestTeamScores{
    public SplatfestTeamScores(){}

    @SerializedName("alpha_solo")
    int alphaSolo;
    @SerializedName("alpha_team")
    int alphaTeam;
    @SerializedName("bravo_solo")
    int bravoSolo;
    @SerializedName("bravo_team")
    int bravoTeam;
}
