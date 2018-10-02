package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.Player;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestStats extends Stats implements Parcelable{
    public SplatfestStats(){}

    @SerializedName("splatfest")
    public Splatfest splatfest;
    @SerializedName("wins")
    public int wins;
    @SerializedName("losses")
    public int losses;
    @SerializedName("sameTeam")
    public int sameTeam;
    @SerializedName("grade")
    public String grade;
    @SerializedName("disconnects")
    public int disconnects;
    @SerializedName("power")
    public int power;
    @SerializedName("played")
    public long timePlayed;

    @SerializedName("playerInkStats")
    public int[] playerInkStats;

    @SerializedName("playerKillStats")
    public int[] playerKillStats;

    @SerializedName("playerDeathStats")
    public int[] playerDeathStats;

    @SerializedName("playerSpecialStats")
    public int[] playerSpecialStats;

    @SerializedName("playerInkAverage")
    public float playerInkAverage;

    @SerializedName("playerKillAverage")
    public float playerKillAverage;

    @SerializedName("playerDeathAverage")
    public float playerDeathAverage;

    @SerializedName("playerSpecialAverage")
    public float playerSpecialAverage;

    @SerializedName("teamInkStats")
    public int[] teamInkStats;

    @SerializedName("teamKillStats")
    public int[] teamKillStats;

    @SerializedName("teamDeathStats")
    public int[] teamDeathStats;

    @SerializedName("teamSpecialStats")
    public int[] teamSpecialStats;

    public ArrayList<Battle> battles;


    protected SplatfestStats(Parcel in) {
        splatfest = in.readParcelable(Splatfest.class.getClassLoader());
        wins = in.readInt();
        losses = in.readInt();
        grade = in.readString();
        disconnects = in.readInt();
        power = in.readInt();
        timePlayed = in.readLong();
        playerInkStats = in.createIntArray();
        playerKillStats = in.createIntArray();
        playerDeathStats = in.createIntArray();
        playerSpecialStats = in.createIntArray();
        playerInkAverage = in.readFloat();
        playerKillAverage = in.readFloat();
        playerDeathAverage = in.readFloat();
        playerSpecialAverage = in.readFloat();
        teamInkStats = in.createIntArray();
        teamKillStats = in.createIntArray();
        teamDeathStats = in.createIntArray();
        teamSpecialStats = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(splatfest,flags);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeString(grade);
        dest.writeInt(disconnects);
        dest.writeInt(power);
        dest.writeLong(timePlayed);
        dest.writeIntArray(playerInkStats);
        dest.writeIntArray(playerKillStats);
        dest.writeIntArray(playerDeathStats);
        dest.writeIntArray(playerSpecialStats);
        dest.writeFloat(playerInkAverage);
        dest.writeFloat(playerKillAverage);
        dest.writeFloat(playerDeathAverage);
        dest.writeFloat(playerSpecialAverage);
        dest.writeIntArray(teamInkStats);
        dest.writeIntArray(teamKillStats);
        dest.writeIntArray(teamDeathStats);
        dest.writeIntArray(teamSpecialStats);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplatfestStats> CREATOR = new Creator<SplatfestStats>() {
        @Override
        public SplatfestStats createFromParcel(Parcel in) {
            return new SplatfestStats(in);
        }

        @Override
        public SplatfestStats[] newArray(int size) {
            return new SplatfestStats[size];
        }
    };

    @Override
    public void calcStats(Context context) {
        ArrayList<Integer> playerInk,playerKill,playerDeath,playerSpecial,teamInk,teamKill,teamDeath,teamSpecial;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getBattleStats(splatfest.id,"splatfest");

        playerInkStats = new int[5];
        playerKillStats = new int[5];
        playerDeathStats = new int[5];
        playerSpecialStats = new int[5];

        teamInkStats = new int[5];
        teamKillStats = new int[5];
        teamDeathStats = new int[5];
        teamSpecialStats = new int[5];

        playerInkAverage = 0;
        playerKillAverage = 0;
        playerDeathAverage = 0;
        playerSpecialAverage = 0;
        wins = 0;
        losses = 0;
        sameTeam = 0;

        playerInk = new ArrayList<>();
        playerKill = new ArrayList<>();
        playerDeath = new ArrayList<>();
        playerSpecial = new ArrayList<>();

        teamInk = new ArrayList<>();
        teamKill = new ArrayList<>();
        teamDeath = new ArrayList<>();
        teamSpecial = new ArrayList<>();

        if(battles.size()>0) {
            int low = battles.get(0).id;
            int high = battles.get(battles.size() - 1).id;

            disconnects = (high - low) - battles.size();
            timePlayed = (battles.size()) * 180000;

            Player player;
            Battle battle;
            for (int i = 0; i < battles.size(); i++) {
                battle = battles.get(i);

                playerInk.add(battle.user.points);
                playerKill.add(battle.user.kills);
                playerDeath.add(battle.user.deaths);
                playerSpecial.add(battle.user.special);
                if(battle.myTheme.color.getColor().equals(battle.otherTheme.color.getColor())) {
                    sameTeam++;
                }else{
                    if (battle.result.key.equals("victory")) {
                        wins++;
                    } else {
                        losses++;
                    }
                }

                playerInkAverage += battle.user.points;
                playerKillAverage += battle.user.kills;
                playerDeathAverage += battle.user.deaths;
                playerSpecialAverage += battle.user.special;

                for (int j = 0; j < battle.myTeam.size(); j++) {
                    player = battle.myTeam.get(j);

                    teamInk.add(player.points);
                    teamKill.add(player.kills);
                    teamDeath.add(player.deaths);
                    teamSpecial.add(player.special);
                }

            }
            battle = battles.get(battles.size()-1);

            if(grade.equals("")){
                grade = battle.user.user.grade.name;
            }
            power = battle.myFesPower;

            if (battles.size() > 5) {
                playerInkStats = calcSpread(sort(playerInk));
                playerKillStats = calcSpread(sort(playerKill));
                playerDeathStats = calcSpread(sort(playerDeath));
                playerSpecialStats = calcSpread(sort(playerSpecial));

                playerInkAverage /= battles.size();
                playerKillAverage /= battles.size();
                playerDeathAverage /= battles.size();
                playerSpecialAverage /= battles.size();

                teamInkStats = calcSpread(sort(teamInk));
                teamKillStats = calcSpread(sort(teamKill));
                teamDeathStats = calcSpread(sort(teamDeath));
                teamSpecialStats = calcSpread(sort(teamSpecial));
            }
        }
    }
}
