package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestStats implements Parcelable{
    public SplatfestStats(){}

    @SerializedName("wins")
    public int wins;
    @SerializedName("losses")
    public int losses;
    @SerializedName("sameTeam")
    public int sameTeam;
    @SerializedName("grade")
    public String grade;
    @SerializedName("disconnects")
    public int disconnects;
    @SerializedName("power")
    public int power;
    @SerializedName("played")
    public long timePlayed;

    @SerializedName("playerInkStats")
    public int[] playerInkStats;

    @SerializedName("playerKillStats")
    public int[] playerKillStats;

    @SerializedName("playerDeathStats")
    public int[] playerDeathStats;

    @SerializedName("playerSpecialStats")
    public int[] playerSpecialStats;

    @SerializedName("playerInkAverage")
    public float playerInkAverage;

    @SerializedName("playerKillAverage")
    public float playerKillAverage;

    @SerializedName("playerDeathAverage")
    public float playerDeathAverage;

    @SerializedName("playerSpecialAverage")
    public float playerSpecialAverage;

    @SerializedName("teamInkStats")
    public int[] teamInkStats;

    @SerializedName("teamKillStats")
    public int[] teamKillStats;

    @SerializedName("teamDeathStats")
    public int[] teamDeathStats;

    @SerializedName("teamSpecialStats")
    public int[] teamSpecialStats;



    protected SplatfestStats(Parcel in) {
        wins = in.readInt();
        losses = in.readInt();
        grade = in.readString();
        disconnects = in.readInt();
        power = in.readInt();
        timePlayed = in.readLong();
        playerInkStats = in.createIntArray();
        playerKillStats = in.createIntArray();
        playerDeathStats = in.createIntArray();
        playerSpecialStats = in.createIntArray();
        playerInkAverage = in.readFloat();
        playerKillAverage = in.readFloat();
        playerDeathAverage = in.readFloat();
        playerSpecialAverage = in.readFloat();
        teamInkStats = in.createIntArray();
        teamKillStats = in.createIntArray();
        teamDeathStats = in.createIntArray();
        teamSpecialStats = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeString(grade);
        dest.writeInt(disconnects);
        dest.writeInt(power);
        dest.writeLong(timePlayed);
        dest.writeIntArray(playerInkStats);
        dest.writeIntArray(playerKillStats);
        dest.writeIntArray(playerDeathStats);
        dest.writeIntArray(playerSpecialStats);
        dest.writeFloat(playerInkAverage);
        dest.writeFloat(playerKillAverage);
        dest.writeFloat(playerDeathAverage);
        dest.writeFloat(playerSpecialAverage);
        dest.writeIntArray(teamInkStats);
        dest.writeIntArray(teamKillStats);
        dest.writeIntArray(teamDeathStats);
        dest.writeIntArray(teamSpecialStats);
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
