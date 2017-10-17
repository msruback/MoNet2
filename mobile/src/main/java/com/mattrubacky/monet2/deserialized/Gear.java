package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
public class Gear implements Parcelable {
    public Gear(){}

    @SerializedName("name")
    String name;
    @SerializedName("brand")
    Brand brand;
    @SerializedName("image")
    String url;
    @SerializedName("rarity")
    int rarity;
    @SerializedName("id")
    int id;
    @SerializedName("kind")
    String kind;

    protected Gear(Parcel in) {
        name = in.readString();
        brand = in.readParcelable(Brand.class.getClassLoader());
        url = in.readString();
        rarity = in.readInt();
        id = in.readInt();
        kind = in.readString();
    }

    public static final Creator<Gear> CREATOR = new Creator<Gear>() {
        @Override
        public Gear createFromParcel(Parcel in) {
            return new Gear(in);
        }

        @Override
        public Gear[] newArray(int size) {
            return new Gear[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(brand, flags);
        dest.writeString(url);
        dest.writeInt(rarity);
        dest.writeInt(id);
        dest.writeString(kind);
    }
}
