package com.mattrubacky.monet2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by mattr on 9/22/2017.
 */

public class SplatnetSQL {

    Context context;
    public SplatnetSQL(Context context){
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

        ImageHandler imageHandler = new ImageHandler();
        String imageName = skill.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("ability",imageName,context)){
            imageHandler.downloadImage("ability",imageName,skill.url,context);
        }
    }

    public Skill selectSkill(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Skill.TABLE_NAME+" WHERE "+ SplatnetContract.Skill._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Skill skill = new Skill();

        if(cursor.moveToFirst()){
            skill.id = id;
            skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
            skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
        }

        return skill;
    }


    public void insertSub(Sub sub){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Sub._ID,sub.id);
        values.put(SplatnetContract.Sub.COLUMN_NAME,sub.name);
        values.put(SplatnetContract.Sub.COLUMN_URL,sub.url);

        database.insert(SplatnetContract.Sub.TABLE_NAME, null, values);
        database.close();

        ImageHandler imageHandler = new ImageHandler();
        String imageName = sub.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("sub",imageName,context)){
            imageHandler.downloadImage("sub",imageName,sub.url,context);
        }
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

        ImageHandler imageHandler = new ImageHandler();
        String imageName = special.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("special",imageName,context)){
            imageHandler.downloadImage("special",imageName,special.url,context);
        }
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

        return special;
    }

    public void insertStage(Stage stage){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Stage._ID,stage.id);
        values.put(SplatnetContract.Stage.COLUMN_NAME,stage.name);
        values.put(SplatnetContract.Stage.COLUMN_URL,stage.image);

        database.insert(SplatnetContract.Stage.TABLE_NAME, null, values);
        database.close();

        ImageHandler imageHandler = new ImageHandler();
        String imageName = stage.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("stage",imageName,context)){
            imageHandler.downloadImage("stage",imageName,stage.image,context);
        }
    }

    public Stage selectStage(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Special.TABLE_NAME+" WHERE "+ SplatnetContract.Special._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Stage stage = new Stage();

        if(cursor.moveToFirst()){
            stage.id = id;
            stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
            stage.image = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
        }

        return stage;
    }

    public void insertSplatfest(Splatfest splatfest){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest._ID,splatfest.id);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA,splatfest.names.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG,splatfest.names.alphaDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR,splatfest.colors.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO,splatfest.names.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG,splatfest.names.bravoDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR,splatfest.colors.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME,splatfest.times.announce);
        values.put(SplatnetContract.Splatfest.COLUMN_START_TIME,splatfest.times.start);
        values.put(SplatnetContract.Splatfest.COLUMN_END_TIME,splatfest.times.end);

        database.insert(SplatnetContract.Splatfest.TABLE_NAME, null, values);

        database.close();
    }

    public void insertSplatfest(Splatfest splatfest,SplatfestResult result){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest._ID,splatfest.id);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA,splatfest.names.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG,splatfest.names.alphaDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR,splatfest.colors.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO,splatfest.names.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG,splatfest.names.bravoDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR,splatfest.colors.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME,splatfest.times.announce);
        values.put(SplatnetContract.Splatfest.COLUMN_START_TIME,splatfest.times.start);
        values.put(SplatnetContract.Splatfest.COLUMN_END_TIME,splatfest.times.end);
        values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.times.result);

        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS,result.participants.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS,result.teamScores.alphaSolo);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS,result.teamScores.alphaTeam);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS,result.participants.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS,result.teamScores.bravoSolo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS,result.teamScores.bravoTeam);

        values.put(SplatnetContract.Splatfest.COLUMN_VOTE,result.summary.vote);
        values.put(SplatnetContract.Splatfest.COLUMN_SOLO,result.summary.solo);
        values.put(SplatnetContract.Splatfest.COLUMN_TEAM,result.summary.team);
        values.put(SplatnetContract.Splatfest.COLUMN_WINNER,result.summary.total);

        database.insert(SplatnetContract.Splatfest.TABLE_NAME, null, values);
        database.close();
    }

    public void updateSplatfest(Splatfest splatfest,SplatfestResult result){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.times.result);

        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS,result.participants.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS,result.teamScores.alphaSolo);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS,result.teamScores.alphaTeam);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS,result.participants.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS,result.teamScores.bravoSolo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS,result.teamScores.bravoTeam);

        values.put(SplatnetContract.Splatfest.COLUMN_VOTE,result.summary.vote);
        values.put(SplatnetContract.Splatfest.COLUMN_SOLO,result.summary.solo);
        values.put(SplatnetContract.Splatfest.COLUMN_TEAM,result.summary.team);
        values.put(SplatnetContract.Splatfest.COLUMN_WINNER,result.summary.total);

        String selection = SplatnetContract.Splatfest._ID + " LIKE ?";
        String[] args = {String.valueOf(splatfest.id)};

        database.update(SplatnetContract.Splatfest.TABLE_NAME, values,selection,args);
        database.close();
    }

    /*public void insertFriend(){
        ContentValues values = new ContentValues();

        values.put();

        database.insert(SplatnetContract.Friends.TABLE_NAME, null, values)
    }*/


    //Battles

    public void insertBattle(Battle battle){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Battle._ID,battle.id);
        values.put(SplatnetContract.Battle.COLUMN_STAGE,battle.stage.name);
        values.put(SplatnetContract.Battle.COLUMN_RESULT,battle.result.name);
        values.put(SplatnetContract.Battle.COLUMN_RULE,battle.rule.name);
        values.put(SplatnetContract.Battle.COLUMN_MODE,battle.type);
        values.put(SplatnetContract.Battle.COLUMN_START_TIME,battle.start);
        switch (battle.type){
            case "regular":
                values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE,battle.myTeamPercent);
                values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE,battle.otherTeamPercent);
                break;
            case "fes":
                values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE,battle.myTeamPercent);
                values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE,battle.otherTeamPercent);
                values.put(SplatnetContract.Battle.COLUMN_FES,battle.splatfestID);
                values.put(SplatnetContract.Battle.COLUMN_POWER,battle.fesPower);
                break;
            case "gachi":
                values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE,battle.myTeamCount);
                values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE,battle.otherTeamCount);
                values.put(SplatnetContract.Battle.COLUMN_POWER,battle.gachiPower);
                values.put(SplatnetContract.Battle.COLUMN_ELAPSED_TIME,battle.time);
                break;
        }

        database.insert(SplatnetContract.Battle.TABLE_NAME, null, values);
        insertPlayer(battle.user,battle.type,battle.id,0);
        for(int i=0;i<battle.myTeam.size();i++){
            insertPlayer(battle.myTeam.get(i),battle.type,battle.id,1);
        }
        for(int i=0;i<battle.otherTeam.size();i++){
            insertPlayer(battle.otherTeam.get(i),battle.type,battle.id,2);
        }
        database.close();
    }

    public ArrayList<Battle> selectBattles(int num){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Battle.TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);

        ArrayList<Battle> battles = new ArrayList<Battle>();

        if(cursor.moveToFirst()) {
            do {
                Battle battle = new Battle();
                battle.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle._ID));
                battle.type = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MODE));
                battle.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_START_TIME));

                Rule rule = new Rule();
                rule.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RULE));
                battle.rule = rule;

                TeamResult teamResult = new TeamResult();
                teamResult.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RESULT));
                battle.result = teamResult;

                battle.user = selectPlayer(battle.id,0).get(0);
                battle.myTeam = selectPlayer(battle.id,1);
                battle.otherTeam = selectPlayer(battle.id,2);

                switch (battle.type){
                    case "regular":
                        battle.myTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                        battle.otherTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                        break;
                    case "gachi":
                        battle.gachiPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_POWER));
                        battle.myTeamCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                        battle.otherTeamCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                        battle.time = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ELAPSED_TIME));
                        break;
                    case "fes":
                        battle.fesPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_POWER));
                        battle.myTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                        battle.otherTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                        battle.splatfestID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FES));
                        break;
                }

                battles.add(battle);
                num--;
            } while (cursor.moveToNext()&&num>0);
        }
        cursor.close();
        database.close();
        return battles;
    }
    public Battle selectBattle(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Battle.TABLE_NAME+ " WHERE "+ SplatnetContract.Battle._ID+ " = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Battle battle = new Battle();
        if(cursor.moveToFirst()) {
            battle.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle._ID));
            battle.type = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MODE));
            battle.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_START_TIME));

            Rule rule = new Rule();
            rule.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RULE));
            battle.rule = rule;

            TeamResult teamResult = new TeamResult();
            teamResult.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RESULT));
            battle.result = teamResult;

            battle.user = selectPlayer(battle.id,0).get(0);
            battle.myTeam = selectPlayer(battle.id,1);
            battle.otherTeam = selectPlayer(battle.id,2);

            switch (battle.type){
                case "regular":
                    battle.myTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                    battle.otherTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                    break;
                case "gachi":
                    battle.gachiPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_POWER));
                    battle.myTeamCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                    battle.otherTeamCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                    battle.time = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ELAPSED_TIME));
                    break;
                case "fes":
                    battle.fesPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_POWER));
                    battle.myTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_ALLY_SCORE));
                    battle.otherTeamPercent = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FOE_SCORE));
                    battle.splatfestID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FES));
                    break;
            }
        }
        cursor.close();
        database.close();
        return battle;
    }
    public int battleCount(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        String query = "SELECT * FROM "+ SplatnetContract.Battle.TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);
        return cursor.getCount();
    }

    //Players

    public void insertPlayer(Player player,String mode,int id,int type){
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
                values.put(SplatnetContract.Player.COLUMN_FES_GRADE,player.grade.name);
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
                }
            }
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
                }
            }
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
                }
            }
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
                user.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player._ID));
                user.rank = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_LEVEL));
                Rank rank = new Rank();
                rank.rank = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_RANK));
                rank.sPlus = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_S_NUM));
                user.udamae = rank;

                user.head = selectGear(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD)));

                GearSkills headSkills = new GearSkills();
                headSkills.main = selectSkill(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_MAIN)));

                ArrayList<Skill> headSub = new ArrayList<>();
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

        ImageHandler imageHandler = new ImageHandler();
        String imageName = gear.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("gear",imageName,context)){
            imageHandler.downloadImage("gear",imageName,gear.url,context);
        }
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

        ImageHandler imageHandler = new ImageHandler();
        String imageName = brand.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("brand",imageName,context)){
            imageHandler.downloadImage("brand",imageName,brand.url,context);
        }
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

        return brand;
    }

    public void insertWeapon(Weapon weapon){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Weapon._ID,weapon.id);
        values.put(SplatnetContract.Weapon.COLUMN_NAME,weapon.name);

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

        ImageHandler imageHandler = new ImageHandler();
        String imageName = weapon.name.toLowerCase().replace(" ","_");;
        if(!imageHandler.imageExists("weapon",imageName,context)){
            imageHandler.downloadImage("weapon",imageName,weapon.url,context);
        }
    }

    public Weapon selectWeapon(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Weapon.TABLE_NAME+" WHERE "+ SplatnetContract.Weapon._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Weapon weapon = new Weapon();

        if(cursor.moveToNext()){
            weapon.id = id;
            weapon.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_NAME));
            weapon.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_URL));
            weapon.special = selectSpecial(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL)));
            weapon.sub = selectSub(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB)));
        }

        return weapon;
    }

}
class SplatnetSQLHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "splatnet";

    public SplatnetSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Tables without Foriegn Keys (Level 0 )
        sqLiteDatabase.execSQL(SplatnetContract.Skill.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Sub.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Special.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Stage.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Splatfest.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Friends.CREATE_TABLE);

        //Tables with Foriegn Keys to Level 0 (Level 1)
        sqLiteDatabase.execSQL(SplatnetContract.Battle.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Brand.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Weapon.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.SplatfestVotes.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.StagePostcards.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.ChunkBag.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Rotation.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 1 (Level 2)
        sqLiteDatabase.execSQL(SplatnetContract.Gear.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.WeaponLocker.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 2 (Level 3)
        sqLiteDatabase.execSQL(SplatnetContract.Player.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Closet.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Shop.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Level 3
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Player.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Closet.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Shop.TABLE_NAME);
        //Level 2
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Gear.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.WeaponLocker.TABLE_NAME);
        //Level 1
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Battle.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Brand.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Weapon.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.SplatfestVotes.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.StagePostcards.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.ChunkBag.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Rotation.TABLE_NAME);
        //Level 0
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Skill.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Sub.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Special.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Stage.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Splatfest.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Friends.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}