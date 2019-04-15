package com.mattrubacky.monet2.data.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.combo.ClosetStatCombo;
import com.mattrubacky.monet2.data.entity.PlayerRoom;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Ignore;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a piece of gear stored in the "Closet"
 * Not a part of the Splatnet API
 */


public class GearStats extends Stats implements Parcelable{

    public GearStats(){
    }

    public ClosetStatCombo closetStatCombo;

    @Ignore
    public Gear gear;

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


    protected GearStats(Parcel in) {
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

    public void calcStats(List<Skill> skills) {
        ArrayList<Integer> ink,kill,death,special;
        List<PlayerRoom> playerRoomList = closetStatCombo.getPlayers();

        for(Skill skill:skills){
            if(this.skills.main.id == skill.id){
                this.skills.main = skill;
            }
            if(this.skills.subs.size()>0){
                if(this.skills.subs.get(0).id == skill.id){
                    this.skills.subs.remove(0);
                    this.skills.subs.add(0,skill);
                }
                if(this.skills.subs.size()>1){
                    if(this.skills.subs.get(1).id == skill.id){
                        this.skills.subs.remove(1);
                        this.skills.subs.add(1,skill);
                    }
                    if(this.skills.subs.size()>2){
                        if(this.skills.subs.get(2).id == skill.id){
                            this.skills.subs.remove(2);
                            this.skills.subs.add(2,skill);
                        }
                    }
                }
            }
        }

        numGames= playerRoomList.size();

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

        for(PlayerRoom playerRoom:playerRoomList){
            if(playerRoom.battleResult.equals("victory")){
                wins++;
            }else{
                losses++;
            }

            inked+=playerRoom.point;
            ink.add(playerRoom.point);
            kill.add(playerRoom.kill);
            death.add(playerRoom.death);
            special.add(playerRoom.special);
        }

        if(playerRoomList.size()>5) {
            inkStats = calcSpread(sort(ink));
            killStats = calcSpread(sort(kill));
            deathStats = calcSpread(sort(death));
            specialStats = calcSpread(sort(special));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(skills, flags);
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
}
