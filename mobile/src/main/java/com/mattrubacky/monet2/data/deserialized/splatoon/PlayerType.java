package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 6/17/2018.
 */

public class PlayerType implements Parcelable{
    public PlayerType(){}

    //The player's species
    @SerializedName("species")
    public String species;

    //The player's style
    @SerializedName("style")
    public String style;

    protected PlayerType(Parcel in) {
        species = in.readString();
        style = in.readString();
    }

    public static final Creator<PlayerType> CREATOR = new Creator<PlayerType>() {
        @Override
        public PlayerType createFromParcel(Parcel in) {
            return new PlayerType(in);
        }

        @Override
        public PlayerType[] newArray(int size) {
            return new PlayerType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(species);
        dest.writeString(style);
    }
}
