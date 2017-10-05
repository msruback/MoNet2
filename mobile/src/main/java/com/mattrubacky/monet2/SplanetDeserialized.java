package com.mattrubacky.monet2;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    @SerializedName("game_mode")
    Gamemode gamemode;
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
        gamemode = in.readParcelable(Gamemode.class.getClassLoader());
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

class Gamemode implements Parcelable{
    public Gamemode(){
    }
    @SerializedName("name")
    String name;
    @SerializedName("key")
    String key;

    protected Gamemode(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Gamemode> CREATOR = new Creator<Gamemode>() {
        @Override
        public Gamemode createFromParcel(Parcel in) {
            return new Gamemode(in);
        }

        @Override
        public Gamemode[] newArray(int size) {
            return new Gamemode[size];
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
class Product implements Parcelable{
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

    protected Product(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        price = in.readString();
        id = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeString(price);
        dest.writeString(id);
        dest.writeParcelable(skill, flags);
    }
}
class Gear implements Parcelable{
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

    protected Gear(Parcel in) {
        name = in.readString();
        brand = in.readParcelable(Brand.class.getClassLoader());
        url = in.readString();
        rarity = in.readInt();
        id = in.readInt();
        kind = in.readString();
    }

    public static final Creator<Gear> CREATOR = new Creator<Gear>() {
        @Override
        public Gear createFromParcel(Parcel in) {
            return new Gear(in);
        }

        @Override
        public Gear[] newArray(int size) {
            return new Gear[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(brand, flags);
        dest.writeString(url);
        dest.writeInt(rarity);
        dest.writeInt(id);
        dest.writeString(kind);
    }
}
class Brand implements Parcelable{
    public Brand(){}

    @SerializedName("name")
    String name;
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String url;
    @SerializedName("frequent_skill")
    Skill skill;

    protected Brand(Parcel in) {
        name = in.readString();
        id = in.readInt();
        url = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeParcelable(skill, flags);
    }
}
class Skill implements Parcelable{
    public Skill(){}

    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;
    @SerializedName("id")
    int id;

    protected Skill(Parcel in) {
        name = in.readString();
        url = in.readString();
        id = in.readInt();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(id);
    }
}

class Timeline {
    public Timeline(){}
    @SerializedName("unique_id")
    String uniqueID;

    @SerializedName("coop")
    GrizzCo currentRun;
}
class GrizzCo{
    public GrizzCo(){}

    @SerializedName("reward_gear")
    RewardGear rewardGear;
}
class RewardGear{
    public RewardGear(){}

    @SerializedName("available_time")
    long available;

    @SerializedName("gear")
    Gear gear;
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
    @SerializedName("unique_id")
    String unique_id;
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

    @SerializedName("unique_id")
    String uniqueId;
    @SerializedName("prinicipal_id")
    String id;
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
class Weapon implements Parcelable{
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

    protected Weapon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        special = in.readParcelable(Special.class.getClassLoader());
        sub = in.readParcelable(Sub.class.getClassLoader());
    }

    public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
        @Override
        public Weapon createFromParcel(Parcel in) {
            return new Weapon(in);
        }

        @Override
        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(special, flags);
        dest.writeParcelable(sub, flags);
    }
}
class Special implements Parcelable{
    public Special(){}

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image_a")
    String url;

    protected Special(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Special> CREATOR = new Creator<Special>() {
        @Override
        public Special createFromParcel(Parcel in) {
            return new Special(in);
        }

        @Override
        public Special[] newArray(int size) {
            return new Special[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
    }
}
class Sub implements Parcelable{
    public Sub(){
    }

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image_a")
    String url;

    protected Sub(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Sub> CREATOR = new Creator<Sub>() {
        @Override
        public Sub createFromParcel(Parcel in) {
            return new Sub(in);
        }

        @Override
        public Sub[] newArray(int size) {
            return new Sub[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
    }
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


//Not Technically from Splatnet, but small enough to include as a tagalong
class SalmonSchedule implements Parcelable{
    public SalmonSchedule(){}
    @SerializedName("schedule")
    ArrayList<SalmonRun> schedule;

    protected SalmonSchedule(Parcel in) {
        schedule = in.createTypedArrayList(SalmonRun.CREATOR);
    }

    public static final Creator<SalmonSchedule> CREATOR = new Creator<SalmonSchedule>() {
        @Override
        public SalmonSchedule createFromParcel(Parcel in) {
            return new SalmonSchedule(in);
        }

        @Override
        public SalmonSchedule[] newArray(int size) {
            return new SalmonSchedule[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(schedule);
    }
}
class SalmonRun implements Parcelable{
    public SalmonRun() {
    }

    @SerializedName("start")
    long startTime;
    @SerializedName("end")
    long endTime;
    @SerializedName("stage")
    String stage;
    @SerializedName("weapons")
    ArrayList<Weapon> weapons;

    protected SalmonRun(Parcel in) {
        startTime = in.readLong();
        endTime = in.readLong();
        stage = in.readString();
        weapons = in.createTypedArrayList(Weapon.CREATOR);
    }

    public static final Creator<SalmonRun> CREATOR = new Creator<SalmonRun>() {
        @Override
        public SalmonRun createFromParcel(Parcel in) {
            return new SalmonRun(in);
        }

        @Override
        public SalmonRun[] newArray(int size) {
            return new SalmonRun[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime);
        dest.writeLong(endTime);
        dest.writeString(stage);
        dest.writeTypedList(weapons);
    }
}
class GearNotifications{
    public GearNotifications(){
    }
    @SerializedName("notifications")
    ArrayList<GearNotification> notifications;
}
class GearNotification implements Parcelable{
    public GearNotification(){}
    @SerializedName("gear")
    Gear gear;
    @SerializedName("ability")
    Skill skill;
    @SerializedName("notified")
    ArrayList<Product> notified;

    protected GearNotification(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        skill = in.readParcelable(Skill.class.getClassLoader());
        notified = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<GearNotification> CREATOR = new Creator<GearNotification>() {
        @Override
        public GearNotification createFromParcel(Parcel in) {
            return new GearNotification(in);
        }

        @Override
        public GearNotification[] newArray(int size) {
            return new GearNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(skill, flags);
        dest.writeTypedList(notified);
    }
}
class StageNotifications{
    public StageNotifications(){
    }
    @SerializedName("notifications")
    ArrayList<StageNotification> notifications;
}
class StageNotification implements Parcelable{
    public StageNotification(){}

    @SerializedName("stage")
    Stage stage;
    @SerializedName("type")
    String type;
    @SerializedName("rule")
    String rule;
    @SerializedName("notified")
    ArrayList<TimePeriod> notified;

    protected StageNotification(Parcel in) {
        stage = in.readParcelable(Stage.class.getClassLoader());
        type = in.readString();
        rule = in.readString();
        notified = in.createTypedArrayList(TimePeriod.CREATOR);
    }

    public static final Creator<StageNotification> CREATOR = new Creator<StageNotification>() {
        @Override
        public StageNotification createFromParcel(Parcel in) {
            return new StageNotification(in);
        }

        @Override
        public StageNotification[] newArray(int size) {
            return new StageNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
        dest.writeString(type);
        dest.writeString(rule);
        dest.writeTypedList(notified);
    }
}