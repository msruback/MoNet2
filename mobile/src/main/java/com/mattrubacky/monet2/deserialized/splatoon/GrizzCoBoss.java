package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/8/2018.
 */

public class GrizzCoBoss implements Parcelable{
    public GrizzCoBoss(){}

    @SerializedName("name")
    public String name;

    protected GrizzCoBoss(Parcel in) {
        name = in.readString();
    }

    public static final Creator<GrizzCoBoss> CREATOR = new Creator<GrizzCoBoss>() {
        @Override
        public GrizzCoBoss createFromParcel(Parcel in) {
            return new GrizzCoBoss(in);
        }

        @Override
        public GrizzCoBoss[] newArray(int size) {
            return new GrizzCoBoss[size];
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
