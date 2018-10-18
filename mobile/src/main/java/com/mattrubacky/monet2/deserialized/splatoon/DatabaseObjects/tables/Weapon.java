package com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.sqlite.Factory.ColumnName;
import com.mattrubacky.monet2.sqlite.Factory.TableName;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.TableManager;

/**
 * Created by mattr on 10/17/2017.
 * This class represent a Weapon
 * Weapon images are stored in the weapon directory
 */
@TableName(SplatnetContract.Weapon.TABLE_NAME)
public class Weapon extends DatabaseObject implements Parcelable {
    public Weapon(){}

    @ColumnName(SplatnetContract.Weapon._ID)
    @SerializedName("id")
    public int id;

    @ColumnName(SplatnetContract.Weapon.COLUMN_NAME)
    @SerializedName("name")
    public String name;

    //The URL for the weapon image
    @ColumnName(SplatnetContract.Weapon.COLUMN_URL)
    @SerializedName("image")
    public String url;

    //The Special Weapon this weapon has
    @SerializedName("special")
    public Special special;

    //The Sub Weapon this weapon has
    @SerializedName("sub")
    public Sub sub;

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

    @Override
    public int getId() {
        return id;
    }

}
