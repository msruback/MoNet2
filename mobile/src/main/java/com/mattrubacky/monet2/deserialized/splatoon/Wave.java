package com.mattrubacky.monet2.deserialized.splatoon;

import android.app.usage.UsageEvents;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 10/9/2018.
 */

public class Wave implements Parcelable{
    public Wave(){}

    @SerializedName("quota_num")
    public int quotaNum;

    @SerializedName("ikura_num")
    public int powerEggNum;

    @SerializedName("golden_ikura_num")
    public int goldenEggNum;

    @SerializedName("golden_ikura_pop_num")
    public int goldenEggPop;

    @SerializedName("water_level")
    public KeyName waterLevel;

    @SerializedName("event_type")
    public KeyName eventType;

    public int job;

    public int num;

    protected Wave(Parcel in) {
        quotaNum = in.readInt();
        powerEggNum = in.readInt();
        goldenEggNum = in.readInt();
        goldenEggPop = in.readInt();
        waterLevel = in.readParcelable(KeyName.class.getClassLoader());
        eventType = in.readParcelable(KeyName.class.getClassLoader());
        job = in.readInt();
        num = in.readInt();
    }

    public static final Creator<Wave> CREATOR = new Creator<Wave>() {
        @Override
        public Wave createFromParcel(Parcel in) {
            return new Wave(in);
        }

        @Override
        public Wave[] newArray(int size) {
            return new Wave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quotaNum);
        dest.writeInt(powerEggNum);
        dest.writeInt(goldenEggNum);
        dest.writeInt(goldenEggPop);
        dest.writeParcelable(waterLevel, flags);
        dest.writeParcelable(eventType, flags);
        dest.writeInt(job);
        dest.writeInt(num);
    }

    public static ArrayList<Wave> sort(ArrayList<Wave> waves){
        if(waves.size()>=1){
            return waves;
        }else if(waves.size()==2){
            if(waves.get(0).num<waves.get(1).num){
                return waves;
            }else{
                ArrayList newWaves = new ArrayList();
                newWaves.add(waves.get(1));
                newWaves.add(waves.get(2));
                return newWaves;
            }
        }else{
            Wave pivot = waves.remove(waves.size()/2);
            ArrayList<Wave> left = new ArrayList<>();
            ArrayList<Wave> right = new ArrayList<>();
            for(Wave wave:waves){
                if(wave.num<pivot.num){
                    left.add(wave);
                }else{
                    right.add(wave);
                }
            }
            ArrayList<Wave> result = new ArrayList<>();
            result.addAll(sort(left));
            result.add(pivot);
            result.addAll(sort(right));
            return result;
        }
    }
}
