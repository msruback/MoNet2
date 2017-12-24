package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerStats extends Stats implements Parcelable {

    public PlayerStats(){}

    @SerializedName("ink_stats")
    public int[] inkStats;

    @SerializedName("kill_stats")
    public int[] killStats;

    @SerializedName("death_stats")
    public int[] deathStats;

    @SerializedName("special_stats")
    public int[] specialStats;

    @SerializedName("lastPlayed")
    public long lastPlayed;


    protected PlayerStats(Parcel in) {
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlayerStats> CREATOR = new Creator<PlayerStats>() {
        @Override
        public PlayerStats createFromParcel(Parcel in) {
            return new PlayerStats(in);
        }

        @Override
        public PlayerStats[] newArray(int size) {
            return new PlayerStats[size];
        }
    };

    @Override
    public void calcStats(Context context) {
        ArrayList<Battle> battles;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getPlayerStats(0,"all");

        inkStats = new int[5];
        killStats = new int[5];
        deathStats = new int[5];
        specialStats = new int[5];

        ink = new ArrayList<>();
        kill = new ArrayList<>();
        death = new ArrayList<>();
        special = new ArrayList<>();

        Player player;
        for(int i=0;i<battles.size();i++){
            player = battles.get(i).user;

            ink.add(player.points);
            kill.add(player.kills);
            death.add(player.deaths);
            special.add(player.special);

        }
        if(battles.size()>0){
            lastPlayed = battles.get(battles.size()-1).start;
        }

        if(battles.size()>5) {
            inkStats = calcSpread(sort(ink));
            killStats = calcSpread(sort(kill));
            deathStats = calcSpread(sort(death));
            specialStats = calcSpread(sort(special));
        }
    }
}
