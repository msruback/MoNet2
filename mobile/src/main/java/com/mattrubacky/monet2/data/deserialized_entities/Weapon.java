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
 * This class represent a Weapon
 * Weapon images are stored in the weapon directory
 */

@Entity(tableName = "weapon",
        foreignKeys = {
                @ForeignKey(entity = Sub.class,
                        parentColumns = "sub_id",
                        childColumns = "weapon_sub"),
                @ForeignKey(entity = Special.class,
                        parentColumns = "special_id",
                        childColumns = "weapon_special")
        },
        indices = {
                @Index(name="weapon_sub",
                        value = "weapon_sub"),
                @Index(name="weapon_special",
                        value = "weapon_special")
        })
public class Weapon implements Parcelable {

    //GSON constructor
    @Ignore
    public Weapon(){}

    //Rooms Constructor
    public Weapon(int id, String name, String url, Sub sub, Special special){
        this.id = id;
        this.name = name;
        this.url = url;
        this.sub = sub;
        this.special = special;
    }

    @PrimaryKey
    @ColumnInfo(name="weapon_id")
    @SerializedName("id")
    public int id;

    @ColumnInfo(name="weapon_name")
    @SerializedName("name")
    public String name;

    //The URL for the weapon image
    @ColumnInfo(name="weapon_url")
    @SerializedName("image")
    public String url;

    //The Special Weapon this weapon has
    @ColumnInfo(name="weapon_special")
    @SerializedName("special")
    public Special special;

    //The Sub Weapon this weapon has
    @ColumnInfo(name="weapon_sub")
    @SerializedName("sub")
    public Sub sub;

    @Ignore
    protected Weapon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        special = in.readParcelable(Special.class.getClassLoader());
        sub = in.readParcelable(Sub.class.getClassLoader());
    }

    public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
        @Override
        public Weapon createFromParcel(Parcel in) {
            return new Weapon(in);
        }

        @Override
        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(special, flags);
        dest.writeParcelable(sub, flags);
    }
}
