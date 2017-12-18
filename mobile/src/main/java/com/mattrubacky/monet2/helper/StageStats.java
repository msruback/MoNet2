package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the User's stats on a certain stage
 */
public class StageStats extends Stats implements Parcelable{
    public StageStats(){}

    //The Stage in question
    @SerializedName("stage")
    public Stage stage;

    //The wins on this Stage under Turf War
    @SerializedName("turf_win")
    public int turfWin;

    //The losses on this Stage under Turf War
    @SerializedName("turf_lose")
    public int turfLose;

    //The wins on this Stage under the Tower Control rule
    @SerializedName("yagura_win")
    public int towerWin;

    //The losses on this stage under the Tower Control rule
    @SerializedName("yagura_lose")
    public int towerLose;

    //The wins on this Stage under the Rainmaker rule
    @SerializedName("hoko_win")
    public int rainmakerWin;

    //The losses on this Stage under the Rainmaker rule
    @SerializedName("hoko_lose")
    public int rainmakerLose;

    //The wins on this Stage under the Splatzone rule
    @SerializedName("area_win")
    public int splatzonesWin;

    //The losses on this Stage under the Splatzone rule
    @SerializedName("area_lose")
    public int splatzonesLose;

    //The wins on this Stage under the Clam Blitz rule
    @SerializedName("asari_win")
    public int clamWin;

    //The losses on this Stage under the Clam Blitz rules
    @SerializedName("asari_lose")
    public int clamLose;

    //The last time the user played on this stage
    //IMPORTANT: This is in seconds from epoch, Java takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("last_play_time")
    public Long lastPlayed;

    @SerializedName("inkStats")
    public int[] inkStats;

    @SerializedName("killStats")
    public int[] killStats;

    @SerializedName("deathStats")
    public int[] deathStats;

    @SerializedName("specialStats")
    public int[] specialStats;

    @SerializedName("numGames")
    public long numGames;

    public ArrayList<Battle> battles;

    protected StageStats(Parcel in) {
        stage = in.readParcelable(Stage.class.getClassLoader());
        turfWin = in.readInt();
        turfLose = in.readInt();
        towerWin = in.readInt();
        towerLose = in.readInt();
        rainmakerWin = in.readInt();
        rainmakerLose = in.readInt();
        splatzonesWin = in.readInt();
        splatzonesLose = in.readInt();
        clamWin = in.readInt();
        clamLose = in.readInt();
        lastPlayed = in.readLong();
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stage, flags);
        dest.writeInt(turfWin);
        dest.writeInt(turfLose);
        dest.writeInt(towerWin);
        dest.writeInt(towerLose);
        dest.writeInt(rainmakerWin);
        dest.writeInt(rainmakerLose);
        dest.writeInt(splatzonesWin);
        dest.writeInt(splatzonesLose);
        dest.writeInt(clamWin);
        dest.writeInt(clamLose);
        dest.writeLong(lastPlayed);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeLong(numGames);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StageStats> CREATOR = new Creator<StageStats>() {
        @Override
        public StageStats createFromParcel(Parcel in) {
            return new StageStats(in);
        }

        @Override
        public StageStats[] newArray(int size) {
            return new StageStats[size];
        }
    };

    @Override
    public void calcStats(Context context) {
        ArrayList<Battle> battles;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getBattleStats(stage.id,"stage");

        numGames = battles.size();

        inkStats = new int[5];
        killStats = new int[5];
        deathStats = new int[5];
        specialStats = new int[5];

        ink = new ArrayList<>();
        kill = new ArrayList<>();
        death = new ArrayList<>();
        special = new ArrayList<>();

        turfWin=0;
        turfLose=0;

        Battle battle;
        for(int i=0;i<battles.size();i++){
            battle = battles.get(i);

            ink.add(battle.user.points);
            kill.add(battle.user.kills);
            death.add(battle.user.deaths);
            special.add(battle.user.special);
            if(battle.rule.key.equals("turf_war")){
                if(battle.result.key.equals("victory")){
                    turfWin++;
                }else{
                    turfLose++;
                }
            }

        }

        if(battles.size()>5) {
            inkStats = calcSpread(sort(ink));
            killStats = calcSpread(sort(kill));
            deathStats = calcSpread(sort(death));
            specialStats = calcSpread(sort(special));
        }

    }
}
