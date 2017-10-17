package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.TimePeriod;

import java.util.ArrayList;

/**
 * Created by mattr on 9/23/2017.
 */


public class Schedules implements Parcelable {
    public Schedules(){

    }

    @SerializedName("regular")
    ArrayList<TimePeriod> regular;
    @SerializedName("gachi")
    ArrayList<TimePeriod> ranked;
    @SerializedName("league")
    ArrayList<TimePeriod> league;
    @SerializedName("fes")
    ArrayList<TimePeriod> splatfest;

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
    public int getLength(){
        return regular.size();
    }
    public void dequeue(){

        if(splatfest!=null&&splatfest.size()>0&&splatfest.get(0).start==regular.get(0).start){
            splatfest.remove(0);
        }
        regular.remove(0);
        ranked.remove(0);
        league.remove(0);
    }
    public void setSplatfest(Splatfest splatfest){
        long start = splatfest.times.start;
        long end = splatfest.times.end;
        this.splatfest = new ArrayList<>();
        for(int i=0;i<regular.size();i++){
            while(regular.size()>i&&(regular.get(i).end>start)&&(regular.get(i).start<=end)){
                this.splatfest.add(regular.get(i));
                regular.remove(i);
                ranked.remove(i);
                league.remove(i);
            }
        }
    }
}
