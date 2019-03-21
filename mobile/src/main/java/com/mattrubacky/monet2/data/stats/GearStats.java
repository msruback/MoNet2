package com.mattrubacky.monet2.data.stats;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a piece of gear stored in the "Closet"
 * Not a part of the Splatnet API
 */

@Entity(tableName = "closet",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "gear"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "main"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "fr_sub"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "sc_sub"
                ),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "tr_sub"
                )
        },
        indices = {
                @Index(value = "gear"),
                @Index(value = "main"),
                @Index(value="fr_sub"),
                @Index(value = "sc_sub"),
                @Index(value = "tr_sub")
        })
public class GearStats extends Stats implements Parcelable{
    @Ignore
    public GearStats(){
    }
    @PrimaryKey
    public Gear gear;

    public Skill main;
    @ColumnInfo(name = "fr_sub")
    public Skill sub1;
    @ColumnInfo(name = "sc_sub")
    public Skill sub2;
    @ColumnInfo(name = "tr_sub")
    public Skill sub3;

    @Ignore
    public GearSkills skills;

    @Ignore
    public int wins;
    @Ignore
    public int losses;
    @Ignore
    public long time;
    @Ignore
    public int[] inkStats;
    @Ignore
    public int[] killStats;
    @Ignore
    public int[] deathStats;
    @Ignore
    public int[] specialStats;
    @Ignore
    public int numGames;
    @Ignore
    public long inked;

    public GearStats(Gear gear, Skill main, Skill sub1, Skill sub2, Skill sub3){
        this.gear = gear;
        this.main = main;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
    }

    @Ignore
    protected GearStats(Parcel in) {
        gear = in.readParcelable(Gear.class.getClassLoader());
        main = in.readParcelable(Skill.class.getClassLoader());
        sub1 = in.readParcelable(Skill.class.getClassLoader());
        sub2 = in.readParcelable(Skill.class.getClassLoader());
        sub3 = in.readParcelable(Skill.class.getClassLoader());
        skills = in.readParcelable(GearSkills.class.getClassLoader());
        wins = in.readInt();
        losses = in.readInt();
        time = in.readLong();
        inkStats = in.createIntArray();
        killStats = in.createIntArray();
        deathStats = in.createIntArray();
        specialStats = in.createIntArray();
        numGames = in.readInt();
        inked = in.readLong();
    }

    public GearSkills getSkills(){
        if(skills!=null){
            skills = new GearSkills();
            skills.main = main;
            skills.subs = new ArrayList<>();
            skills.subs.add(sub1);
            skills.subs.add(sub2);
            skills.subs.add(sub3);
        }
        return skills;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(gear, flags);
        dest.writeParcelable(main, flags);
        dest.writeParcelable(sub1, flags);
        dest.writeParcelable(sub2, flags);
        dest.writeParcelable(sub3, flags);
        dest.writeParcelable(skills,flags);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeLong(time);
        dest.writeIntArray(inkStats);
        dest.writeIntArray(killStats);
        dest.writeIntArray(deathStats);
        dest.writeIntArray(specialStats);
        dest.writeInt(numGames);
        dest.writeLong(inked);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GearStats> CREATOR = new Creator<GearStats>() {
        @Override
        public GearStats createFromParcel(Parcel in) {
            return new GearStats(in);
        }

        @Override
        public GearStats[] newArray(int size) {
            return new GearStats[size];
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
