package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Only present in the festivals/pasts endpoint
 * The Results of a Splatfest
 */
public class SplatfestResult{
    public SplatfestResult(){}

    @SerializedName("festival_id")
    public int id;
    @SerializedName("team_scores")
    public SplatfestTeamScores teamScores;
    @SerializedName("summary")
    public SplatfestSummary summary;
    @SerializedName("team_participants")
    public SplatfestParticipants participants;
}