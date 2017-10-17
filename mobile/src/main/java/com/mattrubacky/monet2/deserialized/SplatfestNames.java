package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestNames implements Parcelable {
    public SplatfestNames(){}

    @SerializedName("bravo_short")
    String bravo;
    @SerializedName("bravo_long")
    String bravoDesc;
    @SerializedName("alpha_short")
    String alpha;
    @SerializedName("alpha_long")
    String alphaDesc;

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
