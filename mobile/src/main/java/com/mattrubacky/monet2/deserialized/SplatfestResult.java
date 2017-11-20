package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Only present in the festivals/pasts endpoint
 * The Results of a Splatfest
 */
public class SplatfestResult implements Parcelable{
    public SplatfestResult(){}

    @SerializedName("festival_id")
    public int id;
    @SerializedName("team_scores")
    public SplatfestTeamScores teamScores;
    @SerializedName("summary")
    public SplatfestSummary summary;
    @SerializedName("team_participants")
    public SplatfestParticipants participants;
    @SerializedName("rates")
    public SplatfestRates rates;


    protected SplatfestResult(Parcel in) {
        id = in.readInt();
        teamScores = in.readParcelable(SplatfestTeamScores.class.getClassLoader());
        summary = in.readParcelable(SplatfestSummary.class.getClassLoader());
        participants = in.readParcelable(SplatfestParticipants.class.getClassLoader());
        rates = in.readParcelable(SplatfestRates.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(teamScores, flags);
        dest.writeParcelable(summary, flags);
        dest.writeParcelable(participants, flags);
        dest.writeParcelable(rates, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestResult> CREATOR = new Creator<SplatfestResult>() {
        @Override
        public SplatfestResult createFromParcel(Parcel in) {
            return new SplatfestResult(in);
        }

        @Override
        public SplatfestResult[] newArray(int size) {
            return new SplatfestResult[size];
        }
    };
}
