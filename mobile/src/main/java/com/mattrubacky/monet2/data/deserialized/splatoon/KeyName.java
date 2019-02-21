package com.mattrubacky.monet2.data.deserialized.splatoon;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.R;
/**
 * Created by mattr on 10/19/2018.
 */
public class KeyName implements Parcelable{
    public KeyName(){}
    @SerializedName("key")
    public String key;
    @SerializedName("name")
    public String name;
    protected KeyName(Parcel in) {
        key = in.readString();
        name = in.readString();
    }
    public static final Creator<KeyName> CREATOR = new Creator<KeyName>() {
        @Override
        public KeyName createFromParcel(Parcel in) {
            return new KeyName(in);
        }
        @Override
        public KeyName[] newArray(int size) {
            return new KeyName[size];
        }
    };
    public String getName(Context context){
        switch(key){
            case "turf_war":
                name = context.getString(R.string.turfWar);
                break;
            case "rainmaker":
                name = context.getString(R.string.rainmaker);
                break;
            case "tower_control":
                name = context.getString(R.string.towerControl);
                break;
            case "splat_zones":
                name = context.getString(R.string.splatzone);
                break;
            case "clam_blitz":
                name = context.getString(R.string.clamBlitz);
                break;
            case "low":
                name = context.getString(R.string.lowTide);
                break;
            case "normal":
                name = context.getString(R.string.normalTide);
                break;
            case "high":
                name = context.getString(R.string.highTide);
                break;
            case "cohock-charge":
                name = context.getString(R.string.cohockCharge);
                break;
            case "fog":
                name = context.getString(R.string.fog);
                break;
            case "griller":
                name = context.getString(R.string.griller);
                break;
            case "rush":
                name = context.getString(R.string.rush);
                break;
            case "the-mothership":
                name = context.getString(R.string.theMothership);
                break;
            case "goldie-seeking":
                name = context.getString(R.string.goldieSeeking);
                break;
            case "regular":
                name = context.getString(R.string.regular);
                break;
            case "gachi":
                name = context.getString(R.string.rank);
                break;
            case "league":
                name = context.getString(R.string.league);
                break;
            case "league_pair":
                name = context.getString(R.string.leaguePair);
                break;
            case "league_team":
                name = context.getString(R.string.leagueTeam);
                break;
            case "10_x_match":
                name = context.getString(R.string.tenTimes);
                break;
            case "100_x_match":
                name = context.getString(R.string.hundredTimes);
                break;
            case "victory":
                name = context.getString(R.string.victory);
                break;
            case "defeat":
                name = context.getString(R.string.defeat);
                break;
        }
        return name;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
    }
}