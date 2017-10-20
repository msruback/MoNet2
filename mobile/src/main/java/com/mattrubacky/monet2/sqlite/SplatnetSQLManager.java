package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.sqlite.table_manager.BattleManager;
import com.mattrubacky.monet2.sqlite.table_manager.SplatfestManager;
import com.mattrubacky.monet2.sqlite.table_manager.StageManager;

import java.util.ArrayList;

/**
 * Created by mattr on 9/22/2017.
 */

public class SplatnetSQLManager {

    Context context;
    BattleManager battleManager;

    public SplatnetSQLManager(Context context){
        this.context = context;
    }

    public boolean existsIn(String tableName,String column, int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        String select = "SELECT * FROM "+ tableName + " WHERE "+ column +" = ?";
        String[] ids = {String.valueOf(id)};
        Cursor cursor = database.rawQuery(select, ids);
        if(cursor.getCount()==0){
            cursor.close();
            database.close();
            return false;
        }else{
            cursor.close();
            database.close();
            return true;
        }
    }


    public void insertSkill(Skill skill) {
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Skill._ID,skill.id);
        values.put(SplatnetContract.Skill.COLUMN_NAME,skill.name);
        values.put(SplatnetContract.Skill.COLUMN_URL,skill.url);

        if(skill.id>13){
            values.put(SplatnetContract.Skill.COLUMN_CHUNKABLE,false);
        }else{
            values.put(SplatnetContract.Skill.COLUMN_CHUNKABLE,true);
        }

        database.insert(SplatnetContract.Skill.TABLE_NAME, null, values);
        database.close();

    }

    public Skill selectSkill(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();
        if(id==-1){
            return null;
        }

        String query = "SELECT * FROM "+ SplatnetContract.Skill.TABLE_NAME+" WHERE "+ SplatnetContract.Skill._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Skill skill = new Skill();

        if(cursor.moveToFirst()){
            skill.id = id;
            skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
            skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
        }
        cursor.close();
        database.close();
        return skill;
    }

    public ArrayList<Skill> getSkills(){
        ArrayList<Skill> skills = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Skill.TABLE_NAME+"";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToLast()) {
            do {
                Skill skill = new Skill();

                skill.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Skill._ID));
                skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
                skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
                skills.add(skill);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        database.close();
        return skills;
    }


    public void insertSub(Sub sub){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Sub._ID,sub.id);
        values.put(SplatnetContract.Sub.COLUMN_NAME,sub.name);
        values.put(SplatnetContract.Sub.COLUMN_URL,sub.url);

        database.insert(SplatnetContract.Sub.TABLE_NAME, null, values);
        database.close();

    }

    public Sub selectSub(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Sub.TABLE_NAME+" WHERE "+ SplatnetContract.Sub._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Sub sub = new Sub();

        if(cursor.moveToFirst()){
            sub.id = id;
            sub.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Sub.COLUMN_NAME));
            sub.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Sub.COLUMN_URL));
        }
        cursor.close();
        database.close();
        return sub;
    }

    public void insertSpecial(Special special){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Special._ID,special.id);
        values.put(SplatnetContract.Special.COLUMN_NAME,special.name);
        values.put(SplatnetContract.Special.COLUMN_URL,special.url);

        database.insert(SplatnetContract.Special.TABLE_NAME, null, values);
        database.close();

    }

    public Special selectSpecial(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Special.TABLE_NAME+" WHERE "+ SplatnetContract.Special._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Special special = new Special();

        if(cursor.moveToFirst()){
            special.id = id;
            special.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Special.COLUMN_NAME));
            special.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Special.COLUMN_URL));
        }
        cursor.close();
        database.close();
        return special;
    }

    public void insertStages(ArrayList<Stage> stages){
        StageManager stageManager = new StageManager(context);
    }

    public Stage selectStage(int id){
        StageManager stageManager = new StageManager(context);
        return stageManager.select(id);
    }

    public ArrayList<Stage> getStages(){
        StageManager stageManager = new StageManager(context);
        return stageManager.selectAll();
    }

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

    //Battles


    //Players

    public void insertPlayer(Player player, String mode, int id, int type){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Player.COLUMN_MODE,mode);
        values.put(SplatnetContract.Player.COLUMN_BATTLE,id);
        values.put(SplatnetContract.Player.COLUMN_TYPE,type);
        values.put(SplatnetContract.Player.COLUMN_ID,player.user.id);
        values.put(SplatnetContract.Player.COLUMN_NAME,player.user.name);
        values.put(SplatnetContract.Player.COLUMN_LEVEL,player.user.rank);
        values.put(SplatnetContract.Player.COLUMN_POINT,player.points);
        values.put(SplatnetContract.Player.COLUMN_KILL,player.kills);
        values.put(SplatnetContract.Player.COLUMN_ASSIST,player.assists);
        values.put(SplatnetContract.Player.COLUMN_DEATH,player.deaths);
        values.put(SplatnetContract.Player.COLUMN_SPECIAL,player.special);

        switch (mode){
            case "gachi":
                values.put(SplatnetContract.Player.COLUMN_RANK,player.user.udamae.rank);
                values.put(SplatnetContract.Player.COLUMN_S_NUM,player.user.udamae.sPlus);
                break;
            case "fes":
                values.put(SplatnetContract.Player.COLUMN_FES_GRADE,player.user.grade.name);
        }

        if(!existsIn(SplatnetContract.Weapon.TABLE_NAME, SplatnetContract.Weapon._ID,player.user.weapon.id)){
            insertWeapon(player.user.weapon);
        }
        values.put(SplatnetContract.Player.COLUMN_WEAPON,player.user.weapon.id);

        if(!existsIn(SplatnetContract.Gear.TABLE_NAME, SplatnetContract.Gear._ID, player.user.head.id)){
            insertGear(player.user.head);
        }
        values.put(SplatnetContract.Player.COLUMN_HEAD,player.user.head.id);

        if(!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID,player.user.headSkills.main.id)){
            insertSkill(player.user.headSkills.main);
        }
        values.put(SplatnetContract.Player.COLUMN_HEAD_MAIN,player.user.headSkills.main.id);

        //Insert sub skills
        if(player.user.headSkills.subs.get(0)!=null) {
            if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.headSkills.subs.get(0).id)){
                insertSkill(player.user.headSkills.subs.get(0));
            }
            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_1,player.user.headSkills.subs.get(0).id);
            if(player.user.headSkills.subs.get(1)!=null) {
                if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.headSkills.subs.get(1).id)){
                    insertSkill(player.user.headSkills.subs.get(1));
                }
                values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2,player.user.headSkills.subs.get(1).id);
                if(player.user.headSkills.subs.get(2)!=null) {
                    if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.headSkills.subs.get(2).id)){
                        insertSkill(player.user.headSkills.subs.get(2));
                    }
                    values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3,player.user.headSkills.subs.get(2).id);
                }else{
                    values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3,-1);
                }
            }else{

                values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2,-1);
                values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3,-1);
            }
        }else{
            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_1,-1);
            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2,-1);
            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3,-1);
        }

        if(!existsIn(SplatnetContract.Gear.TABLE_NAME, SplatnetContract.Gear._ID, player.user.clothes.id)){
            insertGear(player.user.clothes);
        }
        values.put(SplatnetContract.Player.COLUMN_CLOTHES,player.user.clothes.id);

        if(!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID,player.user.clothesSkills.main.id)){
            insertSkill(player.user.clothesSkills.main);
        }
        values.put(SplatnetContract.Player.COLUMN_CLOTHES_MAIN,player.user.clothesSkills.main.id);

        //Insert sub skills
        if(player.user.clothesSkills.subs.get(0)!=null) {
            if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.clothesSkills.subs.get(0).id)){
                insertSkill(player.user.clothesSkills.subs.get(0));
            }
            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1,player.user.clothesSkills.subs.get(0).id);
            if(player.user.clothesSkills.subs.get(1)!=null) {
                if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.clothesSkills.subs.get(1).id)){
                    insertSkill(player.user.clothesSkills.subs.get(1));
                }
                values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2,player.user.clothesSkills.subs.get(1).id);
                if(player.user.clothesSkills.subs.get(2)!=null) {
                    if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.clothesSkills.subs.get(2).id)){
                        insertSkill(player.user.clothesSkills.subs.get(2));
                    }
                    values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3,player.user.clothesSkills.subs.get(2).id);
                }else{
                    values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3,-1);
                }
            }else{

                values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2,-1);
                values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3,-1);
            }
        }else{
            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1,-1);
            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2,-1);
            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3,-1);
        }

        if(!existsIn(SplatnetContract.Gear.TABLE_NAME, SplatnetContract.Gear._ID, player.user.shoes.id)){
            insertGear(player.user.shoes);
        }
        values.put(SplatnetContract.Player.COLUMN_SHOES,player.user.shoes.id);

        if(!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID,player.user.shoeSkills.main.id)){
            insertSkill(player.user.shoeSkills.main);
        }
        values.put(SplatnetContract.Player.COLUMN_SHOES_MAIN,player.user.shoeSkills.main.id);

        //Insert sub skills
        if(player.user.shoeSkills.subs.get(0)!=null) {
            if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.shoeSkills.subs.get(0).id)){
                insertSkill(player.user.shoeSkills.subs.get(0));
            }
            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_1,player.user.shoeSkills.subs.get(0).id);
            if(player.user.shoeSkills.subs.get(1)!=null) {
                if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.shoeSkills.subs.get(1).id)){
                    insertSkill(player.user.shoeSkills.subs.get(1));
                }
                values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2,player.user.shoeSkills.subs.get(1).id);
                if(player.user.shoeSkills.subs.get(2)!=null) {
                    if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, player.user.shoeSkills.subs.get(2).id)){
                        insertSkill(player.user.shoeSkills.subs.get(2));
                    }
                    values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3,player.user.shoeSkills.subs.get(2).id);
                }else{
                    values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3,-1);
                }
            }else{

                values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2,-1);
                values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3,-1);
            }
        }else{
            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_1,-1);
            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2,-1);
            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3,-1);
        }


        database.insert(SplatnetContract.Player.TABLE_NAME, null, values);
        database.close();
    }
    public ArrayList<Player> selectPlayer(int BattleID,int type){
        ArrayList<Player> players = new ArrayList<Player>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Player.TABLE_NAME+" WHERE "+ SplatnetContract.Player.COLUMN_BATTLE+" = "+BattleID+ " AND " + SplatnetContract.Player.COLUMN_TYPE +" = "+type;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                Player player = new Player();

                player.points = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_POINT));
                player.kills = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_KILL));
                player.assists = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ASSIST));
                player.deaths = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_DEATH));
                player.special = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SPECIAL));

                User user = new User();
                user.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_NAME));
                user.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ID));
                user.rank = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_LEVEL));
                Rank rank = new Rank();
                rank.rank = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_RANK));
                rank.sPlus = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_S_NUM));
                user.udamae = rank;
                SplatfestGrade splatfestGrade = new SplatfestGrade();
                splatfestGrade.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_FES_GRADE));
                user.grade = splatfestGrade;

                user.head = selectGear(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD)));

                GearSkills headSkills = new GearSkills();
                headSkills.main = selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_MAIN)));

                ArrayList<Skill> headSub = new ArrayList<>();
                int skill = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_3));
                headSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_1))));
                headSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_2))));
                headSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_3))));
                headSkills.subs = headSub;

                user.headSkills = headSkills;

                user.clothes = selectGear(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES)));

                GearSkills clothesSkills = new GearSkills();
                clothesSkills.main = selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_MAIN)));

                ArrayList<Skill> clothesSub = new ArrayList<>();
                clothesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1))));
                clothesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2))));
                clothesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3))));
                clothesSkills.subs = clothesSub;

                user.clothesSkills = clothesSkills;

                user.shoes = selectGear(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES)));

                GearSkills shoesSkills = new GearSkills();
                shoesSkills.main = selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_MAIN)));

                ArrayList<Skill> shoesSub = new ArrayList<>();
                shoesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_1))));
                shoesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_2))));
                shoesSub.add(selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_3))));
                shoesSkills.subs = shoesSub;

                user.shoeSkills = shoesSkills;

                user.weapon = selectWeapon(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_WEAPON)));

                player.user = user;

                players.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return players;
    }

    public void deletePlayer(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        String selection = SplatnetContract.Player.COLUMN_BATTLE+"=?";
        String[] args = {String.valueOf(id)};
        database.delete(SplatnetContract.Player.TABLE_NAME, selection, args);
        database.close();
    }

    public void insertGear(Gear gear){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Gear._ID,gear.id);
        values.put(SplatnetContract.Gear.COLUMN_NAME,gear.name);
        values.put(SplatnetContract.Gear.COLUMN_KIND,gear.kind);
        values.put(SplatnetContract.Gear.COLUMN_RARITY,gear.rarity);
        values.put(SplatnetContract.Gear.COLUMN_URL,gear.url);

        if(!existsIn(SplatnetContract.Brand.TABLE_NAME, SplatnetContract.Gear._ID,gear.brand.id)){
            insertBrand(gear.brand);
        }
        values.put(SplatnetContract.Gear.COLUMN_BRAND,gear.brand.id);

        database.insert(SplatnetContract.Gear.TABLE_NAME, null, values);
        database.close();

    }

    public Gear selectGear(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Gear.TABLE_NAME+" WHERE "+ SplatnetContract.Gear._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Gear gear = new Gear();

        if(cursor.moveToNext()){
            gear.id = id;
            gear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_NAME));
            gear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_KIND));
            gear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_RARITY));
            gear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_URL));
            gear.brand = selectBrand(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_BRAND)));
        }
        cursor.close();
        database.close();
        return gear;
    }

    public ArrayList<Gear> getGear(){
        ArrayList<Gear> gear = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Gear.TABLE_NAME+"";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToLast()) {
            do {
                Gear currentGear = new Gear();

                currentGear.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear._ID));
                currentGear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_NAME));
                currentGear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_URL));
                currentGear.brand  = selectBrand(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_BRAND)));
                currentGear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_RARITY));
                currentGear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_KIND));
                gear.add(currentGear);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        database.close();
        return gear;
    }

    public void insertBrand(Brand brand){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Brand._ID,brand.id);
        values.put(SplatnetContract.Brand.COLUMN_NAME,brand.name);
        values.put(SplatnetContract.Brand.COLUMN_URL,brand.url);

        if(brand.skill!=null) {
            if (!existsIn(SplatnetContract.Skill.TABLE_NAME, SplatnetContract.Skill._ID, brand.skill.id)) {
                insertSkill(brand.skill);
            }
            values.put(SplatnetContract.Brand.COLUMN_SKILL, brand.skill.id);
        }

        database.insert(SplatnetContract.Brand.TABLE_NAME, null, values);
        database.close();

    }

    public Brand selectBrand(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Brand.TABLE_NAME+" WHERE "+ SplatnetContract.Brand._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Brand brand = new Brand();

        if(cursor.moveToNext()){
            brand.id = id;
            brand.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_NAME));
            brand.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_URL));
            brand.skill = selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_SKILL)));
        }
        cursor.close();
        database.close();
        return brand;
    }

    public void insertWeapon(Weapon weapon){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Weapon._ID,weapon.id);
        values.put(SplatnetContract.Weapon.COLUMN_NAME,weapon.name);
        values.put(SplatnetContract.Weapon.COLUMN_URL,weapon.url);

        if(!existsIn(SplatnetContract.Sub.TABLE_NAME, SplatnetContract.Sub._ID,weapon.sub.id)){
            insertSub(weapon.sub);
        }
        values.put(SplatnetContract.Weapon.COLUMN_SUB,weapon.sub.id);

        if(!existsIn(SplatnetContract.Special.TABLE_NAME, SplatnetContract.Special._ID,weapon.special.id)){
            insertSpecial(weapon.special);
        }
        values.put(SplatnetContract.Weapon.COLUMN_SPECIAL,weapon.special.id);

        database.insert(SplatnetContract.Weapon.TABLE_NAME, null, values);
        database.close();

    }

    public Weapon selectWeapon(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String whereClause = SplatnetContract.Weapon._ID +" = ?";
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(SplatnetContract.Weapon.TABLE_NAME,null,whereClause,args,null,null,null);

        Weapon weapon = new Weapon();

        if(cursor.moveToNext()){
            weapon.id = id;
            weapon.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_NAME));
            weapon.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_URL));
            weapon.special = selectSpecial(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL)));
            weapon.sub = selectSub(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB)));
        }
        cursor.close();
        database.close();
        return weapon;
    }

    public ArrayList<Weapon> getWeapons(){
        ArrayList<Weapon> weapons = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Weapon.TABLE_NAME+"";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToLast()) {
            do {
                Weapon weapon = new Weapon();

                weapon.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon._ID));
                weapon.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_NAME));
                weapon.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_URL));
                weapon.special = selectSpecial(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL)));
                weapon.sub = selectSub(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB)));
                weapons.add(weapon);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        database.close();
        return weapons;
    }

    public void insertCloset(Gear gear,GearSkills gearSkills,Battle battle){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Closet._ID,gear.id);
        values.put(SplatnetContract.Closet.COLUMN_GEAR,gear.id);
        values.put(SplatnetContract.Closet.COLUMN_MAIN,gearSkills.main.id);
        if(gearSkills.subs.get(0)!=null){
            values.put(SplatnetContract.Closet.COLUMN_SUB_1,gearSkills.subs.get(0).id);
            if(gearSkills.subs.get(1)!=null){
                values.put(SplatnetContract.Closet.COLUMN_SUB_2,gearSkills.subs.get(1).id);
                if(gearSkills.subs.get(2)!=null){
                    values.put(SplatnetContract.Closet.COLUMN_SUB_3,gearSkills.subs.get(2).id);
                }
            }
        }

        values.put(SplatnetContract.Closet.COLUMN_LAST_USE_TIME,battle.start);

        database.insert(SplatnetContract.Closet.TABLE_NAME, null, values);
        database.close();
    }

    public void updateCloset(Gear gear,GearSkills gearSkills, Battle battle){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Closet.COLUMN_MAIN,gearSkills.main.id);
        if(gearSkills.subs.get(0)!=null){
            values.put(SplatnetContract.Closet.COLUMN_SUB_1,gearSkills.subs.get(0).id);
            if(gearSkills.subs.get(1)!=null){
                values.put(SplatnetContract.Closet.COLUMN_SUB_2,gearSkills.subs.get(1).id);
                if(gearSkills.subs.get(2)!=null){
                    values.put(SplatnetContract.Closet.COLUMN_SUB_3,gearSkills.subs.get(2).id);
                }
            }
        }

        values.put(SplatnetContract.Closet.COLUMN_LAST_USE_TIME,battle.start);

        String selection = SplatnetContract.Closet._ID + " LIKE ?";
        String[] args = {String.valueOf(gear.id)};

        database.update(SplatnetContract.Closet.TABLE_NAME, values,selection,args);
        database.close();
    }

}


