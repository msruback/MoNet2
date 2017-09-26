package com.mattrubacky.monet2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.Result;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

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
        regular.remove(0);
        ranked.remove(0);
        league.remove(0);
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

class Annie {
    public Annie(){}

    @SerializedName("ordered_info")
    Ordered ordered;
    @SerializedName("merchandises")
    ArrayList<Product> merch;
}
class Ordered{
    public Ordered(){}

    @SerializedName("gear")
    Gear gear;
    @SerializedName("price")
    String price;
    @SerializedName("skill")
    Skill skill;

}
class Product{
    public Product(){}
    @SerializedName("gear")
    Gear gear;
    @SerializedName("price")
    String price;
    @SerializedName("id")
    String id;
    @SerializedName("skill")
    Skill skill;
    @SerializedName("end_time")
    Long endTime;

}
class Gear{
    public Gear(){}

    @SerializedName("name")
    String name;
    @SerializedName("brand")
    Brand brand;
    @SerializedName("image")
    String url;
    @SerializedName("rarity")
    int rarity;
    @SerializedName("id")
    int id;
    @SerializedName("kind")
    String kind;
}
class Brand{
    public Brand(){}

    @SerializedName("name")
    String name;
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String url;
    @SerializedName("frequent_skill")
    Skill skill;
}
class Skill{
    public Skill(){}

    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;
    @SerializedName("id")
    int id;
}

class Timeline {
    public Timeline(){};
    @SerializedName("unique_id")
    String uniqueID;

    @SerializedName("coop")
    GrizzCo currentRun;
}
class GrizzCo{
    public GrizzCo(){};
}


class PastSplatfest{
    public PastSplatfest(){}

    @SerializedName("festivals")
    ArrayList<Splatfest> splatfests;
    @SerializedName("results")
    ArrayList<SplatfestResult> results;
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
    String alpha;
    @SerializedName("bravo")
    String bravo;
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

class SplatfestResult{
    public SplatfestResult(){}

    @SerializedName("festival_id")
    int id;
    @SerializedName("team_scores")
    SplatfestTeamScores teamScores;
    @SerializedName("summary")
    SplatfestSummary summary;
    @SerializedName("team_participants")
    SplatfestParticipants participants;
}
class SplatfestTeamScores{
    public SplatfestTeamScores(){}

    @SerializedName("alpha_solo")
    int alphaSolo;
    @SerializedName("alpha_team")
    int alphaTeam;
    @SerializedName("bravo_solo")
    int bravoSolo;
    @SerializedName("bravo_team")
    int bravoTeam;
}
class SplatfestSummary{
    public SplatfestSummary(){}

    @SerializedName("team")
    int team;
    @SerializedName("solo")
    int solo;
    @SerializedName("vote")
    int vote;
    @SerializedName("total")
    int total;
}
class SplatfestParticipants{
    public SplatfestParticipants(){}

    @SerializedName("alpha")
    int alpha;
    @SerializedName("bravo")
    int bravo;
}

class Record{
    public Record(){}

    @SerializedName("records")
    Records records;
}
class Records{
    public Records(){}

    @SerializedName("stage_stats")
    Map<Integer,StageStats> stageStats;
    @SerializedName("fes_results")
    Map<Integer,SplatfestRecords> splatfestRecords;
    @SerializedName("player")
    User user;
    @SerializedName("weapon_stats")
    Map<Integer,WeaponStats> weaponStats;

}
class StageStats{
    public StageStats(){}

    @SerializedName("stage")
    Stage stage;
    @SerializedName("yagura_win")
    int rainmakerWin;
    @SerializedName("yagura_lose")
    int rainmakerLose;
    @SerializedName("area_win")
    int splatzonesWin;
    @SerializedName("area_lose")
    int splatzonesLose;
    @SerializedName("hoko_win")
    int towerWin;
    @SerializedName("hoko_lose")
    int towerLose;
    @SerializedName("last_play_time")
    Long lastPlayed;
}
class SplatfestRecords{
    public SplatfestRecords(){}

    @SerializedName("fes_point")
    int points;
    @SerializedName("fes_power")
    int power;
    @SerializedName("fes_id")
    int id;
    @SerializedName("fes_grade")
    SplatfestGrade grade;
}
class SplatfestGrade{
    public SplatfestGrade(){
    }
    @SerializedName("name")
    String name;
}
class User{
    public User(){}

    @SerializedName("prinicipal_id")
    int id;
    @SerializedName("nickname")
    String name;
    @SerializedName("player_rank")
    int rank;
    @SerializedName("udemae_tower")
    Rank tower;
    @SerializedName("udemae_rainmaker")
    Rank rainmaker;
    @SerializedName("udemae_zones")
    Rank splatzones;
    @SerializedName("udemae")
    Rank udamae;
    @SerializedName("weapon")
    Weapon weapon;
    @SerializedName("head_skills")
    GearSkills headSkills;
    @SerializedName("clothes_skills")
    GearSkills clothesSkills;
    @SerializedName("shoes_skills")
    GearSkills shoeSkills;
    @SerializedName("head")
    Gear head;
    @SerializedName("clothes")
    Gear clothes;
    @SerializedName("shoes")
    Gear shoes;
}
class Rank {
    public Rank(){}
    @SerializedName("name")
    String rank;
    @SerializedName("s_plus_number")
    String sPlus;
}
class Weapon{
    public Weapon(){}
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;
    @SerializedName("special")
    Special special;
    @SerializedName("sub")
    Sub sub;
}
class Special{
    public Special(){}

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image_a")
    String url;
}
class Sub{
    public Sub(){
    }

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image_a")
    String url;
}
class GearSkills{
    public GearSkills(){}
    @SerializedName("main")
    Skill main;
    @SerializedName("subs")
    ArrayList<Skill> subs;
}
class WeaponStats{
    public WeaponStats(){}

    @SerializedName("win_meter")
    Float winMeter;
    @SerializedName("win_count")
    int wins;
    @SerializedName("lose_count")
    int losses;
    @SerializedName("last_use_time")
    Long lastUsed;
    @SerializedName("max_win_meter")
    Float maxWinMeter;
    @SerializedName("total_paint_point")
    Long totalPaintPoint;
    @SerializedName("weapon")
    Weapon weapon;
}

class ResultList{
    public ResultList(){
    }

    @SerializedName("results")
    ArrayList<ResultId> resultIds;
}
class ResultId{
    public ResultId(){
    }

    @SerializedName("battle_number")
    int id;
}
class Battle{
    public Battle(){}

    @SerializedName("battle_number")
    int id;
    @SerializedName("player_result")
    Player user;
    @SerializedName("rule")
    Rule rule;
    @SerializedName("type")
    String type;
    @SerializedName("stage")
    Stage stage;
    @SerializedName("my_team_result")
    TeamResult result;
    @SerializedName("my_team_count")
    int myTeamCount;
    @SerializedName("other_team_count")
    int otherTeamCount;
    @SerializedName("my_team_percentage")
    float myTeamPercent;
    @SerializedName("other_team_percentage")
    float otherTeamPercent;
    @SerializedName("my_team_members")
    ArrayList<Player> myTeam;
    @SerializedName("other_team_members")
    ArrayList<Player> otherTeam;
    @SerializedName("elapsed_time")
    Long time;
    @SerializedName("start_time")
    Long start;
    @SerializedName("win_meter")
    Float winMeter;
    @SerializedName("fes_id")
    int splatfestID;
    @SerializedName("my_estimate_fes_power")
    int fesPower;
    @SerializedName("estimate_gachi_power")
    int gachiPower;
}
class Player{
    public Player(){}

    @SerializedName("player")
    User user;
    @SerializedName("death_count")
    int deaths;
    @SerializedName("assist_count")
    int assists;
    @SerializedName("kill_count")
    int kills;
    @SerializedName("game_paint_point")
    int points;
    @SerializedName("special_count")
    int special;
    @SerializedName("fes_grade")
    SplatfestGrade grade;
}
class TeamResult{
    public TeamResult(){}

    @SerializedName("name")
    String name;
}
