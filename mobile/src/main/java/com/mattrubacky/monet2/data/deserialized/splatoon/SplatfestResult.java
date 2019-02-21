package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * Only present in the festivals/pasts endpoint
 * The Results of a Splatfest
 */
public class SplatfestResult implements Parcelable{
    public SplatfestResult(){}

    @SerializedName("festival_id")
    public int id;
    @SerializedName("festival_version")
    public int version;
    @SerializedName("summary")
    public SplatfestSummary summary;
    @SerializedName("rates")
    public SplatfestRates rates;
    @SerializedName("contribution_alpha")
    public SplatfestContribution alphaAverages;
    @SerializedName("contribution_bravo")
    public SplatfestContribution bravoAverages;


    protected SplatfestResult(Parcel in) {
        id = in.readInt();
        version = in.readInt();
        summary = in.readParcelable(SplatfestSummary.class.getClassLoader());
        rates = in.readParcelable(SplatfestRates.class.getClassLoader());
        alphaAverages = in.readParcelable(SplatfestContribution.class.getClassLoader());
        bravoAverages = in.readParcelable(SplatfestContribution.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(version);
        dest.writeParcelable(summary, flags);
        dest.writeParcelable(rates, flags);
        dest.writeParcelable(alphaAverages,flags);
        dest.writeParcelable(bravoAverages,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestResult> CREATOR = new Creator<SplatfestResult>() {
        @Override
        public SplatfestResult createFromParcel(Parcel in) {
            return new SplatfestResult(in);
        }

        @Override
        public SplatfestResult[] newArray(int size) {
            return new SplatfestResult[size];
        }
    };
}
