package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/9/2017.
 * This is the root of the api/coop_schedules, it contains a list of detailed Salmon Runs and a list of Salmon Run times
 */

public class SalmonSchedule implements Parcelable{
    public SalmonSchedule(){}

    @SerializedName("details")
    public ArrayList<SalmonRunDetail> details;
    @SerializedName("schedules")
    public ArrayList<SalmonRun> times;

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
