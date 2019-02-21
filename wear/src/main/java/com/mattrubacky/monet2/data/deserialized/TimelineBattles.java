package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/22/2017.
 */

public class TimelineBattles implements Parcelable {
    public TimelineBattles(){
    }
    @SerializedName("recents")
    public ArrayList<ResultIds> battles;

    protected TimelineBattles(Parcel in) {
        battles = in.createTypedArrayList(ResultIds.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(battles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimelineBattles> CREATOR = new Creator<TimelineBattles>() {
        @Override
        public TimelineBattles createFromParcel(Parcel in) {
            return new TimelineBattles(in);
        }

        @Override
        public TimelineBattles[] newArray(int size) {
            return new TimelineBattles[size];
        }
    };
}
