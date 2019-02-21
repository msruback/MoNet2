package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/24/2017.
 */

public class Challenge implements Parcelable{
    public Challenge(){}

    @SerializedName("key")
    public String key;
    @SerializedName("name")
    public String name;
    @SerializedName("paint_points")
    public long paintpoints;
    @SerializedName("image")
    public String url;

    protected Challenge(Parcel in) {
        key = in.readString();
        name = in.readString();
        paintpoints = in.readLong();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeLong(paintpoints);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };
}
