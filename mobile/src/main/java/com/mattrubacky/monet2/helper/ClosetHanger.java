package com.mattrubacky.monet2.helper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.Player;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a piece of gear stored in the "Closet"
 * Not a part of the Splatnet API
 */

public class ClosetHanger extends Stats implements Parcelable{
    public ClosetHanger(){
    }

    @SerializedName("gear")
    public Gear gear;
    @SerializedName("skills")
    public GearSkills skills;
    @SerializedName("wins")
    public int wins;
    @SerializedName("losses")
    public int losses;
    @SerializedName("last_use_time")
    public long time;
    @SerializedName("ink_stats")
    public int[] inkStats;
    @SerializedName("kill_stats")
    public int[] killStats;
    @SerializedName("death_stats")
    public int[] deathStats;
    @SerializedName("special_stats")
    public int[] specialStats;
    @SerializedName("num_games")
    public int numGames;
    @SerializedName("inked")
    public long inked;

    protected ClosetHanger(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        skills = in.readParcelable(GearSkills.class.getClassLoader());
        wins = in.readInt();
        losses = in.readInt();
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readInt();
        inked = in.readLong();
        time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(skills, flags);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeInt(numGames);
        dest.writeLong(inked);
        dest.writeLong(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClosetHanger> CREATOR = new Creator<ClosetHanger>() {
        @Override
        public ClosetHanger createFromParcel(Parcel in) {
            return new ClosetHanger(in);
        }

        @Override
        public ClosetHanger[] newArray(int size) {
            return new ClosetHanger[size];
        }
    };

    @Override
    public void calcStats(Context context) {
        ArrayList<Battle> battles;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getPlayerStats(gear.id,gear.kind);

        numGames= battles.size();

        inkStats = new int[5];
        killStats = new int[5];
        deathStats = new int[5];
        specialStats = new int[5];

        ink = new ArrayList<>();
        kill = new ArrayList<>();
        death = new ArrayList<>();
        special = new ArrayList<>();

        inked = 0;
        wins = 0;
        losses = 0;

        Player player;
        Battle battle;
        for(int i=0;i<battles.size();i++){
            battle = battles.get(i);
            player = battle.user;

            if(battle.result.key.equals("victory")){
                wins++;
            }else{
                losses++;
            }

            inked+=player.points;
            ink.add(player.points);
            kill.add(player.kills);
            death.add(player.deaths);
            special.add(player.special);
        }

        if(battles.size()>5) {
            inkStats = calcSpread(sort(ink));
            killStats = calcSpread(sort(kill));
            deathStats = calcSpread(sort(death));
            specialStats = calcSpread(sort(special));
        }
    }
}
