package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * The number of wins each side has in both the Solo and Team battle categories
 * Part of the SplatfestResult object
 * Note: Since only Alpha v. Bravo battles count, Alpha wins are Bravo losses, and vice versa
 */
public class SplatfestTeamScores{
    public SplatfestTeamScores(){}

    //Alpha's wins in Solo Battles
    @SerializedName("alpha_solo")
    int alphaSolo;

    //Alpha's wins in Team Battles
    @SerializedName("alpha_team")
    int alphaTeam;

    //Bravo's wins in Solo Battles
    @SerializedName("bravo_solo")
    int bravoSolo;

    //Bravo's wins in Team Battles
    @SerializedName("bravo_team")
    int bravoTeam;
}
