package com.mattrubacky.monet2.data.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestGrade;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.TeamTheme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "battle",
        foreignKeys = {
            @ForeignKey(entity = SplatfestRoom.class,
                    parentColumns = "splatfest_id",
                    childColumns = "fes_id"),
            @ForeignKey(entity = Stage.class,
                    parentColumns = "stage_id",
                    childColumns = "stage")
        },
        indices = {
                @Index(name="battle_splatfest",
                        value = "fes_id"),
                @Index(name="battle_stage",
                        value = "stage")
        })
public class BattleRoom {

    //Constructor to translate from Deserialized to Room
    @Ignore
    public BattleRoom(Battle battle){
        this.id = battle.id;
        this.eventType = battle.eventType;
        this.rule = battle.rule;
        this.type = battle.type;
        this.stage = battle.stage;
        this.result = battle.result;
        this.start = battle.start;

        switch (type){
            case "regular":
                this.myTeamCount = battle.myTeamCount;
                this.otherTeamCount = battle.otherTeamCount;
                this.winMeter = battle.winMeter;
                break;
            case "gachi":
                this.gachiPower = battle.gachiPower;
                this.myTeamPercent = battle.myTeamPercent;
                this.otherTeamPercent = battle.otherTeamPercent;
                this.time = battle.time;
                break;
            case "league":
                this.myTeamPercent = battle.myTeamPercent;
                this.otherTeamPercent = battle.otherTeamPercent;
                this.time = battle.time;
                break;
            case "fes":

                this.fesId = battle.splatfestID;
                this.fesMode = battle.fesMode;
                this.myTeamCount = battle.myTeamCount;
                this.otherTeamCount = battle.otherTeamCount;
                this.uniformBonus = battle.uniformBonus;
                this.myFesPower = battle.myFesPower;
                this.myConsecutiveWins = battle.myConsecutiveWins;
                this.myTeamName = battle.myTeamName;
                this.otherFesPower = battle.otherFesPower;
                this.otherConsecutiveWins = battle.otherConsecutiveWins;
                this.otherTeamName = battle.otherTeamName;
                this.fesPoint = battle.fesPoint;
                this.grade = battle.grade;
                this.contributionPoint = battle.contributionPoints;
                this.myColor = battle.myTheme.color.getColor();
                this.myFesTeamKey = battle.myTheme.key;
                this.myFesTeamName = battle.myTheme.name;
                this.otherColor = battle.otherTheme.color.getColor();
                this.otherFesTeamKey = battle.otherTheme.key;
                this.otherFesTeamName = battle.otherTheme.name;

        }
    }

    //Rooms Constructor
    public BattleRoom(int id, KeyName eventType, KeyName rule, String type, KeyName fesMode, Stage stage, KeyName result,
                      long time, long start, float winMeter, int myTeamCount, int otherTeamCount, float myTeamPercent,
                      float otherTeamPercent, float uniformBonus, int fesId, int myFesPower, int myConsecutiveWins, String myTeamName,
                      int otherFesPower, int otherConsecutiveWins, String otherTeamName, int fesPoint, SplatfestGrade grade,
                      int contributionPoint, int gachiPower, String myColor, String myFesTeamKey, String myFesTeamName, String otherColor,
                      String otherFesTeamKey, String otherFesTeamName){
        this.id = id;
        this.eventType = eventType;
        this.rule = rule;
        this.type = type;
        this.fesMode = fesMode;
        this.stage = stage;
        this.result = result;
        this.myTeamCount = myTeamCount;
        this.otherTeamCount = otherTeamCount;
        this.myTeamPercent = myTeamPercent;
        this.otherTeamPercent = otherTeamPercent;
        this.uniformBonus = uniformBonus;
        this.time = time;
        this.start = start;
        this.winMeter = winMeter;
        this.fesId = fesId;
        this.myFesPower = myFesPower;
        this.myConsecutiveWins = myConsecutiveWins;
        this.myTeamName = myTeamName;
        this.otherFesPower = otherFesPower;
        this.otherConsecutiveWins = otherConsecutiveWins;
        this.otherTeamName = otherTeamName;
        this.fesPoint = fesPoint;
        this.grade = grade;
        this.contributionPoint = contributionPoint;
        this.gachiPower = gachiPower;
        this.myColor = myColor;
        this.myFesTeamKey = myFesTeamKey;
        this.myFesTeamName = myFesTeamName;
        this.otherColor = otherColor;
        this.otherFesTeamKey = otherFesTeamKey;
        this.otherFesTeamName = otherFesTeamName;
    }

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "event_type")
    public KeyName eventType;

    public KeyName rule;

    public String type;

    @ColumnInfo(name = "fes_mode")
    public KeyName fesMode;

    public Stage stage;

    public KeyName result;

    public Long time;

    public Long start;

    @ColumnInfo(name = "win_meter")
    public Float winMeter;

    @ColumnInfo(name = "my_team_count")
    public int myTeamCount;

    @ColumnInfo(name = "other_team_count")
    public int otherTeamCount;

    @ColumnInfo(name = "my_team_percent")
    public float myTeamPercent;

    @ColumnInfo(name = "other_team_percent")
    public float otherTeamPercent;

    @ColumnInfo(name = "uniform_bonus")
    public float uniformBonus;

    @ColumnInfo(name = "fes_id")
    public int fesId;

    @ColumnInfo(name = "my_fes_power")
    public int myFesPower;

    @ColumnInfo(name = "my_consecutive_wins")
    public int myConsecutiveWins;

    @ColumnInfo(name = "my_team_name")
    public String myTeamName;

    @ColumnInfo(name = "other_fes_power")
    public int otherFesPower;

    @ColumnInfo(name = "other_consecutive_wins")
    public int otherConsecutiveWins;

    @ColumnInfo(name = "other_team_name")
    public String otherTeamName;

    @ColumnInfo(name = "fes_point")
    public int fesPoint;

    @ColumnInfo(name = "fes_grade")
    public SplatfestGrade grade;

    @ColumnInfo(name = "contribution_point")
    public int contributionPoint;

    @ColumnInfo(name = "estimate_gachi_power")
    public int gachiPower;

    @ColumnInfo(name = "my_color")
    public String myColor;

    @ColumnInfo(name = "my_side_key")
    public String myFesTeamKey;

    @ColumnInfo(name = "my_side_name")
    public String myFesTeamName;

    @ColumnInfo(name = "other_color")
    public String otherColor;

    @ColumnInfo(name = "other_side_key")
    public String otherFesTeamKey;

    @ColumnInfo(name = "other_side_name")
    public String otherFesTeamName;

    public Battle toDeserialized(){
        Battle battle = new Battle();
        battle.id = id;
        battle.eventType = eventType;
        battle.rule = rule;
        battle.type = type;
        battle.fesMode = fesMode;
        battle.result = result;
        battle.start = start;

        switch(type){
            case "regular":
                battle.myTeamCount = myTeamCount;
                battle.otherTeamCount = otherTeamCount;
                battle.winMeter = winMeter;
                break;
            case "gachi":
                battle.myTeamPercent = myTeamPercent;
                battle.otherTeamPercent = otherTeamPercent;
                battle.time = time;
                break;
            case "league":
                battle.myTeamPercent = myTeamPercent;
                battle.otherTeamPercent = otherTeamPercent;
                battle.time = time;
                break;
            case "fes":
                battle.myTeamCount = myTeamCount;
                battle.otherTeamCount = otherTeamCount;
                battle.uniformBonus = uniformBonus;
                battle.splatfestID = fesId;
                battle.myFesPower = myFesPower;
                battle.myConsecutiveWins = myConsecutiveWins;
                battle.myTeamName = myTeamName;
                battle.otherFesPower = otherFesPower;
                battle.otherConsecutiveWins = otherConsecutiveWins;
                battle.otherTeamName = otherTeamName;
                battle.fesPoint = fesPoint;
                battle.grade = grade;
                battle.contributionPoints = contributionPoint;
                battle.gachiPower = gachiPower;
                battle.myTheme = new TeamTheme();
                battle.myTheme.color = new SplatfestColor();
                battle.myTheme.color.color = myColor;
                battle.myTheme.key = myFesTeamKey;
                battle.myTheme.name = myFesTeamName;
                battle.otherTheme = new TeamTheme();
                battle.otherTheme.color = new SplatfestColor();
                battle.otherTheme.color.color = otherColor;
                battle.otherTheme.key = otherFesTeamKey;
                battle.otherTheme.name = otherFesTeamName;
                break;
        }
        return battle;
    }

}
