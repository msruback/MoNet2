package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/8/2018.
 */

public class JobResult implements Parcelable{
    public JobResult(){}

    @SerializedName("is_clear")
    public boolean isClear;

    @SerializedName("failure_reason")
    public String failureReason;

    @SerializedName("failure_wave")
    public int failureWave;

    protected JobResult(Parcel in) {
        isClear = in.readByte() != 0;
        failureReason = in.readString();
        failureWave = in.readInt();
    }

    public static final Creator<JobResult> CREATOR = new Creator<JobResult>() {
        @Override
        public JobResult createFromParcel(Parcel in) {
            return new JobResult(in);
        }

        @Override
        public JobResult[] newArray(int size) {
            return new JobResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isClear ? 1 : 0));
        dest.writeString(failureReason);
        dest.writeInt(failureWave);
    }
}
