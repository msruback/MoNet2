package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 9/22/2017.
 */

public class SplatnetSQLManager {

    Context context;

    public SplatnetSQLManager(Context context){
        this.context = context;
    }

    public void insertSkills(ArrayList<Skill> skills) {
        SkillManager skillManager = new SkillManager(context);
        for(int i=0;i<skills.size();i++){
            skillManager.addToInsert(skills.get(i));
        }
        skillManager.insert();
    }

    public ArrayList<Skill> getSkills(){
        SkillManager skillManager = new SkillManager(context);
        return skillManager.selectAll();
    }

    public ArrayList<Skill> getChunkable(){
        SkillManager skillManager = new SkillManager(context);
        return skillManager.selectChunkable();
    }

    //Stage Methods

    public void insertStages(ArrayList<Stage> stages){
        StageManager stageManager = new StageManager(context);
        for(int i=0;i<stages.size();i++){
            stageManager.addToInsert(stages.get(i));
        }
        stageManager.insert();
    }

    public Stage selectStage(int id){
        StageManager stageManager = new StageManager(context);
        return stageManager.select(id);
    }

    public ArrayList<Stage> getStages(){
        StageManager stageManager = new StageManager(context);
        return stageManager.selectAll();
    }

    //Splatfest Methods

    public void insertSplatfests(ArrayList<Splatfest> splatfests){
        SplatfestManager splatfestManager = new SplatfestManager(context);
        for(int i=0;i<splatfests.size();i++){
            splatfestManager.addToInsert(splatfests.get(i));
        }
        splatfestManager.insert();
    }

    public void insertSplatfests(ArrayList<Splatfest> splatfests,ArrayList<SplatfestResult> results){
        SplatfestManager splatfestManager = new SplatfestManager(context);
        boolean done;
        Splatfest splatfest;
        SplatfestResult result;
        for(int i=0;i<splatfests.size();i++){
            done = false;
            splatfest = splatfests.get(i);
            for (int j = 0; (!done) && j < results.size(); j++) {
                result = results.get(j);
                if (splatfest.id == result.id) {
                    done = true;
                    splatfestManager.addToInsert(splatfest,result);
                }
            }
        }
        splatfestManager.insert();
    }

    public SplatfestDatabase selectSplatfest(int id){
        SplatfestManager splatfestManager = new SplatfestManager(context);
        SplatfestDatabase splatfestDatabase = splatfestManager.select(id);
        return splatfestDatabase;
    }
    public ArrayList<SplatfestDatabase> getSplatfests(){
        SplatfestManager splatfestManager = new SplatfestManager(context);

        return splatfestManager.selectAll();
    }

    //Battle Methods

    public void insertBattles(ArrayList<Battle> battles){
        BattleManager battleManager = new BattleManager(context);
        for(int i=0;i<battles.size();i++){
            battleManager.addToInsert(battles.get(i));
        }
        battleManager.insert();
    }

    public Battle selectBattle(int id){
        BattleManager battleManager = new BattleManager(context);
        return battleManager.select(id);
    }

    public ArrayList<Battle> selectBattle(ArrayList<Integer> ids){
        BattleManager battleManager = new BattleManager(context);
        for (int i=0;i<ids.size();i++){
            battleManager.addToSelect(ids.get(i));
        }
        return  battleManager.select();
    }

    public boolean hasBattle(int id){
        BattleManager battleManager = new BattleManager(context);
        return battleManager.exists(id);
    }

    public int battleCount(){
        BattleManager battleManager = new BattleManager(context);
        return battleManager.battleCount();
    }

    public ArrayList<Battle> getBattles(){
        BattleManager battleManager = new BattleManager(context);
        return  battleManager.selectAll();
    }

    public ArrayList<Battle> getBattleStats(int id,String type){
        BattleManager battleManager = new BattleManager(context);
        return battleManager.getStats(id,type);
    }
    //Players

    public void insertPlayer(Player player, String mode, int id, int type){
        PlayerManager playerManager = new PlayerManager(context);
        playerManager.addToInsert(player,mode,id,type);
        playerManager.insert();
    }

    public ArrayList<Player> getPlayerStats(int id,String type){
        PlayerManager playerManager = new PlayerManager(context);
        return playerManager.selectStats(id,type);
    }

    //Gear
    public void insertGear(ArrayList<Gear> gear){
        GearManager gearManager = new GearManager(context);
        for(int i=0;i<gear.size();i++){
            gearManager.addToInsert(gear.get(i));
        }
        gearManager.insert();
    }

    public Gear selectGear(int id,String kind){
        GearManager gearManager = new GearManager(context);
        return gearManager.select(id,kind);
    }

    public ArrayList<HashMap<Integer,Gear>> selectGear(ArrayList<Integer> ids, ArrayList<String> kinds){
        GearManager gearManager = new GearManager(context);
        for(int i=0;i<ids.size();i++){
            gearManager.addToSelect(ids.get(i),kinds.get(i));
        }
        return gearManager.select();
    }

    public ArrayList<Gear> getGear(){
        GearManager gearManager = new GearManager(context);
        return gearManager.selectAll();
    }

    public ArrayList<Weapon> getWeapons(){
        WeaponManager weaponManager = new WeaponManager(context);
        return weaponManager.selectAll();
    }

    public void insertCloset(Gear gear,GearSkills gearSkills,Battle battle){
        ClosetManager closetManager = new ClosetManager(context);
        closetManager.addToInsert(gear,gearSkills,battle);
        closetManager.insert();
    }

    public void insertCloset(ArrayList<ClosetHanger> gear){
        ClosetManager closetManager = new ClosetManager(context);
        for(int i=0;i<gear.size();i++){
            closetManager.addToInsert(gear.get(i));
        }
        closetManager.insert();
    }

    public ArrayList<ClosetHanger> getCloset(){
        ClosetManager closetManager = new ClosetManager(context);
        return closetManager.selectAll();
    }
    public ClosetHanger selectCloset(int id,String kind){
        ClosetManager closetManager = new ClosetManager(context);
        return closetManager.select(id,kind);
    }


    public void restructureCloset(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();
        ArrayList<ClosetHanger> closetHangers = getCloset();

        database.execSQL("DROP TABLE IF EXISTS closet");

        database.execSQL(SplatnetContract.Closet.CREATE_TABLE);
        ClosetManager closetManager = new ClosetManager(context);
        for(int i=0;i<closetHangers.size();i++){
            closetManager.addToInsert(closetHangers.get(i));
        }
        closetManager.insert();
    }


}


