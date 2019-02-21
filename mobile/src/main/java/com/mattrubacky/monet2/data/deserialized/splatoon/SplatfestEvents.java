package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 9/25/2018.
 */

public class SplatfestEvents implements Parcelable{
    public SplatfestEvents(){}

    @SerializedName("events")
    ArrayList<SplatfestEvents> events;

    protected SplatfestEvents(Parcel in) {
        events = in.createTypedArrayList(SplatfestEvents.CREATOR);
    }

    public static final Creator<SplatfestEvents> CREATOR = new Creator<SplatfestEvents>() {
        @Override
        public SplatfestEvents createFromParcel(Parcel in) {
            return new SplatfestEvents(in);
        }

        @Override
        public SplatfestEvents[] newArray(int size) {
            return new SplatfestEvents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(events);
    }
}
