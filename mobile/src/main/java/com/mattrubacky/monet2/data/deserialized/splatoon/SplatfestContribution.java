package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 9/27/2018.
 */

public class SplatfestContribution implements Parcelable{
    public SplatfestContribution(){
    }

    @SerializedName("regular")
    public float regular;
    @SerializedName("challenge")
    public float challenge;

    protected SplatfestContribution(Parcel in) {
        regular = in.readFloat();
        challenge = in.readFloat();
    }

    public static final Creator<SplatfestContribution> CREATOR = new Creator<SplatfestContribution>() {
        @Override
        public SplatfestContribution createFromParcel(Parcel in) {
            return new SplatfestContribution(in);
        }

        @Override
        public SplatfestContribution[] newArray(int size) {
            return new SplatfestContribution[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(regular);
        dest.writeFloat(challenge);
    }
}
