package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represent a Weapon
 * Weapon images are stored in the weapon directory
 */
public class Weapon implements Parcelable {
    public Weapon(){}
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
