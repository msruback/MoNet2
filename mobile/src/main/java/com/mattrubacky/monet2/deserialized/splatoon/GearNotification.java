package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an Alert set for certain gear
 * This is used for both Battle Gear Notifications and Shop Notifications
 */
public class GearNotification implements Parcelable {
    public GearNotification(){}

    //The gear that notifications are requested for
    @SerializedName("gear")
    public Gear gear;

    //The ability that is requested for the gear. If the id is -1, abilities don't matter
    @SerializedName("ability")
    public Skill skill;

    protected GearNotification(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Creator<GearNotification> CREATOR = new Creator<GearNotification>() {
        @Override
        public GearNotification createFromParcel(Parcel in) {
            return new GearNotification(in);
        }

        @Override
        public GearNotification[] newArray(int size) {
            return new GearNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(skill, flags);
    }
}
