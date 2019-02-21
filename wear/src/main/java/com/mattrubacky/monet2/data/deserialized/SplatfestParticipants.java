package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class contains the amount of participants on each side in a splatfest
 * Part of the Splatfest Result object
 */
public class SplatfestParticipants implements Parcelable{
    public SplatfestParticipants(){}

    //Side Alpha's participants
    @SerializedName("alpha")
    public int alpha;

    //Side Bravo's participants
    @SerializedName("bravo")
    public int bravo;

    protected SplatfestParticipants(Parcel in) {
        alpha = in.readInt();
        bravo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alpha);
        dest.writeInt(bravo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestParticipants> CREATOR = new Creator<SplatfestParticipants>() {
        @Override
        public SplatfestParticipants createFromParcel(Parcel in) {
            return new SplatfestParticipants(in);
        }

        @Override
        public SplatfestParticipants[] newArray(int size) {
            return new SplatfestParticipants[size];
        }
    };
}
