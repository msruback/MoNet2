package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represent a Weapon
 * Weapon images are stored in the weapon directory
 */

@Entity(tableName = "weapon",
        foreignKeys = {
                @ForeignKey(entity = Sub.class,
                        parentColumns = "id",
                        childColumns = "sub"),
                @ForeignKey(entity = Special.class,
                        parentColumns = "id",
                        childColumns = "special")
        })
public class Weapon implements Parcelable {
    @Ignore
    public Weapon(){}

    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;

    //The URL for the weapon image
    @SerializedName("image")
    public String url;

    //The Special Weapon this weapon has
    @SerializedName("special")
    public Special special;

    //The Sub Weapon this weapon has
    @SerializedName("sub")
    public Sub sub;

    public Weapon(int id, String name, String url, Sub sub, Special special){
        this.id = id;
        this.name = name;
        this.url = url;
        this.sub = sub;
        this.special = special;
    }

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
