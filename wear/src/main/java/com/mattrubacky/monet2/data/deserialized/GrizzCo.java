package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the current run in Salmon Run from the Splatnet API
 * I'm only using it to grab the monthly reward gear
 */
public class GrizzCo implements Parcelable{
    public GrizzCo(){}

    @SerializedName("reward_gear")
    public RewardGear rewardGear;

    protected GrizzCo(Parcel in) {
        rewardGear = in.readParcelable(RewardGear.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(rewardGear, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GrizzCo> CREATOR = new Creator<GrizzCo>() {
        @Override
        public GrizzCo createFromParcel(Parcel in) {
            return new GrizzCo(in);
        }

        @Override
        public GrizzCo[] newArray(int size) {
            return new GrizzCo[size];
        }
    };
}
