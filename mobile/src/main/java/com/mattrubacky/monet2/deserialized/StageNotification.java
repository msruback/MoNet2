package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 */
class StageNotification implements Parcelable {
    public StageNotification(){}

    @SerializedName("stage")
    Stage stage;
    @SerializedName("type")
    String type;
    @SerializedName("rule")
    String rule;
    @SerializedName("notified")
    ArrayList<TimePeriod> notified;

    protected StageNotification(Parcel in) {
        stage = in.readParcelable(Stage.class.getClassLoader());
        type = in.readString();
        rule = in.readString();
        notified = in.createTypedArrayList(TimePeriod.CREATOR);
    }

    public static final Creator<StageNotification> CREATOR = new Creator<StageNotification>() {
        @Override
        public StageNotification createFromParcel(Parcel in) {
            return new StageNotification(in);
        }

        @Override
        public StageNotification[] newArray(int size) {
            return new StageNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
        dest.writeString(type);
        dest.writeString(rule);
        dest.writeTypedList(notified);
    }
}
