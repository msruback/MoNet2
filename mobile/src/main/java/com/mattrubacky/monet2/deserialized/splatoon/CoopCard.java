package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/6/2018.
 */

public class CoopCard implements Parcelable{
    public CoopCard(){}

    @SerializedName("ikura_total")
    public int powerEggs;

    @SerializedName("kuma_point_total")
    public int money;

    @SerializedName("help_total")
    public int help;

    @SerializedName("golden_ikura_total")
    public int goldenEggs;

    @SerializedName("kuma_point")
    public int currentPoints;

    @SerializedName("job_num")
    public int jobs;

    protected CoopCard(Parcel in) {
        powerEggs = in.readInt();
        money = in.readInt();
        help = in.readInt();
        goldenEggs = in.readInt();
        currentPoints = in.readInt();
        jobs = in.readInt();
    }

    public static final Creator<CoopCard> CREATOR = new Creator<CoopCard>() {
        @Override
        public CoopCard createFromParcel(Parcel in) {
            return new CoopCard(in);
        }

        @Override
        public CoopCard[] newArray(int size) {
            return new CoopCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(powerEggs);
        dest.writeInt(money);
        dest.writeInt(help);
        dest.writeInt(goldenEggs);
        dest.writeInt(currentPoints);
        dest.writeInt(jobs);
    }
}
