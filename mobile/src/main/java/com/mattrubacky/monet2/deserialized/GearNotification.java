package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
class GearNotification implements Parcelable {
    public GearNotification(){}
    @SerializedName("gear")
    Gear gear;
    @SerializedName("ability")
    Skill skill;
    @SerializedName("notified")
    ArrayList<Product> notified;

    protected GearNotification(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        skill = in.readParcelable(Skill.class.getClassLoader());
        notified = in.createTypedArrayList(Product.CREATOR);
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
        dest.writeTypedList(notified);
    }
}
