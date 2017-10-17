package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a player's competitive rank in a game mode
 */
public class Rank implements Parcelable {
    public Rank(){}

    //The Rank
    @SerializedName("name")
    public String rank;

    //If the rank is S+ this is the number after that, otherwise null
    @SerializedName("s_plus_number")
    public String sPlus;

    protected Rank(Parcel in) {
        rank = in.readString();
        sPlus = in.readString();
    }

    public static final Creator<Rank> CREATOR = new Creator<Rank>() {
        @Override
        public Rank createFromParcel(Parcel in) {
            return new Rank(in);
        }

        @Override
        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
        dest.writeString(sPlus);
    }
}
