package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class holds the various strings refering to both sides in a Splatfest
 * Part of the Splatfest object
 */
public class SplatfestNames implements Parcelable {
    public SplatfestNames(){}

    //The name of side Alpha
    @SerializedName("alpha_short")
    public String alpha;

    //A brief description of side Alpha
    @SerializedName("alpha_long")
    public String alphaDesc;

    //The name of side Bravo
    @SerializedName("bravo_short")
    public String bravo;

    //A brief description of side Bravo
    @SerializedName("bravo_long")
    public String bravoDesc;

    protected SplatfestNames(Parcel in) {
        bravo = in.readString();
        bravoDesc = in.readString();
        alpha = in.readString();
        alphaDesc = in.readString();
    }

    public static final Creator<SplatfestNames> CREATOR = new Creator<SplatfestNames>() {
        @Override
        public SplatfestNames createFromParcel(Parcel in) {
            return new SplatfestNames(in);
        }

        @Override
        public SplatfestNames[] newArray(int size) {
            return new SplatfestNames[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bravo);
        dest.writeString(bravoDesc);
        dest.writeString(alpha);
        dest.writeString(alphaDesc);
    }
}
