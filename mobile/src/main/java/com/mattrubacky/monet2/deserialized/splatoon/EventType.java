package com.mattrubacky.monet2.deserialized.splatoon;

import android.app.usage.UsageEvents;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 9/25/2018.
 */

public class EventType implements Parcelable{
    public EventType(){
    }

    @SerializedName("name")
    public String name;

    @SerializedName("key")
    public String key;

    protected EventType(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<EventType> CREATOR = new Creator<EventType>() {
        @Override
        public EventType createFromParcel(Parcel in) {
            return new EventType(in);
        }

        @Override
        public EventType[] newArray(int size) {
            return new EventType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }
}
