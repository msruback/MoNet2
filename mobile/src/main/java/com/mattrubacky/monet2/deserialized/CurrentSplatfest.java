package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
public class CurrentSplatfest implements Parcelable {
    public CurrentSplatfest(){}
    @SerializedName("festivals")
    ArrayList<Splatfest> splatfests;

    protected CurrentSplatfest(Parcel in) {
        splatfests = in.createTypedArrayList(Splatfest.CREATOR);
    }

    public static final Creator<CurrentSplatfest> CREATOR = new Creator<CurrentSplatfest>() {
        @Override
        public CurrentSplatfest createFromParcel(Parcel in) {
            return new CurrentSplatfest(in);
        }

        @Override
        public CurrentSplatfest[] newArray(int size) {
            return new CurrentSplatfest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(splatfests);
    }
}
