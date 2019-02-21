package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignStage implements Parcelable{
    public CampaignStage(){}

    @SerializedName("area")
    public int area;
    @SerializedName("id")
    public int id;
    @SerializedName("is_boss")
    public boolean isBoss;

    protected CampaignStage(Parcel in) {
        area = in.readInt();
        id = in.readInt();
        isBoss = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(area);
        dest.writeInt(id);
        dest.writeByte((byte) (isBoss ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampaignStage> CREATOR = new Creator<CampaignStage>() {
        @Override
        public CampaignStage createFromParcel(Parcel in) {
            return new CampaignStage(in);
        }

        @Override
        public CampaignStage[] newArray(int size) {
            return new CampaignStage[size];
        }
    };
}
