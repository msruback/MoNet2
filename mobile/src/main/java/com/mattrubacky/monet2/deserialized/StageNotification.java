package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an Alert for a certain Stage
 * It is used for just Stage Notifications
 */
public class StageNotification implements Parcelable {
    public StageNotification(){}

    //The Stage to be alerted about, if the id is -1, then stage does not matter
    @SerializedName("stage")
    public Stage stage;

    //The gamemode to be alerted about, if the string is "any", then type does not matter
    @SerializedName("type")
    public String type;

    //The rule to be alerted about, if the string is "any", then rule does not matter
    @SerializedName("rule")
    public String rule;
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
