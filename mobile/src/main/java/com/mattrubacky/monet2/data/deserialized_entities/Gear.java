package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an item of Gear(Headgear, Clothing, or Shoes)
 * This is used by the Shop, Battles(Player Gear), and the Closet
 * Gear images are stored in the gear directory
 */
@Entity(tableName = "gear",
        foreignKeys = {
                @ForeignKey(entity = Brand.class,
                        parentColumns = "brand_id",
                        childColumns = "gear_brand")
        },
        indices = {
                @Index(name="gear_brand",
                        value = "gear_brand")
        }
)
public class Gear implements Parcelable {

    //GSON constructor
    @Ignore
    public Gear(){}

    //Copy constructor
    @Ignore
    public Gear(Gear gear){
        this(gear.generatedId,gear.id,gear.brand,gear.name,gear.url,gear.kind,gear.rarity);
    }

    //Rooms constructor
    public Gear(int generatedId, int id, Brand brand, String name, String url, String kind, int rarity){
        this.generatedId = generatedId;
        this.id = id;
        this.name = name;
        this.url = url;
        this.kind = kind;
        this.rarity = rarity;
        this.brand = brand;
    }

    @PrimaryKey
    @ColumnInfo(name = "gear_id")
    public int generatedId;

    @ColumnInfo(name = "splatnet_id")
    @SerializedName("id")
    public int id;

    @ColumnInfo(name="gear_name")
    @SerializedName("name")
    public String name;

    //The Brand of the Gear
    @ColumnInfo(name="gear_brand")
    @SerializedName("brand")
    public Brand brand;

    //The URL of the gear url
    @ColumnInfo(name="gear_image")
    @SerializedName("image")
    public String url;

    //The amount of ability slots a piece of gear has
    @SerializedName("rarity")
    public int rarity;

    //The type of the gear, "head","clothes",and "shoe"
    @SerializedName("kind")
    public String kind;

    @Ignore
    protected Gear(Parcel in) {
        name = in.readString();
        brand = in.readParcelable(Brand.class.getClassLoader());
        url = in.readString();
        rarity = in.readInt();
        id = in.readInt();
        kind = in.readString();
    }

    public static int generateId(String kind, int id){
        int genId =0;
        switch(kind){
            case "head":
                genId = 0;
                break;
            case "clothes":
                genId = 100000;
                break;
            case "shoes":
                genId = 200000;
                break;
        }
        return genId +id;
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
