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
                this.myTeamPercent = battle.myTeamPercent;
                this.otherTeamPercent = battle.otherTeamPercent;
                this.winMeter = battle.winMeter;
                break;
            case "gachi":
                this.gachiPower = battle.gachiPower;
                this.myTeamCount = battle.myTeamCount;
                this.otherTeamCount = battle.otherTeamCount;
                this.time = battle.time;
                break;
            case "league":
                this.myTeamCount = battle.myTeamCount;
                this.otherTeamCount = battle.otherTeamCount;
                this.time = battle.time;
                break;
            case "fes":

                this.fesId = battle.splatfestID;
                this.fesMode = battle.fesMode;
                this.myTeamPercent = battle.myTeamPercent;
                this.otherTeamPercent = battle.otherTeamPercent;
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
                      Long time, Long start, Float winMeter, Integer myTeamCount, Integer otherTeamCount, Float myTeamPercent,
                      Float otherTeamPercent, Float uniformBonus, Integer fesId, Integer myFesPower, Integer myConsecutiveWins, String myTeamName,
                      Integer otherFesPower, Integer otherConsecutiveWins, String otherTeamName, Integer fesPoint, SplatfestGrade grade,
                      Integer contributionPoint, Integer gachiPower, String myColor, String myFesTeamKey, String myFesTeamName, String otherColor,
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
    public Integer myTeamCount;

    @ColumnInfo(name = "other_team_count")
    public Integer otherTeamCount;

    @ColumnInfo(name = "my_team_percent")
    public Float myTeamPercent;

    @ColumnInfo(name = "other_team_percent")
    public Float otherTeamPercent;

    @ColumnInfo(name = "uniform_bonus")
    public Float uniformBonus;

    @ColumnInfo(name = "fes_id")
    public Integer fesId;

    @ColumnInfo(name = "my_fes_power")
    public Integer myFesPower;

    @ColumnInfo(name = "my_consecutive_wins")
    public Integer myConsecutiveWins;

    @ColumnInfo(name = "my_team_name")
    public String myTeamName;

    @ColumnInfo(name = "other_fes_power")
    public Integer otherFesPower;

    @ColumnInfo(name = "other_consecutive_wins")
    public Integer otherConsecutiveWins;

    @ColumnInfo(name = "other_team_name")
    public String otherTeamName;

    @ColumnInfo(name = "fes_point")
    public Integer fesPoint;

    @ColumnInfo(name = "fes_grade")
    public SplatfestGrade grade;

    @ColumnInfo(name = "contribution_point")
    public Integer contributionPoint;

    @ColumnInfo(name = "estimate_gachi_power")
    public Integer gachiPower;

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
                battle.myTeamPercent = myTeamPercent;
                battle.otherTeamPercent = otherTeamPercent;
                battle.winMeter = winMeter;
                break;
            case "gachi":
                battle.myTeamCount = myTeamCount;
                battle.otherTeamCount = otherTeamCount;
                battle.time = time;
                break;
            case "league":
                battle.myTeamCount = myTeamCount;
                battle.otherTeamCount = otherTeamCount;
                battle.time = time;
                break;
            case "fes":
                battle.myTeamPercent = myTeamPercent;
                battle.otherTeamPercent = otherTeamPercent;
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
