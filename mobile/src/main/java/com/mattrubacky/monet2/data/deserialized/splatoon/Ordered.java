package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the gear the user ordered but has yet to pick up from Merch
 */
public class Ordered implements Parcelable{
    public Ordered(){}

    //The gear ordered
    @SerializedName("gear")
    public Gear gear;

    //The price
    @SerializedName("price")
    public String price;

    //The main ability
    @SerializedName("skill")
    public Skill skill;

    protected Ordered(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        price = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeString(price);
        dest.writeParcelable(skill, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ordered> CREATOR = new Creator<Ordered>() {
        @Override
        public Ordered createFromParcel(Parcel in) {
            return new Ordered(in);
        }

        @Override
        public Ordered[] newArray(int size) {
            return new Ordered[size];
        }
    };
}
