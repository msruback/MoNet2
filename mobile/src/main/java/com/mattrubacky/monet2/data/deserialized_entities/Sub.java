package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a Sub Weapon, not to be confused with a Sub Ability
 */
@Entity(tableName = "sub")
public class Sub implements Parcelable {
    //GSON constructor
    @Ignore
    public Sub(){}

    //Rooms constructor
    public Sub(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @PrimaryKey
    @ColumnInfo(name="sub_id")
    @SerializedName("id")
    public int id;

    @ColumnInfo(name="sub_name")
    @SerializedName("name")
    public String name;

    //The base image of the sub weapon
    @ColumnInfo(name="sub_url")
    @SerializedName("image_a")
    public String url;

    @Ignore
    protected Sub(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Sub> CREATOR = new Creator<Sub>() {
        @Override
        public Sub createFromParcel(Parcel in) {
            return new Sub(in);
        }

        @Override
        public Sub[] newArray(int size) {
            return new Sub[size];
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
