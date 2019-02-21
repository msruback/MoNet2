package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/24/2017.
 */

public class RewardImage implements Parcelable{
    public RewardImage(){}

    @SerializedName("url")
    public String url;

    protected RewardImage(Parcel in) {
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RewardImage> CREATOR = new Creator<RewardImage>() {
        @Override
        public RewardImage createFromParcel(Parcel in) {
            return new RewardImage(in);
        }

        @Override
        public RewardImage[] newArray(int size) {
            return new RewardImage[size];
        }
    };
}
