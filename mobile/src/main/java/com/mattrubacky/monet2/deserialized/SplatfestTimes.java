package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class SplatfestTimes implements Parcelable {
    public SplatfestTimes(){}

    @SerializedName("start")
    Long start;
    @SerializedName("end")
    Long end;
    @SerializedName("announce")
    Long announce;
    @SerializedName("result")
    Long result;

    protected SplatfestTimes(Parcel in) {
        start = in.readLong();
        end = in.readLong();
        announce = in.readLong();
        result = in.readLong();
    }

    public static final Creator<SplatfestTimes> CREATOR = new Creator<SplatfestTimes>() {
        @Override
        public SplatfestTimes createFromParcel(Parcel in) {
            return new SplatfestTimes(in);
        }

        @Override
        public SplatfestTimes[] newArray(int size) {
            return new SplatfestTimes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(start);
        dest.writeLong(end);
        dest.writeLong(announce);
        dest.writeLong(result);
    }
}
