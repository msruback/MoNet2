package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/20/2017.
 */

public class SplatfestRates implements Parcelable{
    public SplatfestRates(){}

    @SerializedName("vote")
    public Rates vote;
    @SerializedName("solo")
    public Rates solo;
    @SerializedName("team")
    public Rates team;

    protected SplatfestRates(Parcel in) {
        vote = in.readParcelable(Rates.class.getClassLoader());
        solo = in.readParcelable(Rates.class.getClassLoader());
        team = in.readParcelable(Rates.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(vote, flags);
        dest.writeParcelable(solo, flags);
        dest.writeParcelable(team, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestRates> CREATOR = new Creator<SplatfestRates>() {
        @Override
        public SplatfestRates createFromParcel(Parcel in) {
            return new SplatfestRates(in);
        }

        @Override
        public SplatfestRates[] newArray(int size) {
            return new SplatfestRates[size];
        }
    };
}
