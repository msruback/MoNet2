package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/8/2018.
 */

public class GrizzCoBossKills implements Parcelable{
    public GrizzCoBossKills(){}

    @SerializedName("boss")
    public GrizzCoBoss boss;

    @SerializedName("count")
    public int count;

    protected GrizzCoBossKills(Parcel in) {
        boss = in.readParcelable(GrizzCoBoss.class.getClassLoader());
        count = in.readInt();
    }

    public static final Creator<GrizzCoBossKills> CREATOR = new Creator<GrizzCoBossKills>() {
        @Override
        public GrizzCoBossKills createFromParcel(Parcel in) {
            return new GrizzCoBossKills(in);
        }

        @Override
        public GrizzCoBossKills[] newArray(int size) {
            return new GrizzCoBossKills[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(boss, flags);
        dest.writeInt(count);
    }
}
