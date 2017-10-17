package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class holds colors for both sides in a Splatfest
 * Alpha is the first side, supported by Pearl
 * Bravo is the second side, supported by Marina
 * Part of the Splatfest Object
 */
public class SplatfestColors implements Parcelable {
    public SplatfestColors(){
    }

    //Side Alpha's colors.
    @SerializedName("alpha")
    SplatfestColor alpha;

    //Side Bravo's colors
    @SerializedName("bravo")
    SplatfestColor bravo;

    protected SplatfestColors(Parcel in) {
        alpha = in.readParcelable(SplatfestColor.class.getClassLoader());
        bravo = in.readParcelable(SplatfestColor.class.getClassLoader());
    }

    public static final Creator<SplatfestColors> CREATOR = new Creator<SplatfestColors>() {
        @Override
        public SplatfestColors createFromParcel(Parcel in) {
            return new SplatfestColors(in);
        }

        @Override
        public SplatfestColors[] newArray(int size) {
            return new SplatfestColors[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(alpha, flags);
        dest.writeParcelable(bravo, flags);
    }
}
