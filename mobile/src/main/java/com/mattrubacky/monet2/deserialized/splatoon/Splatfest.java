package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Stage;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a Splatfest
 * Present in the festivals/pasts and festivals/active endpoints
 */
public class Splatfest implements Parcelable {
    public Splatfest(){}

    @SerializedName("festival_id")
    public int id;

    //The time the splatfest starts and ends, as well as the time it was announced and results were posted
    @SerializedName("times")
    public SplatfestTimes times;

    //The colors in the splatfest
    @SerializedName("colors")
    public SplatfestColors colors;

    //The names of the sides
    @SerializedName("names")
    public SplatfestNames names;

    //The Special Stage
    @SerializedName("special_stage")
    public Stage stage;

    //The Images in the Splatfest
    @SerializedName("images")
    public SplatfestImages images;


    protected Splatfest(Parcel in) {
        id = in.readInt();
        times = in.readParcelable(SplatfestTimes.class.getClassLoader());
        colors = in.readParcelable(SplatfestColors.class.getClassLoader());
        names = in.readParcelable(SplatfestNames.class.getClassLoader());
        stage = in.readParcelable(Stage.class.getClassLoader());
        images = in.readParcelable(SplatfestImages.class.getClassLoader());
    }

    public static final Creator<Splatfest> CREATOR = new Creator<Splatfest>() {
        @Override
        public Splatfest createFromParcel(Parcel in) {
            return new Splatfest(in);
        }

        @Override
        public Splatfest[] newArray(int size) {
            return new Splatfest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(times, flags);
        dest.writeParcelable(colors, flags);
        dest.writeParcelable(names, flags);
        dest.writeParcelable(stage, flags);
        dest.writeParcelable(images, flags);
    }
}
