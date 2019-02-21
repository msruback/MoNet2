package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents individual abilities.
 * Skill images are stored in the ability directory.
 */
@Entity(tableName = "skill")
public class Skill implements Parcelable {
    @Ignore
    public Skill(){}

    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;

    //The URL of the Ability images
    @SerializedName("image")
    public String url;

    public Skill(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Ignore
    protected Skill(Parcel in) {
        name = in.readString();
        url = in.readString();
        id = in.readInt();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(id);
    }
}
