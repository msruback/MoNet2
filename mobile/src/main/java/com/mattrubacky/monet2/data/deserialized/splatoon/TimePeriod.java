package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a two hour time period of certain maps and a certain rule being available
 */
@Entity(tableName = "time_period",
        foreignKeys = {
                @ForeignKey(entity = Stage.class,
                        parentColumns = "id",
                        childColumns = "a"),
                @ForeignKey(entity = Stage.class,
                        parentColumns = "id",
                        childColumns = "b")
        })
public class TimePeriod implements Parcelable {
    public TimePeriod(){}

    @PrimaryKey
    @SerializedName("id")
    public int id;

    //The mode the TimePeriod is in
    @SerializedName("game_mode")
    public KeyName mode;

    //The rule available during this TimePeriod
    @SerializedName("rule")
    public KeyName rule;

    //A Stage available in this TimePeriod
    @SerializedName("stage_a")
    public Stage a;
    //Another Stage available in this TimePeriod
    @SerializedName("stage_b")
    public Stage b;

    //The time this TimePeriod starts
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    public Long start;

    //The time this TimePeriod ends
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    public Long end;

    public TimePeriod(int id,long start,long end, KeyName rule, KeyName mode,Stage a,Stage b){
        this.id = id;
        this.start = start;
        this.end = end;
        this.rule = rule;
        this.mode = mode;
        this.a = a;
        this.b = b;
    }

    protected TimePeriod(Parcel in) {
        mode = in.readParcelable(KeyName.class.getClassLoader());
        rule = in.readParcelable(KeyName.class.getClassLoader());
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
        dest.writeParcelable(mode, flags);
        dest.writeParcelable(rule, flags);
        dest.writeParcelable(b, flags);
        dest.writeParcelable(a, flags);
        dest.writeLong(start);
        dest.writeLong(end);
    }
}
