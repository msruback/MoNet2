package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response from the /onlineshop/merchandises endpoint
 */

public class Annie implements Parcelable{
    public Annie(){}

    //The Gear that is ordered and currently awaiting pickup at Merch
    @SerializedName("ordered_info")
    public Ordered ordered;
    //Gear that is for sale currently in the shop
    @SerializedName("merchandises")
    public ArrayList<Product> merch;

    protected Annie(Parcel in) {
        ordered = in.readParcelable(Ordered.class.getClassLoader());
        merch = in.createTypedArrayList(Product.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ordered, flags);
        dest.writeTypedList(merch);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Annie> CREATOR = new Creator<Annie>() {
        @Override
        public Annie createFromParcel(Parcel in) {
            return new Annie(in);
        }

        @Override
        public Annie[] newArray(int size) {
            return new Annie[size];
        }
    };
}
