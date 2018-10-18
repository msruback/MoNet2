package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the gamemode for a TimePeriod
 */
public class Gamemode implements Parcelable {
    public Gamemode(){
    }
    //The pretty name of the gamemode
    @SerializedName("name")
    public String name;
    /**The name of the gamemode lowercase with underscores
     * This could be "regular", "gachi", "league", and "fes"
     */
    @SerializedName("value")
    public String key;

    protected Gamemode(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Gamemode> CREATOR = new Creator<Gamemode>() {
        @Override
        public Gamemode createFromParcel(Parcel in) {
            return new Gamemode(in);
        }

        @Override
        public Gamemode[] newArray(int size) {
            return new Gamemode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }
}
