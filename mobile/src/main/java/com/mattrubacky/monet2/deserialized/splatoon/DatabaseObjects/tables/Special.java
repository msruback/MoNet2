package com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.sqlite.Factory.ColumnName;
import com.mattrubacky.monet2.sqlite.Factory.TableName;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

/**
 * Created by mattr on 10/17/2017.
 * This class represents Weapon Specials.
 * Special images are stored in the special directory,
 * although for battle info edited drawables are used
 */
@TableName(SplatnetContract.Special.TABLE_NAME)
public class Special extends DatabaseObject implements Parcelable {
    public Special(){}

    @ColumnName(SplatnetContract.Special._ID)
    @SerializedName("id")
    public int id;

    @ColumnName(SplatnetContract.Special.COLUMN_NAME)
    @SerializedName("name")
    public String name;

    //The URL of the basic url
    @ColumnName(SplatnetContract.Special.COLUMN_URL)
    @SerializedName("image_a")
    public String url;

    protected Special(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Special> CREATOR = new Creator<Special>() {
        @Override
        public Special createFromParcel(Parcel in) {
            return new Special(in);
        }

        @Override
        public Special[] newArray(int size) {
            return new Special[size];
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
    }

    @Override
    public int getId() {
        return id;
    }
}
