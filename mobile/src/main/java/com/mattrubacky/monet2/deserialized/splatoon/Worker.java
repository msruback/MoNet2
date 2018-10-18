package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Special;

import java.util.ArrayList;

/**
 * Created by mattr on 10/8/2018.
 */

public class Worker implements Parcelable{
    public Worker(){}

    @SerializedName("pid")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("ikura_num")
    public int powerEggs;

    @SerializedName("golden_ikura_num")
    public int goldenEggs;

    @SerializedName("dead_count")
    public int deadCount;

    @SerializedName("help_count")
    public int helpCount;

    @SerializedName("special")
    public Special special;

    @SerializedName("special_counts")
    public ArrayList<Integer> specialCounts;

    @SerializedName("weapon_list")
    public ArrayList<SalmonRunWeapon> weapons;

    @SerializedName("boss_kill_counts")
    public ArrayList<GrizzCoBossKills> bossKillses;

    protected Worker(Parcel in) {
        id = in.readString();
        name = in.readString();
        powerEggs = in.readInt();
        goldenEggs = in.readInt();
        deadCount = in.readInt();
        helpCount = in.readInt();
        special = in.readParcelable(Special.class.getClassLoader());
        in.readList(specialCounts,Integer.class.getClassLoader());
        weapons = in.createTypedArrayList(SalmonRunWeapon.CREATOR);
        bossKillses = in.createTypedArrayList(GrizzCoBossKills.CREATOR);
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(powerEggs);
        dest.writeInt(goldenEggs);
        dest.writeInt(deadCount);
        dest.writeInt(helpCount);
        dest.writeParcelable(special, flags);
        dest.writeList(specialCounts);
        dest.writeTypedList(weapons);
        dest.writeTypedList(bossKillses);
    }
}
