package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonRunDetail implements Parcelable{
    public SalmonRunDetail(){}

    @SerializedName("stage")
    SalmonStage stage;
    @SerializedName("weapons")
    ArrayList<Weapon> weapons;
    @SerializedName("start_time")
    long start;
    @SerializedName("end_time")
    long end;

    protected SalmonRunDetail(Parcel in) {
        stage = in.readParcelable(SalmonStage.class.getClassLoader());
        weapons = in.createTypedArrayList(Weapon.CREATOR);
        start = in.readLong();
        end = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
        dest.writeTypedList(weapons);
        dest.writeLong(start);
        dest.writeLong(end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalmonRunDetail> CREATOR = new Creator<SalmonRunDetail>() {
        @Override
        public SalmonRunDetail createFromParcel(Parcel in) {
            return new SalmonRunDetail(in);
        }

        @Override
        public SalmonRunDetail[] newArray(int size) {
            return new SalmonRunDetail[size];
        }
    };
}
