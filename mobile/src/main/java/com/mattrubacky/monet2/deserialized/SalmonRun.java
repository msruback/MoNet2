package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/9/2017.
 * This Class describes the SalmonRun Start and End Times
 * An Important Note is that this contains not only the times for runs not in details, but the ones in details as well
 */

public class SalmonRun implements Parcelable{
    public SalmonRun(){}

    @SerializedName("start_time")
    public long start;
    @SerializedName("end_time")
    public long end;

    protected SalmonRun(Parcel in) {
        start = in.readLong();
        end = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(start);
        dest.writeLong(end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalmonRun> CREATOR = new Creator<SalmonRun>() {
        @Override
        public SalmonRun createFromParcel(Parcel in) {
            return new SalmonRun(in);
        }

        @Override
        public SalmonRun[] newArray(int size) {
            return new SalmonRun[size];
        }
    };
}
