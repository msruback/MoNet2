package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonRun implements Parcelable{
    public SalmonRun(){}

    @SerializedName("start_time")
    long start;

    @SerializedName("end_time")
    long end;

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
