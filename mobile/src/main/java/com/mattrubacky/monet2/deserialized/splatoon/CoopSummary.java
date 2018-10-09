package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/6/2018.
 */

public class CoopSummary implements Parcelable{
    public CoopSummary(){}

    @SerializedName("card")
    CoopCard coopCard;

    protected CoopSummary(Parcel in) {
        coopCard = in.readParcelable(CoopCard.class.getClassLoader());
    }

    public static final Creator<CoopSummary> CREATOR = new Creator<CoopSummary>() {
        @Override
        public CoopSummary createFromParcel(Parcel in) {
            return new CoopSummary(in);
        }

        @Override
        public CoopSummary[] newArray(int size) {
            return new CoopSummary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coopCard, flags);
    }
}
