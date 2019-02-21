package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class SplatfestVotes implements Parcelable{
    public SplatfestVotes(){}

    @SerializedName("nickname_and_icons")
    public ArrayList<NicknameIcon> players;
    @SerializedName("votes")
    public SplatfestVote votes;

    protected SplatfestVotes(Parcel in) {
        players = in.createTypedArrayList(NicknameIcon.CREATOR);
        votes = in.readParcelable(SplatfestVote.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(players);
        dest.writeParcelable(votes, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestVotes> CREATOR = new Creator<SplatfestVotes>() {
        @Override
        public SplatfestVotes createFromParcel(Parcel in) {
            return new SplatfestVotes(in);
        }

        @Override
        public SplatfestVotes[] newArray(int size) {
            return new SplatfestVotes[size];
        }
    };
}
