package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignRecords implements Parcelable{
    public CampaignRecords(){}
    @SerializedName("weapon_map")
    public Map<Integer,CampaignWeapon> weaponMap;
    @SerializedName("summary")
    public CampaignSummary summary;
    @SerializedName("stage_infos")
    public ArrayList<CampaignStageInfo> info;

    protected CampaignRecords(Parcel in) {
        summary = in.readParcelable(CampaignSummary.class.getClassLoader());
        info = in.createTypedArrayList(CampaignStageInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(summary, flags);
        dest.writeTypedList(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampaignRecords> CREATOR = new Creator<CampaignRecords>() {
        @Override
        public CampaignRecords createFromParcel(Parcel in) {
            return new CampaignRecords(in);
        }

        @Override
        public CampaignRecords[] newArray(int size) {
            return new CampaignRecords[size];
        }
    };
}
