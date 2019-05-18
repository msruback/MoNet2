package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the gear of the month at GrizzCo
 */
@Entity(tableName = "salmon_gear",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "gear_id",
                        childColumns = "monthly_gear")
        },
        indices = {
                @Index(name="monthly_gear",
                        value = "monthly_gear")
        }
)
public class RewardGear implements Parcelable{

    //GSON constructor
    @Ignore
    public RewardGear(){}

    //Rooms constructor
    public RewardGear(int month,long available,Gear gear){
        this.month = month;
        this.available = available;
        this.gear = gear;
    }

    //The first date the gear is available
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("available_time")
    public long available;

    @PrimaryKey
    public int month;

    //The gear itself
    @ColumnInfo(name="monthly_gear")
    @SerializedName("gear")
    public Gear gear;

    public static int generateId(long now){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(new Date(now*1000));
        int id = cal.get(Calendar.YEAR)-2017;
        id *= 100;
        id += cal.get(Calendar.MONTH);
        return id;
    }

    @Ignore
    protected RewardGear(Parcel in) {
        available = in.readLong();
        gear = in.readParcelable(Gear.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(available);
        dest.writeParcelable(gear, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RewardGear> CREATOR = new Creator<RewardGear>() {
        @Override
        public RewardGear createFromParcel(Parcel in) {
            return new RewardGear(in);
        }

        @Override
        public RewardGear[] newArray(int size) {
            return new RewardGear[size];
        }
    };
}
