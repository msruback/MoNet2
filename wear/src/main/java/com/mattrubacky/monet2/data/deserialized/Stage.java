package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a Stage
 * It is used in both Schedules, Splatfests, and Battles
 * Stage images are stored in the stage directory
 */
public class Stage implements Parcelable {
    public Stage(){}

    @SerializedName("id")
    public int id;

    //The URL of the Stage image
    @SerializedName("image")
    public String url;

    //The name of the Stage
    @SerializedName("name")
    public String name;

    protected Stage(Parcel in) {
        id = in.readInt();
        url = in.readString();
        name = in.readString();
    }

    public static final Creator<Stage> CREATOR = new Creator<Stage>() {
        @Override
        public Stage createFromParcel(Parcel in) {
            return new Stage(in);
        }

        @Override
        public Stage[] newArray(int size) {
            return new Stage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(name);
    }
}
