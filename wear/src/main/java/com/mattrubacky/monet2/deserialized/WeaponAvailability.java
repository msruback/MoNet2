package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/1/2017.
 * This is part of the timeline and will say when new weapons are out
 */

public class WeaponAvailability implements Parcelable{
    public WeaponAvailability(){}

    @SerializedName("release_time")
    public Long release;
    @SerializedName("weapon")
    public Weapon weapon;

    protected WeaponAvailability(Parcel in) {
        release = in.readLong();
        weapon = in.readParcelable(Weapon.class.getClassLoader());
    }

    public static final Creator<WeaponAvailability> CREATOR = new Creator<WeaponAvailability>() {
        @Override
        public WeaponAvailability createFromParcel(Parcel in) {
            return new WeaponAvailability(in);
        }

        @Override
        public WeaponAvailability[] newArray(int size) {
            return new WeaponAvailability[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(release);
        dest.writeParcelable(weapon, flags);
    }
}
