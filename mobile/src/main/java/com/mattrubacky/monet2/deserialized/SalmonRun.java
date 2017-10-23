package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a Salmon Run Shift
 * Note: This is not from the Splatnet API
 */
public class SalmonRun implements Parcelable {
    public SalmonRun() {
    }

    //The time the shift starts
    //Note: This is in milliseconds from epoch
    @SerializedName("start")
    public long startTime;

    //The time the shift ends
    //Note: This is in milliseconds from epoch
    @SerializedName("end")
    public long endTime;

    //The Stage the shift will be on
    //Note: Currently this is only a String not a stage object
    @SerializedName("stage")
    public String stage;

    //The List of weapons available for the run, -1 is mystery
    //Note: Will always be size()==4, however items might be null
    @SerializedName("weapons")
    public ArrayList<Weapon> weapons;

    @SerializedName("notified")
    public boolean notified;


    protected SalmonRun(Parcel in) {
        startTime = in.readLong();
        endTime = in.readLong();
        stage = in.readString();
        weapons = in.createTypedArrayList(Weapon.CREATOR);
        notified = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime);
        dest.writeLong(endTime);
        dest.writeString(stage);
        dest.writeTypedList(weapons);
        dest.writeByte((byte) (notified ? 1 : 0));
    }
}
