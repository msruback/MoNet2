package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
public class Gamemode implements Parcelable {
    public Gamemode(){
    }
    @SerializedName("name")
    String name;
    @SerializedName("key")
    String key;

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
