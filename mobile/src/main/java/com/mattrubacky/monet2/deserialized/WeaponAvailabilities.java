package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/1/2017.
 */

public class WeaponAvailabilities implements Parcelable{
    public WeaponAvailabilities(){}

    @SerializedName("availabilities")
    public ArrayList<WeaponAvailability> newWeapons;

    protected WeaponAvailabilities(Parcel in) {
        newWeapons = in.createTypedArrayList(WeaponAvailability.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(newWeapons);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeaponAvailabilities> CREATOR = new Creator<WeaponAvailabilities>() {
        @Override
        public WeaponAvailabilities createFromParcel(Parcel in) {
            return new WeaponAvailabilities(in);
        }

        @Override
        public WeaponAvailabilities[] newArray(int size) {
            return new WeaponAvailabilities[size];
        }
    };
}
