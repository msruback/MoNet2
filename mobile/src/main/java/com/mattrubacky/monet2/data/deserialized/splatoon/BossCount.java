package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/19/2018.
 */

public class BossCount implements Parcelable{
    public BossCount(){}

    @SerializedName("3")
    public GrizzCoBossKills goldie;

    @SerializedName("6")
    public GrizzCoBossKills steelhead;

    @SerializedName("9")
    public GrizzCoBossKills flyfish;

    @SerializedName("12")
    public GrizzCoBossKills scrapper;

    @SerializedName("13")
    public GrizzCoBossKills steelEel;

    @SerializedName("14")
    public GrizzCoBossKills stinger;

    @SerializedName("15")
    public GrizzCoBossKills maws;

    @SerializedName("16")
    public GrizzCoBossKills griller;

    @SerializedName("21")
    public GrizzCoBossKills drizzler;

    protected BossCount(Parcel in) {
        goldie = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        steelhead = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        flyfish = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        scrapper = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        steelEel = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        stinger = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        maws = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        griller = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
        drizzler = in.readParcelable(GrizzCoBossKills.class.getClassLoader());
    }

    public static final Creator<BossCount> CREATOR = new Creator<BossCount>() {
        @Override
        public BossCount createFromParcel(Parcel in) {
            return new BossCount(in);
        }

        @Override
        public BossCount[] newArray(int size) {
            return new BossCount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(goldie, flags);
        dest.writeParcelable(steelhead, flags);
        dest.writeParcelable(flyfish, flags);
        dest.writeParcelable(scrapper, flags);
        dest.writeParcelable(steelEel, flags);
        dest.writeParcelable(stinger, flags);
        dest.writeParcelable(maws, flags);
        dest.writeParcelable(griller, flags);
        dest.writeParcelable(drizzler, flags);
    }
}
