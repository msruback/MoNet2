package com.mattrubacky.monet2.deserialized.splatoon;

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

    @SerializedName("is_x")
    public Boolean isX;


    protected Rank(Parcel in) {
        rank = in.readString();
        sPlus = in.readString();
        isX = in.readInt() !=0;
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
        if(isX==null){
            isX=false;
        }
        dest.writeString(rank);
        dest.writeString(sPlus);
        dest.writeInt(isX ? 1:0);
    }
}
