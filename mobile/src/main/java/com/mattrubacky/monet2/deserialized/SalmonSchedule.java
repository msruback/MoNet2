package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonSchedule implements Parcelable{
    public SalmonSchedule(){}

    @SerializedName("details")
    ArrayList<SalmonRunDetail> details;
    @SerializedName("schedules")
    ArrayList<SalmonRun> times;

    protected SalmonSchedule(Parcel in) {
        details = in.createTypedArrayList(SalmonRunDetail.CREATOR);
        times = in.createTypedArrayList(SalmonRun.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(details);
        dest.writeTypedList(times);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalmonSchedule> CREATOR = new Creator<SalmonSchedule>() {
        @Override
        public SalmonSchedule createFromParcel(Parcel in) {
            return new SalmonSchedule(in);
        }

        @Override
        public SalmonSchedule[] newArray(int size) {
            return new SalmonSchedule[size];
        }
    };
}
