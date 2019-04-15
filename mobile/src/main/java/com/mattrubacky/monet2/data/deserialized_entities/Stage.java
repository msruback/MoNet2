package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a Stage
 * It is used in both Schedules, Splatfests, and Battles
 * Stage images are stored in the stage directory
 */
@Entity(tableName = "stage")
public class Stage implements Parcelable {
    //GSON constructor
    @Ignore
    public Stage(){}

    //Rooms Constructor
    public Stage(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @PrimaryKey
    @ColumnInfo(name="stage_id")
    @SerializedName("id")
    public int id;

    //The name of the Stage
    @ColumnInfo(name="stage_name")
    @SerializedName("name")
    public String name;

    //The URL of the Stage image
    @ColumnInfo(name="stage_image")
    @SerializedName("image")
    public String url;

    @Ignore
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
