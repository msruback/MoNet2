package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents individual abilities.
 * Skill images are stored in the ability directory.
 */
public class Skill implements Parcelable {
    public Skill(){}

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;

    //The URL of the Ability images
    @SerializedName("image")
    public String url;

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
