package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/9/2017.
 */

public class SalmonStage implements Parcelable{
    public SalmonStage(){}

    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;

    protected SalmonStage(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalmonStage> CREATOR = new Creator<SalmonStage>() {
        @Override
        public SalmonStage createFromParcel(Parcel in) {
            return new SalmonStage(in);
        }

        @Override
        public SalmonStage[] newArray(int size) {
            return new SalmonStage[size];
        }
    };
}
