package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.SplatfestSummary;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.SplatfestTeamScores;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestResult{
    public SplatfestResult(){}

    @SerializedName("festival_id")
    int id;
    @SerializedName("team_scores")
    SplatfestTeamScores teamScores;
    @SerializedName("summary")
    SplatfestSummary summary;
    @SerializedName("team_participants")
    SplatfestParticipants participants;
}
