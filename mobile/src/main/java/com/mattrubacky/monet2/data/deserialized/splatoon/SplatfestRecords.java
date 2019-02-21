package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Part of the Records Endpoint
 * The user's performance in a specific Splatfest
 */
public class SplatfestRecords implements Parcelable{
    public SplatfestRecords(){}

    @SerializedName("fes_id")
    public int id;

    //The number of points progressed to the next grade
    @SerializedName("fes_point")
    public int points;

    //The power the user is at
    @SerializedName("fes_power")
    public int power;

    //The splatfest grade the user is at
    @SerializedName("fes_grade")
    public SplatfestGrade grade;

    @SerializedName("fes_contribution_challenge")
    public UserClout challengeClout;

    @SerializedName("fes_contribution_regular")
    public UserClout regularClout;

    protected SplatfestRecords(Parcel in) {
        id = in.readInt();
        points = in.readInt();
        power = in.readInt();
        grade = in.readParcelable(SplatfestGrade.class.getClassLoader());
        challengeClout = in.readParcelable(UserClout.class.getClassLoader());
        regularClout = in.readParcelable(UserClout.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(points);
        dest.writeInt(power);
        dest.writeParcelable(grade, flags);
        dest.writeParcelable(challengeClout, flags);
        dest.writeParcelable(regularClout,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestRecords> CREATOR = new Creator<SplatfestRecords>() {
        @Override
        public SplatfestRecords createFromParcel(Parcel in) {
            return new SplatfestRecords(in);
        }

        @Override
        public SplatfestRecords[] newArray(int size) {
            return new SplatfestRecords[size];
        }
    };
}
