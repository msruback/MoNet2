package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/6/2018.
 */

public class CoopIds implements Parcelable{
    public CoopIds(){}

    @SerializedName("job_id")
    public int jobId;

    protected CoopIds(Parcel in) {
        jobId = in.readInt();
    }

    public static final Creator<CoopIds> CREATOR = new Creator<CoopIds>() {
        @Override
        public CoopIds createFromParcel(Parcel in) {
            return new CoopIds(in);
        }

        @Override
        public CoopIds[] newArray(int size) {
            return new CoopIds[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(jobId);
    }
}
