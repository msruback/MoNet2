package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * The root of the timeline endpoint
 */
public class Timeline implements Parcelable{
    public Timeline(){}

    //The User's unique id
    @SerializedName("unique_id")
    public String uniqueID;

    //The info on Salmon Run
    //Note: coop is only full when Salmon Run is available, otherwise it is empty
    @SerializedName("coop")
    public GrizzCo currentRun;

    @SerializedName("weapon_availability")
    public WeaponAvailabilities sheldon;

    @SerializedName("stats")
    public TimelineBattles battles;

    protected Timeline(Parcel in) {
        uniqueID = in.readString();
        currentRun = in.readParcelable(GrizzCo.class.getClassLoader());
        sheldon = in.readParcelable(WeaponAvailabilities.class.getClassLoader());
        battles = in.readParcelable(TimelineBattles.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uniqueID);
        dest.writeParcelable(currentRun, flags);
        dest.writeParcelable(sheldon, flags);
        dest.writeParcelable(battles,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Timeline> CREATOR = new Creator<Timeline>() {
        @Override
        public Timeline createFromParcel(Parcel in) {
            return new Timeline(in);
        }

        @Override
        public Timeline[] newArray(int size) {
            return new Timeline[size];
        }
    };
}
