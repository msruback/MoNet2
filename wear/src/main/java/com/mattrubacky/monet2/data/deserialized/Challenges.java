package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class Challenges implements Parcelable{
    public Challenges(){}

    @SerializedName("next_challenge")
    public Challenge nextChallenge;

    @SerializedName("archived_challenges")
    public ArrayList<Challenge> challenges;

    @SerializedName("rewards")
    public ArrayList<Reward> rewards;

    @SerializedName("total_paint_point")
    public Long totalPaint;

    protected Challenges(Parcel in) {
        nextChallenge = in.readParcelable(Challenge.class.getClassLoader());
        challenges = in.createTypedArrayList(Challenge.CREATOR);
        rewards = in.createTypedArrayList(Reward.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(nextChallenge, flags);
        dest.writeTypedList(challenges);
        dest.writeTypedList(rewards);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Challenges> CREATOR = new Creator<Challenges>() {
        @Override
        public Challenges createFromParcel(Parcel in) {
            return new Challenges(in);
        }

        @Override
        public Challenges[] newArray(int size) {
            return new Challenges[size];
        }
    };
}
