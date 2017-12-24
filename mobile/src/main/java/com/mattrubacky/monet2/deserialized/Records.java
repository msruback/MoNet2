package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.helper.StageStats;
import com.mattrubacky.monet2.helper.WeaponStats;

import java.util.Map;

/**
 * Created by mattr on 10/17/2017.
 * This class represents records of the user
 */
public class Records implements Parcelable{
    public Records(){}

    //The stats the user has on various stages
    @SerializedName("stage_stats")
    public Map<Integer,StageStats> stageStats;

    //Past Splatfest performance
    @SerializedName("fes_results")
    public Map<Integer,SplatfestRecords> splatfestRecords;

    //The Player currently
    @SerializedName("player")
    public User user;

    @SerializedName("win_count")
    public int wins;

    @SerializedName("lose_count")
    public int losses;

    @SerializedName("start_time")
    public long startTime;

    //The player's id
    @SerializedName("unique_id")
    public String unique_id;

    //The stats the user has on their weapons
    @SerializedName("weapon_stats")
    public Map<Integer,WeaponStats> weaponStats;

    protected Records(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        unique_id = in.readString();
        wins = in.readInt();
        losses = in.readInt();
        in.readMap(stageStats,StageStats.class.getClassLoader());
        in.readMap(splatfestRecords,SplatfestRecords.class.getClassLoader());
        in.readMap(weaponStats,WeaponStats.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(unique_id);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeMap(stageStats);
        dest.writeMap(splatfestRecords);
        dest.writeMap(weaponStats);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Records> CREATOR = new Creator<Records>() {
        @Override
        public Records createFromParcel(Parcel in) {
            return new Records(in);
        }

        @Override
        public Records[] newArray(int size) {
            return new Records[size];
        }
    };
}
