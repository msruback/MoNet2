package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 9/25/2018.
 */

public class SplatfestEvent implements Parcelable{
    public SplatfestEvent(){}

    @SerializedName("members")
    public ArrayList<User> members;

    @SerializedName("event_type")
    public EventType type;

    @SerializedName("another_name")
    public String teamName;

    @SerializedName("festival_id")
    public int splatfestId;

    @SerializedName("updated_time")
    public long updateTime;

    protected SplatfestEvent(Parcel in) {
        members = in.createTypedArrayList(User.CREATOR);
        type = in.readParcelable(EventType.class.getClassLoader());
        teamName = in.readString();
        splatfestId = in.readInt();
        updateTime = in.readLong();
    }

    public static final Creator<SplatfestEvent> CREATOR = new Creator<SplatfestEvent>() {
        @Override
        public SplatfestEvent createFromParcel(Parcel in) {
            return new SplatfestEvent(in);
        }

        @Override
        public SplatfestEvent[] newArray(int size) {
            return new SplatfestEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(members);
        dest.writeParcelable(type, flags);
        dest.writeString(teamName);
        dest.writeInt(splatfestId);
        dest.writeLong(updateTime);
    }
}
