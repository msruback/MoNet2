package com.mattrubacky.monet2.data.deserialized.splatoon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;


/**
 * Created by mattr on 10/17/2017.
 * This class represents individual Battles
 */
public class Battle implements Parcelable{
    public Battle(){
        time = (long)0;
    }

    @SerializedName("battle_number")
    @Expose
    public int id;

    @SerializedName("event_type")
    public KeyName eventType;

    //The user's stats and info
    @SerializedName("player_result")
    public Player user;

    //The rule used, such as Turf War, Splatzones, etc
    @SerializedName("rule")
    public KeyName rule;

    //The mode, such as regular, gachi(ranked), league, fes
    @SerializedName("type")
    public String type;

    @SerializedName("fes_mode")
    public KeyName fesMode;

    //Stage the match took place on
    @SerializedName("stage")
    public Stage stage;

    //The result for the player
    @SerializedName("my_team_result")
    public KeyName result;

    //In competitive modes the amount the objective was pushed or held. null for casual modes
    @SerializedName("my_team_count")
    public int myTeamCount;
    @SerializedName("other_team_count")
    public int otherTeamCount;

    //In casual modes the percent the stage was inked. Note: Unless the stage is completly inked, it does not add up to 100%;
    @SerializedName("my_team_percentage")
    public float myTeamPercent;
    @SerializedName("other_team_percentage")
    public float otherTeamPercent;

    //In the new fes mode score is a bit different
    @SerializedName("uniform_bonus")
    public float uniformBonus;

    //The players on the user's team, minus themselves. As far as I know, will always be size()==3
    @SerializedName("my_team_members")
    public ArrayList<Player> myTeam;

    //The players on the other team. As far as I know, will always be size()==4
    @SerializedName("other_team_members")
    public ArrayList<Player> otherTeam;

    //In competitive modes, the time the match took. Will be null for casual modes.
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("elapsed_time")
    public Long time;

    //The time the match started
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("start_time")
    public Long start;

    //For casual modes, the "freshness" of a weapon.
    @SerializedName("win_meter")
    public Float winMeter;

    //If the battle is part of a Splatfest, this will refer to the Splatfest in question, null otherwise
    @SerializedName("fes_id")
    public int splatfestID;

    //If the battle is part of a Splatfest, this will contain the user's festival power level, null otherwise
    @SerializedName("my_estimate_fes_power")
    public int myFesPower;

    @SerializedName("my_team_consecutive_win")
    public int myConsecutiveWins;

    @SerializedName("my_team_another_name")
    public String myTeamName;

    @SerializedName("other_estimate_fes_power")
    public int otherFesPower;

    @SerializedName("other_team_consecutive_win")
    public int otherConsecutiveWins;

    @SerializedName("other_team_another_name")
    public String otherTeamName;

    @SerializedName("fes_point")
    public int fesPoint;

    //Resulting grade in a match. Grade in player is Starting Grade
    @SerializedName("fes_grade")
    public SplatfestGrade grade;

    @SerializedName("contribution_point")
    public int contributionPoints;

    //For ranked battles, this will contain the power level of the match, null otherwise
    @SerializedName("estimate_gachi_power")
    public int gachiPower;

    //If the battle is part of a Splatfest, these will contain the team splatfest themes for each team, null otherwise
    @SerializedName("my_team_fes_theme")
    public TeamTheme myTheme;
    @SerializedName("other_team_fes_theme")
    public TeamTheme otherTheme;

    protected Battle(Parcel in) {
        id = in.readInt();
        eventType = in.readParcelable(KeyName.class.getClassLoader());
        user = in.readParcelable(Player.class.getClassLoader());
        rule = in.readParcelable(KeyName.class.getClassLoader());
        type = in.readString();
        stage = in.readParcelable(Stage.class.getClassLoader());
        result = in.readParcelable(KeyName.class.getClassLoader());
        myTeamCount = in.readInt();
        otherTeamCount = in.readInt();
        myTeamPercent = in.readFloat();
        otherTeamPercent = in.readFloat();
        uniformBonus = in.readFloat();
        myTeam = in.createTypedArrayList(Player.CREATOR);
        otherTeam = in.createTypedArrayList(Player.CREATOR);
        time = in.readLong();
        start = in.readLong();
        splatfestID = in.readInt();
        myFesPower = in.readInt();
        myConsecutiveWins = in.readInt();
        myTeamName = in.readString();
        otherFesPower = in.readInt();
        otherConsecutiveWins = in.readInt();
        otherTeamName = in.readString();
        fesPoint = in.readInt();
        grade = in.readParcelable(SplatfestGrade.class.getClassLoader());
        contributionPoints = in.readInt();
        gachiPower = in.readInt();
        myTheme = in.readParcelable(TeamTheme.class.getClassLoader());
        otherTheme = in.readParcelable(TeamTheme.class.getClassLoader());
    }

    public static final Parcelable.Creator<Battle> CREATOR = new Parcelable.Creator<Battle>() {
        @Override
        public Battle createFromParcel(Parcel in) {
            return new Battle(in);
        }

        @Override
        public Battle[] newArray(int size) {
            return new Battle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(eventType,flags);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(rule, flags);
        dest.writeString(type);
        dest.writeParcelable(stage, flags);
        dest.writeParcelable(result, flags);
        dest.writeInt(myTeamCount);
        dest.writeInt(otherTeamCount);
        dest.writeFloat(myTeamPercent);
        dest.writeFloat(otherTeamPercent);
        dest.writeFloat(uniformBonus);
        dest.writeTypedList(myTeam);
        dest.writeTypedList(otherTeam);
        dest.writeLong(time);
        dest.writeLong(start);
        dest.writeInt(splatfestID);
        dest.writeInt(myFesPower);
        dest.writeInt(myConsecutiveWins);
        dest.writeString(myTeamName);
        dest.writeInt(otherFesPower);
        dest.writeInt(otherConsecutiveWins);
        dest.writeString(otherTeamName);
        dest.writeInt(fesPoint);
        dest.writeParcelable(grade,flags);
        dest.writeInt(contributionPoints);
        dest.writeInt(gachiPower);
        dest.writeParcelable(myTheme, flags);
        dest.writeParcelable(otherTheme, flags);
    }
}
