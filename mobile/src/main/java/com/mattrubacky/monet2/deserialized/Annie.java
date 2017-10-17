package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response from the /onlineshop/merchandises endpoint
 */

public class Annie {
    public Annie(){}

    //The Gear that is ordered and currently awaiting pickup at Merch
    @SerializedName("ordered_info")
    Ordered ordered;
    //Gear that is for sale currently in the shop
    @SerializedName("merchandises")
    ArrayList<Product> merch;

}
