package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * A User's Grade in a splatfest
 */
public class SplatfestGrade implements Parcelable {
    public SplatfestGrade(){
    }
    @SerializedName("name")
    String name;

    protected SplatfestGrade(Parcel in) {
        name = in.readString();
    }

    public static final Creator<SplatfestGrade> CREATOR = new Creator<SplatfestGrade>() {
        @Override
        public SplatfestGrade createFromParcel(Parcel in) {
            return new SplatfestGrade(in);
        }

        @Override
        public SplatfestGrade[] newArray(int size) {
            return new SplatfestGrade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
