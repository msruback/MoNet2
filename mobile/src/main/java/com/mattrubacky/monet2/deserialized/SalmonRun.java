package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.Weapon;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
class SalmonRun implements Parcelable {
    public SalmonRun() {
    }

    @SerializedName("start")
    long startTime;
    @SerializedName("end")
    long endTime;
    @SerializedName("stage")
    String stage;
    @SerializedName("weapons")
    ArrayList<Weapon> weapons;

    protected SalmonRun(Parcel in) {
        startTime = in.readLong();
        endTime = in.readLong();
        stage = in.readString();
        weapons = in.createTypedArrayList(Weapon.CREATOR);
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
    }
}
