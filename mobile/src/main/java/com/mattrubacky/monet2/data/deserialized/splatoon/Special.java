package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents Weapon Specials.
 * Special images are stored in the special directory,
 * although for battle info edited drawables are used
 */

@Entity(tableName = "special")
public class Special implements Parcelable {
    @Ignore
    public Special(){}

    @PrimaryKey
    @ColumnInfo(name="special_id")
    @SerializedName("id")
    public int id;
    @ColumnInfo(name="special_name")
    @SerializedName("name")
    public String name;

    //The URL of the basic url
    @ColumnInfo(name="special_url")
    @SerializedName("image_a")
    public String url;

    public Special(int id,String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Ignore
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
}
