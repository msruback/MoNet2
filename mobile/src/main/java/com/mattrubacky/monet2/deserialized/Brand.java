package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */

public class Brand implements Parcelable{
    public Brand(){}

    @SerializedName("name")
    String name;
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String url;
    @SerializedName("frequent_skill")
    Skill skill;

    protected Brand(Parcel in) {
        name = in.readString();
        id = in.readInt();
        url = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Parcelable.Creator<Brand> CREATOR = new Parcelable.Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeParcelable(skill, flags);
    }
}
