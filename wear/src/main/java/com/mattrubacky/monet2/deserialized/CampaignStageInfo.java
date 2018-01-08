package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStageInfo implements Parcelable{
    public CampaignStageInfo(){}

    @SerializedName("clear_weapons")
    public Map<Integer,CampaignWeapon> weapons;
    @SerializedName("stage")
    public CampaignStage stage;

    protected CampaignStageInfo(Parcel in) {
        stage = in.readParcelable(CampaignStage.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampaignStageInfo> CREATOR = new Creator<CampaignStageInfo>() {
        @Override
        public CampaignStageInfo createFromParcel(Parcel in) {
            return new CampaignStageInfo(in);
        }

        @Override
        public CampaignStageInfo[] newArray(int size) {
            return new CampaignStageInfo[size];
        }
    };
}
