package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestStats implements Parcelable{
    public SplatfestStats(){}

    @SerializedName("wins")
    public int wins;
    @SerializedName("losses")
    public int losses;
    @SerializedName("grade")
    public String grade;
    @SerializedName("disconnects")
    public int disconnects;
    @SerializedName("power")
    public int power;
    @SerializedName("played")
    public long timePlayed;

    protected SplatfestStats(Parcel in) {
        wins = in.readInt();
        losses = in.readInt();
        grade = in.readString();
        disconnects = in.readInt();
        power = in.readInt();
        timePlayed = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeString(grade);
        dest.writeInt(disconnects);
        dest.writeInt(power);
        dest.writeLong(timePlayed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestStats> CREATOR = new Creator<SplatfestStats>() {
        @Override
        public SplatfestStats createFromParcel(Parcel in) {
            return new SplatfestStats(in);
        }

        @Override
        public SplatfestStats[] newArray(int size) {
            return new SplatfestStats[size];
        }
    };
}
