package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a piece of gear stored in the "Closet"
 * Not a part of the Splatnet API
 */

public class ClosetHanger implements Parcelable{
    public ClosetHanger(){
    }

    @SerializedName("gear")
    public Gear gear;
    @SerializedName("skills")
    public GearSkills skills;
    @SerializedName("last_use_time")
    public Long time;
    @SerializedName("ink_stats")
    public int[] inkStats;
    @SerializedName("kill_stats")
    public int[] killStats;
    @SerializedName("death_stats")
    public int[] deathStats;
    @SerializedName("special_stats")
    public int[] specialStats;
    @SerializedName("num_games")
    public int numGames;

    protected ClosetHanger(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        skills = in.readParcelable(GearSkills.class.getClassLoader());
        time = in.readLong();
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readInt();
    }

    public static final Creator<ClosetHanger> CREATOR = new Creator<ClosetHanger>() {
        @Override
        public ClosetHanger createFromParcel(Parcel in) {
            return new ClosetHanger(in);
        }

        @Override
        public ClosetHanger[] newArray(int size) {
            return new ClosetHanger[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(skills, flags);
        dest.writeLong(time);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeInt(numGames);
    }
}
