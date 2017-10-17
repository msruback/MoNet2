package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class Weapon implements Parcelable {
    public Weapon(){}
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;
    @SerializedName("special")
    Special special;
    @SerializedName("sub")
    Sub sub;

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
