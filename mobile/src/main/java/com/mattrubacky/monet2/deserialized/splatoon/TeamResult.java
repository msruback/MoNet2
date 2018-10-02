package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class contains the result of a Battle, from the perspective of the user
 */
public class TeamResult implements Parcelable {
    public TeamResult(){}

    //The text of the result, either "VICTORY" or "DEFEAT"
    @SerializedName("name")
    public String name;

    @SerializedName("key")
    public String key;


    protected TeamResult(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TeamResult> CREATOR = new Creator<TeamResult>() {
        @Override
        public TeamResult createFromParcel(Parcel in) {
            return new TeamResult(in);
        }

        @Override
        public TeamResult[] newArray(int size) {
            return new TeamResult[size];
        }
    };
}
