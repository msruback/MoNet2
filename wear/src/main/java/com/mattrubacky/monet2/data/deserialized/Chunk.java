package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/20/2017.
 */

public class Chunk implements Parcelable{
    public Chunk(){}

    @SerializedName("skill")
    public Skill skill;
    @SerializedName("count")
    public int count;

    protected Chunk(Parcel in) {
        skill = in.readParcelable(Skill.class.getClassLoader());
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(skill, flags);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chunk> CREATOR = new Creator<Chunk>() {
        @Override
        public Chunk createFromParcel(Parcel in) {
            return new Chunk(in);
        }

        @Override
        public Chunk[] newArray(int size) {
            return new Chunk[size];
        }
    };

    public void add(){
        count++;
    }

    public void sub(){
        count--;
    }
}
