package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestColors implements Parcelable {
    public SplatfestColors(){
    }
    @SerializedName("alpha")
    SplatfestColor alpha;
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
