package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response of the festivals/pasts endpoint
 */
public class PastSplatfest implements Parcelable{
    public PastSplatfest(){}

    //Past Festivals
    @SerializedName("festivals")
    public ArrayList<Splatfest> splatfests;

    //Festival Results
    //IMPORTANT: The order of this array is not necessarily, and probably isn't the same as the order of the festival array
    @SerializedName("results")
    public ArrayList<SplatfestResult> results;

    protected PastSplatfest(Parcel in) {
        splatfests = in.createTypedArrayList(Splatfest.CREATOR);
        results = in.createTypedArrayList(SplatfestResult.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(splatfests);
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PastSplatfest> CREATOR = new Creator<PastSplatfest>() {
        @Override
        public PastSplatfest createFromParcel(Parcel in) {
            return new PastSplatfest(in);
        }

        @Override
        public PastSplatfest[] newArray(int size) {
            return new PastSplatfest[size];
        }
    };
}
