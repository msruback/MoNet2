package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 9/27/2018.
 */

public class UserClout implements Parcelable{
    public UserClout(){}

    @SerializedName("average")
    public float average;

    @SerializedName("total")
    public long total;


    protected UserClout(Parcel in) {
        average = in.readFloat();
        total = in.readLong();
    }

    public static final Creator<UserClout> CREATOR = new Creator<UserClout>() {
        @Override
        public UserClout createFromParcel(Parcel in) {
            return new UserClout(in);
        }

        @Override
        public UserClout[] newArray(int size) {
            return new UserClout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(average);
        dest.writeLong(total);
    }
}
