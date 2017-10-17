package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class TeamTheme implements Parcelable {
    public TeamTheme(){}
    @SerializedName("color")
    SplatfestColor color;
    @SerializedName("key")
    String key;
    @SerializedName("name")
    String name;

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
