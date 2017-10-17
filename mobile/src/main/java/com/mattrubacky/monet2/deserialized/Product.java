package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a pieces of Gear for sale in the shop
 */
public class Product implements Parcelable {
    public Product(){}

    @SerializedName("id")
    String id;

    //The Gear for sale
    @SerializedName("gear")
    Gear gear;

    //The price of the gear
    @SerializedName("price")
    String price;

    //The Main Ability on the Gear
    @SerializedName("skill")
    Skill skill;

    //The time the gear stops being available
    //IMPORTANT: This is in seconds from epoch, Jave takes milliseconds from epoch,don't forget to multiply by 1000
    @SerializedName("end_time")
    Long endTime;

    protected Product(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        price = in.readString();
        id = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeString(price);
        dest.writeString(id);
        dest.writeParcelable(skill, flags);
    }
}
