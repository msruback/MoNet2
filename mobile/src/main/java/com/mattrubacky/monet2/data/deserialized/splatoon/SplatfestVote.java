package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class SplatfestVote implements Parcelable{
    public SplatfestVote(){}

    @SerializedName("alpha")
    public ArrayList<String> alpha;
    @SerializedName("bravo")
    public ArrayList<String> bravo;

    protected SplatfestVote(Parcel in) {
        alpha = in.createStringArrayList();
        bravo = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(alpha);
        dest.writeStringList(bravo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestVote> CREATOR = new Creator<SplatfestVote>() {
        @Override
        public SplatfestVote createFromParcel(Parcel in) {
            return new SplatfestVote(in);
        }

        @Override
        public SplatfestVote[] newArray(int size) {
            return new SplatfestVote[size];
        }
    };
}
