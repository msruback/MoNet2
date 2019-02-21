package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/6/2018.
 */

public class CoopResults implements Parcelable{
    public CoopResults(){}

    @SerializedName("results")
    public ArrayList<CoopIds> results;

    @SerializedName("summary")
    public CoopSummary coopSummary;

    protected CoopResults(Parcel in) {
        results = in.createTypedArrayList(CoopIds.CREATOR);
        coopSummary = in.readParcelable(CoopSummary.class.getClassLoader());
    }

    public static final Creator<CoopResults> CREATOR = new Creator<CoopResults>() {
        @Override
        public CoopResults createFromParcel(Parcel in) {
            return new CoopResults(in);
        }

        @Override
        public CoopResults[] newArray(int size) {
            return new CoopResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
        dest.writeParcelable(coopSummary, flags);
    }
}
