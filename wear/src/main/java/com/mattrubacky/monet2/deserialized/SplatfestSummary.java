package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the break down of which side won which category
 * 0 is an alpha win, 1 is a bravo win
 * Part of the SplatfestResult object
 */
public class SplatfestSummary implements Parcelable{
    public SplatfestSummary(){}

    //The result in the Team Battle category
    @SerializedName("team")
    public int team;

    //The result in the Solo Battle category
    @SerializedName("solo")
    public int solo;

    //The result of the Vote
    @SerializedName("vote")
    public int vote;

    //The result of the Splatfest Overall
    @SerializedName("total")
    public int total;

    protected SplatfestSummary(Parcel in) {
        team = in.readInt();
        solo = in.readInt();
        vote = in.readInt();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(team);
        dest.writeInt(solo);
        dest.writeInt(vote);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestSummary> CREATOR = new Creator<SplatfestSummary>() {
        @Override
        public SplatfestSummary createFromParcel(Parcel in) {
            return new SplatfestSummary(in);
        }

        @Override
        public SplatfestSummary[] newArray(int size) {
            return new SplatfestSummary[size];
        }
    };
}
