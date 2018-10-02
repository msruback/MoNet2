package com.mattrubacky.monet2.deserialized.splatoon;

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

    @SerializedName("next_challenge_octa")
    public Challenge nextChallengeOcto;

    @SerializedName("archived_challenges")
    public ArrayList<Challenge> challenges;

    @SerializedName("archived_challenges_octa")
    public ArrayList<Challenge> challengesOcto;

    @SerializedName("rewards")
    public ArrayList<Reward> rewards;

    @SerializedName("rewards_octa")
    public ArrayList<Reward> rewardsOcto;

    @SerializedName("total_paint_point")
    public Long totalPaint;

    @SerializedName("total_paint_point_octa")
    public Long totalPaintOcta;

    protected Challenges(Parcel in) {
        nextChallenge = in.readParcelable(Challenge.class.getClassLoader());
        nextChallengeOcto = in.readParcelable(Challenge.class.getClassLoader());
        challenges = in.createTypedArrayList(Challenge.CREATOR);
        challengesOcto = in.createTypedArrayList(Challenge.CREATOR);
        rewards = in.createTypedArrayList(Reward.CREATOR);
        rewardsOcto = in.createTypedArrayList(Reward.CREATOR);
        totalPaint = in.readLong();
        totalPaintOcta = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(nextChallenge, flags);
        dest.writeParcelable(nextChallengeOcto,flags);
        dest.writeTypedList(challenges);
        dest.writeTypedList(challengesOcto);
        dest.writeTypedList(rewards);
        dest.writeTypedList(rewardsOcto);
        dest.writeLong(totalPaint);
        dest.writeLong(totalPaintOcta);
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
