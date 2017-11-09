package com.mattrubacky.monet2.helper;

import android.content.Context;

import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Weapon;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 9/30/2017.
 */

public class StatCalc {
    private int[] inkStats,killStats,deathStats,specialStats;
    private int num;

    //WeaponStat constructor
    public StatCalc(Context context, Weapon weapon){
        ArrayList<Player> players;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        players = database.getPlayerStats(weapon.id,"weapon");

        num = players.size();

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
            inkStats = calcStats(sort(ink));
            killStats = calcStats(sort(kill));
            deathStats = calcStats(sort(death));
            specialStats = calcStats(sort(special));
        }

    }

    //GearStats constructor
    public StatCalc(Context context, Gear gear){
        ArrayList<Player> players;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        players = database.getPlayerStats(gear.id,gear.kind);

        num = players.size();

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
            inkStats = calcStats(sort(ink));
            killStats = calcStats(sort(kill));
            deathStats = calcStats(sort(death));
            specialStats = calcStats(sort(special));
        }

    }

    private ArrayList<Integer> sort(ArrayList<Integer> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0)<=data.get(1)){
                return data;
            }else{
                int hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            int pivot = data.get(0);
            ArrayList<Integer> lower = new ArrayList<>();
            ArrayList<Integer> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot>data.get(i)){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<Integer> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }

    private int[] calcStats(ArrayList<Integer> data){
        int[] stats = new int[5];
        //minimum;
        stats[0] = data.get(0);

        //median
        int lower,upper;
        if(data.size()%2==0){
            stats[2] = data.get(data.size()/2);
        }else{
            lower = data.get(data.size()/2);
            upper = data.get((data.size()/2)+1);
            lower+= upper;
            stats[2] = lower/2;
        }

        //quartiles
        if (data.size() % 4 == 0) {
            stats[1] = data.get(data.size()/4);
            stats[3] = data.get((data.size()/2)+(data.size()/4));
        }else{
            lower = data.get(data.size()/4);
            upper = data.get((data.size()/4)+1);
            lower += upper;
            stats[1] = lower/2;

            lower = data.get((data.size()/2)+(data.size()/4));
            upper = data.get((data.size()/2)+(data.size()/4)+1);
            lower += upper;
            stats[3] = lower/2;
        }

        //maximum
        stats[4] = data.get(data.size()-1);

        return stats;
    }

    public int[] getInkStats(){
        return inkStats;
    }

    public int[] getKillStats(){
        return killStats;
    }

    public int[] getDeathStats(){
        return deathStats;
    }

    public int[] getSpecialStats(){
        return specialStats;
    }

    public int getNum(){
        return num;
    }

}
