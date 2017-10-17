package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Contains the important times in a splatfest
 * Part of the Splatfest object
 */
class SplatfestTimes implements Parcelable {
    public SplatfestTimes(){}

    //The time the Splatfest begins
    @SerializedName("start")
    Long start;

    //The time the Splatfest ends
    @SerializedName("end")
    Long end;

    //The time Pearl and Marina first annouced the Splatfest
    @SerializedName("announce")
    Long announce;

    //The time Pearl and Marina announced the results of the Splatfest
    //Note: As results are only presented after the Splatfest is over, this is only present in the festivals/pasts endpoint
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
