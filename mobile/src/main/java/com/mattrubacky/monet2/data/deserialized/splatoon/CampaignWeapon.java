package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 12/6/2017.
 */

public class CampaignWeapon implements Parcelable{
    public CampaignWeapon(){}

    @SerializedName("weapon_category")
    public int wepcategory;
    @SerializedName("category")
    public int category;
    @SerializedName("image")
    public String url;
    @SerializedName("weapon_level")
    public int level;
    @SerializedName("clear_time")
    public long time;

    protected CampaignWeapon(Parcel in) {
        category = in.readInt();
        url = in.readString();
        level = in.readInt();
        time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeString(url);
        dest.writeInt(level);
        dest.writeLong(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CampaignWeapon> CREATOR = new Creator<CampaignWeapon>() {
        @Override
        public CampaignWeapon createFromParcel(Parcel in) {
            return new CampaignWeapon(in);
        }

        @Override
        public CampaignWeapon[] newArray(int size) {
            return new CampaignWeapon[size];
        }
    };
}
