package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the rule a TimePeriod or a battle has(Turf War, Rainmaker, etc)
 */
public class Rule implements Parcelable {
    public Rule(){}

    @SerializedName("name")
    public String name;

    //Name lowercase and with underscores
    @SerializedName("key")
    public String key;

    protected Rule(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Rule> CREATOR = new Creator<Rule>() {
        @Override
        public Rule createFromParcel(Parcel in) {
            return new Rule(in);
        }

        @Override
        public Rule[] newArray(int size) {
            return new Rule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }
}
