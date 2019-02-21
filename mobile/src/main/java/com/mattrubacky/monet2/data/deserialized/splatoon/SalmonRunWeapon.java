package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 7/4/2018.
 */

public class SalmonRunWeapon implements Parcelable {

    public SalmonRunWeapon(){}

    @SerializedName("id")
    public int id;

    @SerializedName("weapon")
    public Weapon weapon;

    protected SalmonRunWeapon(Parcel in) {
        id = in.readInt();
        weapon = in.readParcelable(Weapon.class.getClassLoader());
    }

    public static final Creator<SalmonRunWeapon> CREATOR = new Creator<SalmonRunWeapon>() {
        @Override
        public SalmonRunWeapon createFromParcel(Parcel in) {
            return new SalmonRunWeapon(in);
        }

        @Override
        public SalmonRunWeapon[] newArray(int size) {
            return new SalmonRunWeapon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(weapon, flags);
    }
}
