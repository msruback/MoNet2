package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Weapon;

/**
 * Created by mattr on 10/17/2017.
 * This class contains Stats on a Weapon
 * For use in the Weapon Locker
 */

public class WeaponStats implements Parcelable{
    public WeaponStats(){}

    //The casual mode "Freshness" meter current level
    @SerializedName("win_meter")
    public Float winMeter;

    //The number of wins using this weapon
    @SerializedName("win_count")
    public int wins;

    //The number of losses using this weapon
    @SerializedName("lose_count")
    public int losses;

    //The last time this weapon was used
    @SerializedName("last_use_time")
    public Long lastUsed;

    //The max value of the "Freshness" meter, used to properly display the flag achieved
    @SerializedName("max_win_meter")
    public Float maxWinMeter;

    //The total amount inked with this weapon
    @SerializedName("total_paint_point")
    public Long totalPaintPoint;

    //The weapon itself
    @SerializedName("weapon")
    public Weapon weapon;

    protected WeaponStats(Parcel in) {
        wins = in.readInt();
        losses = in.readInt();
        weapon = in.readParcelable(Weapon.class.getClassLoader());
    }

    public static final Creator<WeaponStats> CREATOR = new Creator<WeaponStats>() {
        @Override
        public WeaponStats createFromParcel(Parcel in) {
            return new WeaponStats(in);
        }

        @Override
        public WeaponStats[] newArray(int size) {
            return new WeaponStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeParcelable(weapon, flags);
    }
}


