package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/19/2017.
 * A Class to encompass both the Splatfest and SplatfestResult
 */

public class SplatfestDatabase implements Parcelable{
    public SplatfestDatabase(){}

    @SerializedName("splatfest")
    public Splatfest splatfest;

    @SerializedName("result")
    public SplatfestResult result;

    protected SplatfestDatabase(Parcel in) {
        splatfest = in.readParcelable(Splatfest.class.getClassLoader());
        result = in.readParcelable(SplatfestResult.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(splatfest, flags);
        dest.writeParcelable(result, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestDatabase> CREATOR = new Creator<SplatfestDatabase>() {
        @Override
        public SplatfestDatabase createFromParcel(Parcel in) {
            return new SplatfestDatabase(in);
        }

        @Override
        public SplatfestDatabase[] newArray(int size) {
            return new SplatfestDatabase[size];
        }
    };
}
