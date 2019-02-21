package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignSummary implements Parcelable{
    public CampaignSummary(){}

    @SerializedName("clear_rate")
    public double clear;
    @SerializedName("honor")
    public Honor honor;
    @SerializedName("weapon_cleared_info")
    public Map<Integer,Boolean> cleared;

    protected CampaignSummary(Parcel in) {
        clear = in.readDouble();
        honor = in.readParcelable(Honor.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(clear);
        dest.writeParcelable(honor, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampaignSummary> CREATOR = new Creator<CampaignSummary>() {
        @Override
        public CampaignSummary createFromParcel(Parcel in) {
            return new CampaignSummary(in);
        }

        @Override
        public CampaignSummary[] newArray(int size) {
            return new CampaignSummary[size];
        }
    };
}
