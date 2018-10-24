package com.mattrubacky.monet2.helper;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.GrizzCoGrade;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

/**
 * Created by mattr on 10/24/2018.
 */

public class ShiftStats implements Parcelable{
    public ShiftStats(){}

    @SerializedName("job_num")
    public int jobNum;

    @SerializedName("my_ikura_total")
    public int powerEggTotal;

    @SerializedName("my_golden_egg_total")
    public int goldenEggTotal;

    @SerializedName("dead_total")
    public int deadTotal;

    @SerializedName("help_total")
    public int helpTotal;

    @SerializedName("team_power_egg_total")
    public int teamPowerEggTotal;

    @SerializedName("team_golden_ikura_total")
    public int teamGoldenEggTotal;

    @SerializedName("schedule")
    public SalmonRunDetail schedule;

    @SerializedName("grade")
    public GrizzCoGrade grade;

    protected ShiftStats(Parcel in) {
        jobNum = in.readInt();
        powerEggTotal = in.readInt();
        goldenEggTotal = in.readInt();
        deadTotal = in.readInt();
        helpTotal = in.readInt();
        teamPowerEggTotal = in.readInt();
        teamGoldenEggTotal = in.readInt();
        schedule = in.readParcelable(SalmonRunDetail.class.getClassLoader());
        grade = in.readParcelable(GrizzCoGrade.class.getClassLoader());
    }

    public static final Creator<ShiftStats> CREATOR = new Creator<ShiftStats>() {
        @Override
        public ShiftStats createFromParcel(Parcel in) {
            return new ShiftStats(in);
        }

        @Override
        public ShiftStats[] newArray(int size) {
            return new ShiftStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(jobNum);
        dest.writeInt(powerEggTotal);
        dest.writeInt(goldenEggTotal);
        dest.writeInt(deadTotal);
        dest.writeInt(helpTotal);
        dest.writeInt(teamPowerEggTotal);
        dest.writeInt(teamGoldenEggTotal);
        dest.writeParcelable(schedule, flags);
        dest.writeParcelable(grade, flags);
    }
}
