package com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.api.splatnet.Splatnet;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.sqlite.Factory.ColumnName;
import com.mattrubacky.monet2.sqlite.Factory.ForeignReference;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an item of Gear(Headgear, Clothing, or Shoes)
 * This is used by the Shop, Battles(Player Gear), and the Closet
 * Gear images are stored in the gear directory
 */
public class Gear extends DatabaseObject implements Parcelable {
    public Gear(){}

    @ColumnName(SplatnetContract.Head._ID)
    @SerializedName("id")
    public int id;

    @ColumnName(SplatnetContract.Head.COLUMN_NAME)
    @SerializedName("name")
    public String name;

    //The Brand of the Gear]
    @ForeignReference
    @ColumnName(value = SplatnetContract.Head.COLUMN_BRAND, field = "id")
    @SerializedName("brand")
    public Brand brand;

    //The URL of the gear url
    @SerializedName("image")
    public String url;

    //The amount of ability slots a piece of gear has
    @SerializedName("rarity")
    public int rarity;

    //The type of the gear, "head","clothes",and "shoe"
    @SerializedName("kind")
    public String kind;

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

    @Override
    public int getId() {
        return id;
    }
}
