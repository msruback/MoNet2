package com.mattrubacky.monet2.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/6/2018.
 */

public class CoopResult implements Parcelable{
    public CoopResult(){}

    @SerializedName("job_id")
    public int id;

    @SerializedName("start_time")
    public long startTime;

    @SerializedName("play_time")
    public long playTime;

    @SerializedName("job_rate")
    public int jobRate;

    @SerializedName("danger_rate")
    public float dangerRate;

    @SerializedName("kuma_point")
    public int money;

    @SerializedName("grade_point")
    public int gradePoint;
    
    @SerializedName("grade_point_delta")
    public int gradePointDelta;

    @SerializedName("job_score")
    public int jobScore;

    @SerializedName("grade")
    public GrizzCoGrade grade;

    @SerializedName("my_result")
    public Worker myResult;

    @SerializedName("other_results")
    public ArrayList<Worker> otherResults;

    @SerializedName("boss_counts")
    public ArrayList<GrizzCoBossKills> bossCount;

    @SerializedName("wave_details")
    public ArrayList<Wave> waves;

    @SerializedName("schedule")
    public SalmonRunDetail shift;

    protected CoopResult(Parcel in) {
        id = in.readInt();
        startTime = in.readLong();
        playTime = in.readLong();
        jobRate = in.readInt();
        dangerRate = in.readFloat();
        money = in.readInt();
        gradePoint = in.readInt();
        gradePointDelta = in.readInt();
        jobScore = in.readInt();
        grade = in.readParcelable(GrizzCoGrade.class.getClassLoader());
        myResult = in.readParcelable(Worker.class.getClassLoader());
        otherResults = in.createTypedArrayList(Worker.CREATOR);
        bossCount = in.createTypedArrayList(GrizzCoBossKills.CREATOR);
        waves = in.createTypedArrayList(Wave.CREATOR);
        shift = in.readParcelable(SalmonRunDetail.class.getClassLoader());
    }

    public static final Creator<CoopResult> CREATOR = new Creator<CoopResult>() {
        @Override
        public CoopResult createFromParcel(Parcel in) {
            return new CoopResult(in);
        }

        @Override
        public CoopResult[] newArray(int size) {
            return new CoopResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(startTime);
        dest.writeLong(playTime);
        dest.writeInt(jobRate);
        dest.writeFloat(dangerRate);
        dest.writeInt(money);
        dest.writeInt(gradePoint);
        dest.writeInt(gradePointDelta);
        dest.writeInt(jobScore);
        dest.writeParcelable(grade, flags);
        dest.writeParcelable(myResult, flags);
        dest.writeTypedList(otherResults);
        dest.writeTypedList(bossCount);
        dest.writeTypedList(waves);
        dest.writeParcelable(shift, flags);
    }
}
