package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class is the results from Results
 */

public class ResultIds implements Parcelable{
    public ResultIds(){}

    @SerializedName("battle_number")
    public int id;

    protected ResultIds(Parcel in) {
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultIds> CREATOR = new Creator<ResultIds>() {
        @Override
        public ResultIds createFromParcel(Parcel in) {
            return new ResultIds(in);
        }

        @Override
        public ResultIds[] newArray(int size) {
            return new ResultIds[size];
        }
    };
}
