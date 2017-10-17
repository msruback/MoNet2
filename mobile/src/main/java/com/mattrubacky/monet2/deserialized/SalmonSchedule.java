package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * The list of Salmon Run shifts
 */
public class SalmonSchedule implements Parcelable {
    public SalmonSchedule(){}

    @SerializedName("schedule")
    ArrayList<SalmonRun> schedule;

    protected SalmonSchedule(Parcel in) {
        schedule = in.createTypedArrayList(SalmonRun.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(schedule);
    }
}
