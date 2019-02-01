package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.Player;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestDatabase;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.helper.ClosetHanger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

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

    public void insertRewardGear(RewardGear rewardGear){
        RewardGearManager rewardGearManager = new RewardGearManager(context);
        rewardGearManager.addToInsert(rewardGear);
        rewardGearManager.insert();
    }

    public RewardGear getRewardGear(long time){
        Date date = new Date(time*1000);
        int month = date.getMonth();
        int year = date.getYear()+1900;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.set(year,month,1,0,0,0);
        RewardGearManager rewardGearManager = new RewardGearManager(context);
        return rewardGearManager.select(calendar.getTime().getTime()/1000);
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
        return splatfestManager.select(id);
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

    public ArrayList<Battle> getPlayerStats(int id,String type){
        PlayerManager playerManager = new PlayerManager(context);
        return playerManager.selectStats(id,type);
    }

    public void updateSkills(){
        SkillManager skillManager = new SkillManager(context);
        skillManager.addToSelect(12);
        skillManager.addToSelect(13);
        skillManager.addToSelect(200);
        skillManager.addToSelect(201);
        HashMap<Integer,Skill> skills = skillManager.select();
        skillManager.update(skills.get(12));
        skillManager.update(skills.get(13));
        skillManager.update(skills.get(200));
        skillManager.update(skills.get(2001));
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

    public void insertCloset(Gear gear, GearSkills gearSkills, Battle battle){
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

    public void insertShifts(ArrayList<SalmonRunDetail> details){
        ShiftManager shiftManager = new ShiftManager(context);
        for(SalmonRunDetail shift:details){
            shiftManager.addToInsert(shift);
        }
        shiftManager.insert();
    }

    public ArrayList<SalmonRunDetail> getShifts(){
        ShiftManager shiftManager = new ShiftManager(context);
        return shiftManager.selectAll();
    }

    public void insertJobs(ArrayList<CoopResult> results){
        JobManager jobManager = new JobManager(context);
        for(CoopResult job:results){
            jobManager.addToInsert(job);
        }
        jobManager.insert();
    }

    public ArrayList<CoopResult> getJobs(long start){
        JobManager jobManager = new JobManager(context);
        return jobManager.select(start);
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

    public void removeBattle(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        BattleManager battleManager = new BattleManager(context);
        ArrayList<Battle> battles = battleManager.selectAll();

        for(int i=0;i<battles.size();i++){
            if(battles.get(i).myTeamCount==0&&battles.get(i).otherTeamCount==0){
                if(battles.get(i).myTeamPercent>battles.get(i).otherTeamPercent){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SplatnetContract.Battle.COLUMN_RESULT,"victory");
                    database.update(SplatnetContract.Battle.TABLE_NAME,contentValues, SplatnetContract.Battle._ID+" = ?",new String[]{String.valueOf(battles.get(i).id)});
                }
            }else{
                if(battles.get(i).myTeamCount>battles.get(i).otherTeamCount){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SplatnetContract.Battle.COLUMN_RESULT,"victory");
                    database.update(SplatnetContract.Battle.TABLE_NAME,contentValues, SplatnetContract.Battle._ID+" = ?",new String[]{String.valueOf(battles.get(i).id)});
                }
            }
        }
    }

    public void removeBattle(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        BattleManager battleManager = new BattleManager(context);
        database.delete(SplatnetContract.Battle.TABLE_NAME,SplatnetContract.Battle._ID+" = ? ", new String[]{String.valueOf(id)});
        database.delete(SplatnetContract.Player.TABLE_NAME,SplatnetContract.Player.COLUMN_BATTLE+" = ? ",new String[]{String.valueOf(id)});
    }


}


