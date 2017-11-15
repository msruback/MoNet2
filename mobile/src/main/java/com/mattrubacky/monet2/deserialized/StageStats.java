package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the User's stats on a certain stage
 */
public class StageStats implements Parcelable{
    public StageStats(){}

    //The Stage in question
    @SerializedName("stage")
    public Stage stage;

    //The wins on this Stage under the Rainmaker rule
    @SerializedName("yagura_win")
    public int rainmakerWin;

    //The losses on this Stage under the Rainmaker rule
    @SerializedName("yagura_lose")
    public int rainmakerLose;

    //The wins on this Stage under the Splatzone rule
    @SerializedName("area_win")
    public int splatzonesWin;

    //The losses on this Stage under the Splatzone rule
    @SerializedName("area_lose")
    public int splatzonesLose;

    //The wins on this Stage under the Tower Control rule
    @SerializedName("hoko_win")
    public int towerWin;

    //The losses on this stage under the Tower Control rule
    @SerializedName("hoko_lose")
    public int towerLose;

    //The last time the user played on this stage
    //IMPORTANT: This is in seconds from epoch, Jave takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("last_play_time")
    public Long lastPlayed;

    @SerializedName("inkStats")
    public int[] inkStats;

    @SerializedName("killStats")
    public int[] killStats;

    @SerializedName("deathStats")
    public int[] deathStats;

    @SerializedName("specialStats")
    public int[] specialStats;

    @SerializedName("numGames")
    public long numGames;

    protected StageStats(Parcel in) {
        stage = in.readParcelable(Stage.class.getClassLoader());
        rainmakerWin = in.readInt();
        rainmakerLose = in.readInt();
        splatzonesWin = in.readInt();
        splatzonesLose = in.readInt();
        towerWin = in.readInt();
        towerLose = in.readInt();
        lastPlayed = in.readLong();
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
        dest.writeInt(rainmakerWin);
        dest.writeInt(rainmakerLose);
        dest.writeInt(splatzonesWin);
        dest.writeInt(splatzonesLose);
        dest.writeInt(towerWin);
        dest.writeInt(towerLose);
        dest.writeLong(lastPlayed);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeLong(numGames);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StageStats> CREATOR = new Creator<StageStats>() {
        @Override
        public StageStats createFromParcel(Parcel in) {
            return new StageStats(in);
        }

        @Override
        public StageStats[] newArray(int size) {
            return new StageStats[size];
        }
    };
}
