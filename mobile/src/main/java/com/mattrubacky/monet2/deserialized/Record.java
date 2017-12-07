package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This is the root of the response of the records endpoint
 */
public class Record implements Parcelable{
    public Record(){}

    @SerializedName("records")
    public Records records;

    protected Record(Parcel in) {
        records = in.readParcelable(Records.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(records, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
