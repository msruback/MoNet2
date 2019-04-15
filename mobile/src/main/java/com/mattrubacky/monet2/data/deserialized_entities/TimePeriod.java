package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.data.deserialized.splatoon.KeyName;

import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a two hour time period of certain maps and a certain rule being available
 */
@Entity(tableName = "time_period",
        foreignKeys = {
                @ForeignKey(entity = Stage.class,
                        parentColumns = "stage_id",
                        childColumns = "a"),
                @ForeignKey(entity = Stage.class,
                        parentColumns = "stage_id",
                        childColumns = "b")
        },
        indices = {
                @Index(name="a",
                        value = "a"),
                @Index(name="b",
                        value = "b")
        }
)
public class TimePeriod implements Parcelable {
    //GSON constructor
    @Ignore
    public TimePeriod() {}
    //Rooms constructor
    public TimePeriod(int id, long start, long end, KeyName rule, KeyName mode, Stage a, Stage b) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.rule = rule;
        this.mode = mode;
        this.a = a;
        this.b = b;
    }

    //I made the mistake of annotating this once, and for some godforsaken reason GSON won't learn that it isn't annotated any more
    @PrimaryKey
    @ColumnInfo(name="time_period_id")
    @SerializedName("gson is fucking stupid and refuses to understand that this field isn't annotated")
    public int id;

    @SerializedName("id")
    public long trash;

    //The mode the TimePeriod is in
    @ColumnInfo(name = "time_period_mode")
    @SerializedName("game_mode")
    public KeyName mode;

    //The rule available during this TimePeriod
    @ColumnInfo(name="time_period_rule")
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

    public static int generateId(long start,String mode){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(start*1000);
        int id;
        switch(mode){
            case "regular":
                id = 0;
                break;
            case "gachi":
                id = 1000;
                break;
            case "league":
                id = 2000;
                break;
            case "fes":
                id = 3000;
                break;
            default:
                id = 0;
                break;
        }
        id +=cal.get(Calendar.HOUR_OF_DAY);
        return id;
    }

    //Constructor for Parcelable
    @Ignore
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
