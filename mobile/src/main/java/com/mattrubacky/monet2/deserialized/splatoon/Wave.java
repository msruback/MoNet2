package com.mattrubacky.monet2.deserialized.splatoon;

import android.app.usage.UsageEvents;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/9/2018.
 */

public class Wave implements Parcelable{
    public Wave(){}

    @SerializedName("quota_num")
    public int quotaNum;

    @SerializedName("ikura_num")
    public int powerEggNum;

    @SerializedName("golden_ikura_num")
    public int goldenEggNum;

    @SerializedName("golden_ikura_pop_num")
    public int goldenEggPop;

    @SerializedName("water_level")
    public KeyName waterLevel;

    @SerializedName("eventType")
    public KeyName eventType;

    protected Wave(Parcel in) {
        quotaNum = in.readInt();
        powerEggNum = in.readInt();
        goldenEggNum = in.readInt();
        goldenEggPop = in.readInt();
        waterLevel = in.readParcelable(KeyName.class.getClassLoader());
        eventType = in.readParcelable(KeyName.class.getClassLoader());
    }

    public static final Creator<Wave> CREATOR = new Creator<Wave>() {
        @Override
        public Wave createFromParcel(Parcel in) {
            return new Wave(in);
        }

        @Override
        public Wave[] newArray(int size) {
            return new Wave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quotaNum);
        dest.writeInt(powerEggNum);
        dest.writeInt(goldenEggNum);
        dest.writeInt(goldenEggPop);
        dest.writeParcelable(waterLevel, flags);
        dest.writeParcelable(eventType, flags);
    }
}
