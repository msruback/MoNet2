package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 11/9/2017.
 * This class represents the stages in Salmon Run. These stages only contain a name and an image, no id or anything else */

@Entity(tableName = "salmon_stage")
public class SalmonStage implements Parcelable{
    @Ignore
    public SalmonStage(){}

    @PrimaryKey
    public int id;

    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String url;

    public SalmonStage(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Ignore
    protected SalmonStage(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalmonStage> CREATOR = new Creator<SalmonStage>() {
        @Override
        public SalmonStage createFromParcel(Parcel in) {
            return new SalmonStage(in);
        }

        @Override
        public SalmonStage[] newArray(int size) {
            return new SalmonStage[size];
        }
    };

    public static int generateId(String name){
        switch(name){
            case "Spawning Grounds":
                return 0;
            case "Marooner's Bay":
                return 1;
            case "Lost Outpost":
                return 2;
            case "Salmonid Smokeyard":
                return 3;
            case "Ruins of Ark Polaris":
                return 4;
            default:
                return -1;
        }
    }
}
