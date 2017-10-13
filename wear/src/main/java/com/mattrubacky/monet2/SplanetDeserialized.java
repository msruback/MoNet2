package com.mattrubacky.monet2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 9/23/2017.
 */


class Schedules implements Parcelable {
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
        for(int i=0;i<getLength();i++){
            if(regular.get(i).start>start&&regular.get(i).start<end){
                this.splatfest.add(regular.get(i));
                regular.remove(i);
                ranked.remove(i);
                league.remove(i);
            }
        }
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


//Not Technically from Splatnet, but small enough to include as a tagalong
class SalmonSchedule{
    public SalmonSchedule(){}
    @SerializedName("schedule")
    ArrayList<SalmonRun> schedule;
}

class SalmonRun {
    public SalmonRun() {
    }

    @SerializedName("start")
    long startTime;
    @SerializedName("end")
    long endTime;
    @SerializedName("stage")
    String stage;
}

class CurrentSplatfest{
    public CurrentSplatfest(){}
    @SerializedName("festivals")
    ArrayList<Splatfest> splatfests;
}
class Splatfest{
    public Splatfest(){}

    @SerializedName("festival_id")
    int id;
    @SerializedName("times")
    SplatfestTimes times;
    @SerializedName("colors")
    SplatfestColors colors;
    @SerializedName("names")
    SplatfestNames names;
    @SerializedName("special_stage")
    Stage stage;
}

class SplatfestTimes{
    public SplatfestTimes(){}

    @SerializedName("start")
    Long start;
    @SerializedName("end")
    Long end;
    @SerializedName("announce")
    Long announce;
    @SerializedName("result")
    Long result;
}
class SplatfestColors{
    public SplatfestColors(){
    }
    @SerializedName("alpha")
    SplatfestColor alpha;
    @SerializedName("bravo")
    SplatfestColor bravo;
}
class SplatfestColor{
    public SplatfestColor(){}
    @SerializedName("b")
    double b;
    @SerializedName("r")
    double r;
    @SerializedName("g")
    double g;
    public String getColor(){
        StringBuilder builder = new StringBuilder();
        builder.append("#");
        int colorNum = (int) (255*r);
        builder.append(getHexDigit(colorNum/16));
        builder.append(getHexDigit(colorNum%16));
        colorNum = (int) (255*g);
        builder.append(getHexDigit(colorNum/16));
        builder.append(getHexDigit(colorNum%16));
        colorNum = (int) (255*b);
        builder.append(getHexDigit(colorNum/16));
        builder.append(getHexDigit(colorNum%16));
        return builder.toString();
    }
    private String getHexDigit(int num){
        switch(num){
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            default:
                return String.valueOf(num);
        }
    }
}
class SplatfestNames{
    public SplatfestNames(){}

    @SerializedName("bravo_short")
    String bravo;
    @SerializedName("bravo_long")
    String bravoDesc;
    @SerializedName("alpha_short")
    String alpha;
    @SerializedName("alpha_long")
    String alphaDesc;
}