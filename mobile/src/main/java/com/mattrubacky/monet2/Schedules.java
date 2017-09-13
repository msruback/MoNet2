package com.mattrubacky.monet2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 9/12/2017.
 */


public class Schedules implements Parcelable{
    public Schedules(){}

    @SerializedName("regular")
    ArrayList<TimePeriod> regular;
    @SerializedName("gachi")
    ArrayList<TimePeriod> ranked;
    @SerializedName("league")
    ArrayList<TimePeriod> league;

    protected Schedules(Parcel in) {
        regular = in.createTypedArrayList(TimePeriod.CREATOR);
        ranked = in.createTypedArrayList(TimePeriod.CREATOR);
        league = in.createTypedArrayList(TimePeriod.CREATOR);
    }

    public static final Creator<Schedules> CREATOR = new Creator<Schedules>() {
        @Override
        public Schedules createFromParcel(Parcel in) {
            return new Schedules(in);
        }

        @Override
        public Schedules[] newArray(int size) {
            return new Schedules[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(regular);
        dest.writeTypedList(ranked);
        dest.writeTypedList(league);
    }
}

class TimePeriod implements Parcelable{
    public TimePeriod(){}

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
        dest.writeLong(this.start);
        dest.writeLong(this.end);
        dest.writeParcelable(this.a,0);
        dest.writeParcelable(this.b,0);
    }
}

class Rule implements Parcelable{
    public Rule(){}

    @SerializedName("name")
    String name;

    @SerializedName("key")
    String key;

    protected Rule(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Rule> CREATOR = new Creator<Rule>() {
        @Override
        public Rule createFromParcel(Parcel in) {
            return new Rule(in);
        }

        @Override
        public Rule[] newArray(int size) {
            return new Rule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }
}

class Stage implements Parcelable{
    public Stage(){}

    @SerializedName("id")
    int id;
    @SerializedName("image")
    String image;
    @SerializedName("name")
    String name;

    protected Stage(Parcel in) {
        id = in.readInt();
        image = in.readString();
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
        dest.writeString(image);
        dest.writeString(name);
    }
}