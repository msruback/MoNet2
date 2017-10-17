package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mattrubacky.monet2.deserialized.*;

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

    public void insertStage(Stage stage){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Stage._ID,stage.id);
        values.put(SplatnetContract.Stage.COLUMN_NAME,stage.name);
        values.put(SplatnetContract.Stage.COLUMN_URL,stage.url);

        database.insert(SplatnetContract.Stage.TABLE_NAME, null, values);
        database.close();

    }

    public Stage selectStage(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Stage.TABLE_NAME+" WHERE "+ SplatnetContract.Stage._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Stage stage = new Stage();

        if(cursor.moveToFirst()){
            stage.id = id;
            stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
            stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
        }
        cursor.close();
        database.close();
        return stage;
    }

    public ArrayList<Stage> getStages(){
        ArrayList<Stage> stages = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Stage.TABLE_NAME+"";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.moveToLast()) {
            do {
                Stage stage = new Stage();

                stage.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Stage._ID));
                stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
                stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
                stages.add(stage);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        database.close();
        return stages;
    }

    public void insertSplatfest(Splatfest splatfest){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest._ID,splatfest.id);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA,splatfest.names.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG,splatfest.names.alphaDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR,splatfest.colors.alpha.getColor());
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO,splatfest.names.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG,splatfest.names.bravoDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR,splatfest.colors.bravo.getColor());
        values.put(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME,splatfest.times.announce);
        values.put(SplatnetContract.Splatfest.COLUMN_START_TIME,splatfest.times.start);
        values.put(SplatnetContract.Splatfest.COLUMN_END_TIME,splatfest.times.end);

        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL,splatfest.images.panel);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA,splatfest.images.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO,splatfest.images.bravo);

        if(!existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,splatfest.stage.id)){
            insertStage(splatfest.stage);
        }
        values.put(SplatnetContract.Splatfest.COLUMN_STAGE,splatfest.stage.id);

        database.insert(SplatnetContract.Splatfest.TABLE_NAME, null, values);

        database.close();
    }

    public void insertSplatfest(Splatfest splatfest,SplatfestResult result){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest._ID,splatfest.id);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA,splatfest.names.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG,splatfest.names.alphaDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR,splatfest.colors.alpha.getColor());
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO,splatfest.names.bravo);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG,splatfest.names.bravoDesc);
        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR,splatfest.colors.bravo.getColor());
        values.put(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME,splatfest.times.announce);
        values.put(SplatnetContract.Splatfest.COLUMN_START_TIME,splatfest.times.start);
        values.put(SplatnetContract.Splatfest.COLUMN_END_TIME,splatfest.times.end);
        values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.times.result);

        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL,splatfest.images.panel);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA,splatfest.images.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO,splatfest.images.bravo);

        if(!existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,splatfest.stage.id)){
            insertStage(splatfest.stage);
        }
        values.put(SplatnetContract.Splatfest.COLUMN_STAGE,splatfest.stage.id);

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
    public Splatfest selectSplatfest(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Splatfest.TABLE_NAME+" WHERE "+ SplatnetContract.Splatfest._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Splatfest splatfest = new Splatfest();

        if(cursor.moveToFirst()){
            splatfest.id = id;

            SplatfestNames names = new SplatfestNames();
            names.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA));
            names.alphaDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG));
            names.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO));
            names.bravoDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG));
            splatfest.names = names;

            SplatfestColor color = new SplatfestColor();
            SplatfestColors colors = new SplatfestColors();
            color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR));
            colors.alpha = color;
            color = new SplatfestColor();
            color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR));
            colors.bravo = color;
            splatfest.colors = colors;

            splatfest.stage = selectStage(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_STAGE)));

            SplatfestImages images = new SplatfestImages();
            images.panel = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL));
            images.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA));
            images.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO));
            splatfest.images = images;

            SplatfestTimes times = new SplatfestTimes();
            times.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_START_TIME));
            times.end = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_END_TIME));
            times.announce = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME));
            times.result = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_RESULT_TIME));
            splatfest.times = times;
        }
        cursor.close();
        database.close();
        return splatfest;
    }
    public void updateSplatfest(Splatfest splatfest){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.times.result);

        values.put(SplatnetContract.Splatfest.COLUMN_STAGE,splatfest.stage.id);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL,splatfest.images.panel);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA,splatfest.images.alpha);
        values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO,splatfest.images.bravo);

        String selection = SplatnetContract.Splatfest._ID + " LIKE ?";
        String[] args = {String.valueOf(splatfest.id)};

        database.update(SplatnetContract.Splatfest.TABLE_NAME, values,selection,args);
        database.close();
    }
    public boolean isSplatfestUpdated(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        String select = "SELECT * FROM "+ SplatnetContract.Splatfest.TABLE_NAME + " WHERE "+ SplatnetContract.Splatfest._ID +" = ?";
        String[] ids = {String.valueOf(id)};
        Cursor cursor = database.rawQuery(select, ids);
        cursor.moveToFirst();
        if(cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VOTE))==(long)0){
            cursor.close();
            database.close();
            return false;
        }else{
            cursor.close();
            database.close();
            return true;
        }
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

                values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR,battle.myTheme.color.getColor());
                values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_KEY,battle.myTheme.key);
                values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_NAME,battle.myTheme.name);

                values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR,battle.otherTheme.color.getColor());
                values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY,battle.otherTheme.key);
                values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME,battle.otherTheme.name);
                break;
            case "gachi":
                values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE,battle.myTeamCount);
                values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE,battle.otherTeamCount);
                values.put(SplatnetContract.Battle.COLUMN_POWER,battle.gachiPower);
                values.put(SplatnetContract.Battle.COLUMN_ELAPSED_TIME,battle.time);
                break;
        }

        if(existsIn(SplatnetContract.Closet.TABLE_NAME, SplatnetContract.Closet._ID,battle.user.user.head.id)){
            updateCloset(battle.user.user.head,battle.user.user.headSkills,battle);
        }else{
            insertCloset(battle.user.user.head,battle.user.user.headSkills,battle);
        }

        if(existsIn(SplatnetContract.Closet.TABLE_NAME, SplatnetContract.Closet._ID,battle.user.user.clothes.id)){
            updateCloset(battle.user.user.clothes,battle.user.user.clothesSkills,battle);
        }else{
            insertCloset(battle.user.user.clothes,battle.user.user.clothesSkills,battle);
        }

        if(existsIn(SplatnetContract.Closet.TABLE_NAME, SplatnetContract.Closet._ID,battle.user.user.shoes.id)){
            updateCloset(battle.user.user.shoes,battle.user.user.shoeSkills,battle);
        }else{
            insertCloset(battle.user.user.shoes,battle.user.user.shoeSkills,battle);
        }


        if(!existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,battle.stage.id)){
            insertStage(battle.stage);
        }
        values.put(SplatnetContract.Battle.COLUMN_STAGE,battle.stage.id);

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

        if(cursor.moveToLast()) {
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

                battle.stage = selectStage(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE)));

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

                        TeamTheme theme = new TeamTheme();
                        SplatfestColor color = new SplatfestColor();
                        color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR));
                        theme.color = color;
                        theme.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_KEY));
                        theme.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_NAME));
                        battle.myTheme = theme;

                        theme = new TeamTheme();
                        color = new SplatfestColor();
                        color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR));
                        theme.color = color;
                        theme.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY));
                        theme.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME));
                        battle.otherTheme = theme;
                        break;
                }

                battles.add(battle);
                num--;
            } while (cursor.moveToPrevious()&&num>0);
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

            battle.stage = selectStage(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE)));

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

                    TeamTheme theme = new TeamTheme();
                    SplatfestColor color = new SplatfestColor();
                    color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR));
                    theme.color = color;
                    theme.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_KEY));
                    theme.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_NAME));
                    battle.myTheme = theme;

                    theme = new TeamTheme();
                    color = new SplatfestColor();
                    color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR));
                    theme.color = color;
                    theme.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY));
                    theme.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME));
                    battle.otherTheme = theme;
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
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }
    public void deleteBattle(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        String selection = SplatnetContract.Battle._ID+"=?";
        String[] args = {String.valueOf(id)};
        database.delete(SplatnetContract.Battle.TABLE_NAME, selection, args);
        database.close();
    }

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


class SplatnetSQLHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 3;
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
        sqLiteDatabase.execSQL(SplatnetContract.Rotation.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 1 (Level 2)
        sqLiteDatabase.execSQL(SplatnetContract.Gear.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 2 (Level 3)
        sqLiteDatabase.execSQL(SplatnetContract.Player.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Closet.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Shop.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {


        for(int i=oldVer+1;i<=newVer;i++) {
            switch (i) {

                case 3:
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS weapon_locker");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS stage_postcards");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS chunk_bag");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rotation");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shop");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_KEY + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_NAME + " TEXT");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME + " TEXT");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_STAGE + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO + " INTEGER REFERENCES stage(_id)");

                    break;
            }
        }
    }
}
