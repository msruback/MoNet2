package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class TimePeriod implements Parcelable {
    public TimePeriod(){}

    @SerializedName("game_mode")
    Gamemode gamemode;
    @SerializedName("rule")
    Rule rule;
    @SerializedName("stage_b")
    Stage b;
    @SerializedName("stage_a")
    Stage a;
    @SerializedName("start_time")
    Long start;
    @SerializedName("end_time")
    Long end;

    protected TimePeriod(Parcel in) {
        gamemode = in.readParcelable(Gamemode.class.getClassLoader());
        rule = in.readParcelable(Rule.class.getClassLoader());
        b = in.readParcelable(Stage.class.getClassLoader());
        a = in.readParcelable(Stage.class.getClassLoader());
        start = in.readLong();
        end = in.readLong();
    }

    public static final Creator<TimePeriod> CREATOR = new Creator<TimePeriod>() {
        @Override
        public TimePeriod createFromParcel(Parcel in) {
            return new TimePeriod(in);
        }

        @Override
        public TimePeriod[] newArray(int size) {
            return new TimePeriod[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gamemode, flags);
        dest.writeParcelable(rule, flags);
        dest.writeParcelable(b, flags);
        dest.writeParcelable(a, flags);
        dest.writeLong(start);
        dest.writeLong(end);
    }
}
