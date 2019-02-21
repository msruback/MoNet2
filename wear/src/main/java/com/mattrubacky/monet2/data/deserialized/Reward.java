package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class Reward implements Parcelable{
    public Reward(){}

    @SerializedName("images")
    public ArrayList<RewardImage> images;

    @SerializedName("paint_points")
    public long paintPoints;

    protected Reward(Parcel in) {
        images = in.createTypedArrayList(RewardImage.CREATOR);
        paintPoints = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(images);
        dest.writeLong(paintPoints);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel in) {
            return new Reward(in);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };
}
