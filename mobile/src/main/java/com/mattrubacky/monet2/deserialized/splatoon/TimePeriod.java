package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.rooms.SplatnetDatabase;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a two hour time period of certain maps and a certain rule being available
 */
public class TimePeriod implements Parcelable {
    public TimePeriod(){}

    //The gamemode the TimePeriod is in
    @SerializedName("game_mode")
    public KeyName gamemode;

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
    @SerializedName("start_time")
    public Long start;

    //The time this TimePeriod ends
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("end_time")
    public Long end;

    public TimePeriodRoom toRoom(){
        int bias = 0;
        switch (gamemode.key){
            case "regular":
                bias=0;
                break;
            case "gachi":
                bias = 100;
                break;
            case "league":
                bias = 200;
                break;
            default:
                bias = 300;
                break;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(start));
        int id = cal.get(Calendar.HOUR_OF_DAY)+bias;
        return new TimePeriodRoom(id,start,end,rule.key,gamemode.key,a.id,b.id);
    }

    protected TimePeriod(Parcel in) {
        gamemode = in.readParcelable(KeyName.class.getClassLoader());
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
