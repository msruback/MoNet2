package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/8/2018.
 */

public class GrizzCoGrade implements Parcelable{
    public GrizzCoGrade(){}

    @SerializedName("name")
    public String name;

    @SerializedName("id")
    public int id;

    protected GrizzCoGrade(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<GrizzCoGrade> CREATOR = new Creator<GrizzCoGrade>() {
        @Override
        public GrizzCoGrade createFromParcel(Parcel in) {
            return new GrizzCoGrade(in);
        }

        @Override
        public GrizzCoGrade[] newArray(int size) {
            return new GrizzCoGrade[size];
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
    }
}
