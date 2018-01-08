package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the gear of the month at GrizzCo
 */
public class RewardGear implements Parcelable{
    public RewardGear(){}

    //The first date the gear is available
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("available_time")
    public long available;

    //The gear itself
    @SerializedName("gear")
    public Gear gear;

    protected RewardGear(Parcel in) {
        available = in.readLong();
        gear = in.readParcelable(Gear.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(available);
        dest.writeParcelable(gear, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RewardGear> CREATOR = new Creator<RewardGear>() {
        @Override
        public RewardGear createFromParcel(Parcel in) {
            return new RewardGear(in);
        }

        @Override
        public RewardGear[] newArray(int size) {
            return new RewardGear[size];
        }
    };
}
