package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class hold the Theme of a Splatfest Team
 */
public class TeamTheme implements Parcelable {
    public TeamTheme(){}

    //The Color of the team
    @SerializedName("color")
    public SplatfestColor color;

    //Either "alpha" or "bravo", refering to whether the team is on Side Alpha or Side Bravo respectively.
    @SerializedName("key")
    public String key;

    //The short name of the side(Check SplatfestNames for more detail)
    @SerializedName("name")
    public String name;

    protected TeamTheme(Parcel in) {
        color = in.readParcelable(SplatfestColor.class.getClassLoader());
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<TeamTheme> CREATOR = new Creator<TeamTheme>() {
        @Override
        public TeamTheme createFromParcel(Parcel in) {
            return new TeamTheme(in);
        }

        @Override
        public TeamTheme[] newArray(int size) {
            return new TeamTheme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(color, flags);
        dest.writeString(key);
        dest.writeString(name);
    }
}
