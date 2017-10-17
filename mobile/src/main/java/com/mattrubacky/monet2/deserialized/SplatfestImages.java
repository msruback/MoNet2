package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestImages implements Parcelable {
    public SplatfestImages(){}

    @SerializedName("panel")
    String panel;
    @SerializedName("alpha")
    String alpha;
    @SerializedName("bravo")
    String bravo;

    protected SplatfestImages(Parcel in) {
        panel = in.readString();
        alpha = in.readString();
        bravo = in.readString();
    }

    public static final Creator<SplatfestImages> CREATOR = new Creator<SplatfestImages>() {
        @Override
        public SplatfestImages createFromParcel(Parcel in) {
            return new SplatfestImages(in);
        }

        @Override
        public SplatfestImages[] newArray(int size) {
            return new SplatfestImages[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(panel);
        dest.writeString(alpha);
        dest.writeString(bravo);
    }
}
