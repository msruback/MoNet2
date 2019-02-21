package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class Honor implements Parcelable{
    public Honor(){}

    @SerializedName("name")
    public String name;

    protected Honor(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Honor> CREATOR = new Creator<Honor>() {
        @Override
        public Honor createFromParcel(Parcel in) {
            return new Honor(in);
        }

        @Override
        public Honor[] newArray(int size) {
            return new Honor[size];
        }
    };
}
