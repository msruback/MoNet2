package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Weapon;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class contains Stats on a Weapon
 * For use in the Weapon Locker
 */

public class WeaponStats extends Stats implements Parcelable{
    public WeaponStats(){}

    //The casual mode "Freshness" meter current level
    @SerializedName("win_meter")
    public Float winMeter;

    //The number of wins using this weapon
    @SerializedName("win_count")
    public int wins;

    //The number of losses using this weapon
    @SerializedName("lose_count")
    public int losses;

    //The last time this weapon was used
    @SerializedName("last_use_time")
    public Long lastUsed;

    //The max value of the "Freshness" meter, used to properly display the flag achieved
    @SerializedName("max_win_meter")
    public Float maxWinMeter;

    //The total amount inked with this weapon
    @SerializedName("total_paint_point")
    public Long totalPaintPoint;

    //The weapon itself
    @SerializedName("weapon")
    public Weapon weapon;

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


    protected WeaponStats(Parcel in) {
        winMeter = in.readFloat();
        wins = in.readInt();
        losses = in.readInt();
        lastUsed = in.readLong();
        maxWinMeter = in.readFloat();
        totalPaintPoint = in.readLong();
        weapon = in.readParcelable(Weapon.class.getClassLoader());
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readLong();
    }

    public static final Creator<WeaponStats> CREATOR = new Creator<WeaponStats>() {
        @Override
        public WeaponStats createFromParcel(Parcel in) {
            return new WeaponStats(in);
        }

        @Override
        public WeaponStats[] newArray(int size) {
            return new WeaponStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(winMeter);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeLong(lastUsed);
        dest.writeFloat(maxWinMeter);
        dest.writeLong(totalPaintPoint);
        dest.writeParcelable(weapon, flags);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeLong(numGames);
    }

    @Override
    public void calcStats(Context context) {
        ArrayList<Player> players;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        players = database.getPlayerStats(weapon.id,"weapon");

        numGames = players.size();

        inkStats = new int[5];
        killStats = new int[5];
        deathStats = new int[5];
        specialStats = new int[5];

        ink = new ArrayList<>();
        kill = new ArrayList<>();
        death = new ArrayList<>();
        special = new ArrayList<>();

        Player player;
        for(int i=0;i<players.size();i++){
            player = players.get(i);

            ink.add(player.points);
            kill.add(player.kills);
            death.add(player.deaths);
            special.add(player.special);

        }

        if(players.size()>5) {
            inkStats = calcSpread(sort(ink));
            killStats = calcSpread(sort(kill));
            deathStats = calcSpread(sort(death));
            specialStats = calcSpread(sort(special));
        }
    }
}


