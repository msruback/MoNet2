package com.mattrubacky.monet2.helper;

import android.content.Context;

import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.deserialized.Weapon;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 9/30/2017.
 */

public class StatCalc {
    private int[] inkStats,killStats,deathStats,specialStats;
    private int num;
    private SplatfestStats splatfestStats;
    private ArrayList<Battle> battles;

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

    //StageStats constructor
    public StatCalc(Context context,Stage stage){
        ArrayList<Battle> battles;
        ArrayList<Integer> ink,kill,death,special;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getBattleStats(stage.id,"stage");

        num = battles.size();

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

        if(battles.size()>5) {
            inkStats = calcStats(sort(ink));
            killStats = calcStats(sort(kill));
            deathStats = calcStats(sort(death));
            specialStats = calcStats(sort(special));
        }
    }

    //Splatfest constructor, does not get power or grade
    public StatCalc(Context context, Splatfest splatfest){
        ArrayList<Integer> playerInk,playerKill,playerDeath,playerSpecial,teamInk,teamKill,teamDeath,teamSpecial;
        SplatnetSQLManager database = new SplatnetSQLManager(context);

        battles = database.getBattleStats(splatfest.id,"splatfest");

        splatfestStats = new SplatfestStats();

        splatfestStats.playerInkStats = new int[5];
        splatfestStats.playerKillStats = new int[5];
        splatfestStats.playerDeathStats = new int[5];
        splatfestStats.playerSpecialStats = new int[5];

        splatfestStats.teamInkStats = new int[5];
        splatfestStats.teamKillStats = new int[5];
        splatfestStats.teamDeathStats = new int[5];
        splatfestStats.teamSpecialStats = new int[5];

        splatfestStats.playerInkAverage = 0;
        splatfestStats.playerKillAverage = 0;
        splatfestStats.playerDeathAverage = 0;
        splatfestStats.playerSpecialAverage = 0;
        splatfestStats.wins = 0;
        splatfestStats.losses = 0;
        splatfestStats.sameTeam = 0;

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

            splatfestStats.disconnects = (high - low) - battles.size();
            splatfestStats.timePlayed = (battles.size()) * 180000;

            Player player;
            Battle battle;
            for (int i = 0; i < battles.size(); i++) {
                battle = battles.get(i);

                playerInk.add(battle.user.points);
                playerKill.add(battle.user.kills);
                playerDeath.add(battle.user.deaths);
                playerSpecial.add(battle.user.special);
                if(battle.myTheme.color.color.equals(battle.otherTheme.color.color)) {
                    splatfestStats.sameTeam++;
                }else{
                    if (battle.result.name.equals("VICTORY")) {
                        splatfestStats.wins++;
                    } else {
                        splatfestStats.losses++;
                    }
                }

                splatfestStats.playerInkAverage += battle.user.points;
                splatfestStats.playerKillAverage += battle.user.kills;
                splatfestStats.playerDeathAverage += battle.user.deaths;
                splatfestStats.playerSpecialAverage += battle.user.special;

                for (int j = 0; j < battle.myTeam.size(); j++) {
                    player = battle.myTeam.get(j);

                    teamInk.add(player.points);
                    teamKill.add(player.kills);
                    teamDeath.add(player.deaths);
                    teamSpecial.add(player.special);
                }

            }
            battle = battles.get(battles.size()-1);
            splatfestStats.grade = battle.user.user.grade.name;
            splatfestStats.power = battle.fesPower;

            if (battles.size() > 5) {
                splatfestStats.playerInkStats = calcStats(sort(playerInk));
                splatfestStats.playerKillStats = calcStats(sort(playerKill));
                splatfestStats.playerDeathStats = calcStats(sort(playerDeath));
                splatfestStats.playerSpecialStats = calcStats(sort(playerSpecial));

                splatfestStats.playerInkAverage /= battles.size();
                splatfestStats.playerKillAverage /= battles.size();
                splatfestStats.playerDeathAverage /= battles.size();
                splatfestStats.playerSpecialAverage /= battles.size();

                splatfestStats.teamInkStats = calcStats(sort(teamInk));
                splatfestStats.teamKillStats = calcStats(sort(teamKill));
                splatfestStats.teamDeathStats = calcStats(sort(teamDeath));
                splatfestStats.teamSpecialStats = calcStats(sort(teamSpecial));
            }
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

    public SplatfestStats getSplatfestStats(){
        return splatfestStats;
    }

    public ArrayList<Battle> getBattles(){
        return battles;
    }

}
