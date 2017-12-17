package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class BattleManager {
    Context context;
    StageManager stageManager;
    PlayerManager playerManager;
    ClosetManager closetManager;
    SplatfestManager splatfestManager;
    ArrayList<Battle> toInsert;
    ArrayList<Integer> toSelect;

    public BattleManager(Context context){
        this.context = context;

        //Get tableManagers that this table relies on
        playerManager = new PlayerManager(context);
        stageManager = new StageManager(context);

        //Get tableMangers that this table has data for
        closetManager = new ClosetManager(context);
        splatfestManager = new SplatfestManager(context);

        toInsert = new ArrayList<>();
        toSelect = new ArrayList<>();
    }

    public boolean exists(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String sql = "SELECT COUNT(*) FROM " + SplatnetContract.Battle.TABLE_NAME +" WHERE "+ SplatnetContract.Battle._ID+" = "+id;

        if(0==database.compileStatement(sql).simpleQueryForLong()){
            database.close();
            return false;
        }else{
            database.close();
            return true;
        }
    }

    //Add to the list of items to insert
    public void addToInsert(Battle battle){


        stageManager.addToInsert(battle.stage);

        //Need to avoid duplicates, might be a place for further optimization in the future
        if(!exists(battle.id)) {
            toInsert.add(battle);

            closetManager.addToInsert(battle.user.user.head,battle.user.user.headSkills,battle);

            closetManager.addToInsert(battle.user.user.clothes,battle.user.user.clothesSkills,battle);

            closetManager.addToInsert(battle.user.user.shoes,battle.user.user.shoeSkills,battle);
        }
    }

    //Add to the list of items to select
    public void addToSelect(int id){
        toSelect.add(id);

        playerManager.addToSelect(id);
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

    //Call this method to finalize the insert
    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;
            Battle battle;

            String whereClause = SplatnetContract.Battle._ID + " = ?";
            String[] args;
            Cursor cursor = null;

            for (int i = 0; i < toInsert.size(); i++) {
                values = new ContentValues();
                battle = toInsert.get(i);
                args = new String[]{String.valueOf(battle.id)};
                cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values.put(SplatnetContract.Battle._ID, battle.id);
                    values.put(SplatnetContract.Battle.COLUMN_RESULT, battle.result.name);
                    values.put(SplatnetContract.Battle.COLUMN_RULE, battle.rule.key);
                    values.put(SplatnetContract.Battle.COLUMN_MODE, battle.type);
                    values.put(SplatnetContract.Battle.COLUMN_START_TIME, battle.start);
                    switch (battle.type) {
                        case "regular":
                            values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE, battle.myTeamPercent);
                            values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE, battle.otherTeamPercent);
                            break;
                        case "fes":
                            values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE, battle.myTeamPercent);
                            values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE, battle.otherTeamPercent);
                            values.put(SplatnetContract.Battle.COLUMN_FES, battle.splatfestID);
                            values.put(SplatnetContract.Battle.COLUMN_POWER, battle.fesPower);

                            values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR, battle.myTheme.color.getColor());
                            values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_KEY, battle.myTheme.key);
                            values.put(SplatnetContract.Battle.COLUMN_MY_TEAM_NAME, battle.myTheme.name);

                            values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR, battle.otherTheme.color.getColor());
                            values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY, battle.otherTheme.key);
                            values.put(SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME, battle.otherTheme.name);
                            break;
                        case "gachi":
                            values.put(SplatnetContract.Battle.COLUMN_ALLY_SCORE, battle.myTeamCount);
                            values.put(SplatnetContract.Battle.COLUMN_FOE_SCORE, battle.otherTeamCount);
                            values.put(SplatnetContract.Battle.COLUMN_POWER, battle.gachiPower);
                            values.put(SplatnetContract.Battle.COLUMN_ELAPSED_TIME, battle.time);
                            break;
                    }

                    playerManager.addToInsert(battle.user, battle.type, battle.id, 0);
                    for (int j = 0; j < battle.myTeam.size(); j++) {
                        playerManager.addToInsert(battle.myTeam.get(j), battle.type, battle.id, 1);
                    }
                    for (int j = 0; j < battle.otherTeam.size(); j++) {
                        playerManager.addToInsert(battle.otherTeam.get(j), battle.type, battle.id, 2);
                    }

                    values.put(SplatnetContract.Battle.COLUMN_STAGE, battle.stage.id);
                    database.insert(SplatnetContract.Battle.TABLE_NAME, null, values);
                }
            }

            //Insert other tables
            playerManager.insert(); //playerManager needs to insert before closetManager to prevent Closet from referencing gear not in the database and to keep things simple
            closetManager.insert();
            stageManager.insert();

            database.close();
            //Clear toInsert
            toInsert = new ArrayList<>();
        }
    }

    //Call this method to select one battle(like what is needed for BattleInfo)
    //Performance is still improved as Players are selected in one statement, rather than eight
    public Battle select(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String[] args = new String[1];
        args[0] = String.valueOf(id);

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Battle._ID+" = ?");

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,whereClause,args,null,null,null);

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

            //Only one stage is needed from the database, so further optimisation isn't possible
            battle.stage = stageManager.select(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE)));

            playerManager.addToSelect(battle.id);

            HashMap<Integer,ArrayList<PlayerDatabase>> playerHashMap = playerManager.select();

            ArrayList<PlayerDatabase> players = playerHashMap.get(battle.id);

            battle.myTeam = new ArrayList<>();
            battle.otherTeam = new ArrayList<>();

            PlayerDatabase player;

            for(int i=0;i<players.size();i++){
                player = players.get(i);
                switch (player.playerType){
                    case 0:
                        battle.user=player.player;
                        break;
                    case 1:
                        battle.myTeam.add(player.player);
                        break;
                    case 2:
                        battle.otherTeam.add(player.player);
                }
            }

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

    //Call this method to execute the select with a list
    public ArrayList<Battle> select(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Battle._ID+" = ?");

        //build the select statement
        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Battle._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,whereClause,args,null,null,null);

        HashMap<Integer,ArrayList<PlayerDatabase>> playerMap = playerManager.select();

        ArrayList<Integer> stageIDs = new ArrayList<>();
        int stageID;

        ArrayList<Battle> battles = new ArrayList<>();
        ArrayList<Battle> battleList = new ArrayList<>();
        Battle battle;
        boolean update = false;
        if(cursor.moveToFirst()) {
            do {
                battle = new Battle();
                battle.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle._ID));
                battle.type = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MODE));
                battle.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_START_TIME));

                Rule rule = new Rule();
                rule.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RULE));
                battle.rule = rule;

                TeamResult teamResult = new TeamResult();
                teamResult.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RESULT));
                battle.result = teamResult;

                //The stage ids can only be gathered while retrieving the battle info
                stageID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE));
                stageIDs.add(stageID);
                stageManager.addToSelect(stageID);

                ArrayList<PlayerDatabase> players = playerMap.get(battle.id);
                PlayerDatabase player;

                for (int i = 0; i < players.size(); i++) {
                    player = players.get(i);
                    switch (player.playerType) {
                        case 0:
                            battle.user = player.player;
                            break;
                        case 1:
                            battle.myTeam.add(player.player);
                            break;
                        case 2:
                            battle.otherTeam.add(player.player);
                    }
                }

                switch (battle.type) {
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
                battleList.add(battle);
            }while(cursor.moveToNext());
        }
        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        for(int i=0;i<stageIDs.size();i++){
            battle = battleList.get(i);
            battle.stage = stageHashMap.get(stageIDs.get(i));
            battles.add(battle);
        }
        cursor.close();
        database.close();
        toSelect = new ArrayList<>();
        return battles;
    }
    public ArrayList<Battle> selectAll(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        Cursor cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,null,null,null,null,null);

        HashMap<Integer,ArrayList<PlayerDatabase>> playerMap = playerManager.selectAll();

        ArrayList<Integer> stageIDs = new ArrayList<>();
        int stageID;

        ArrayList<Battle> battles = new ArrayList<>();
        ArrayList<Battle> battleList = new ArrayList<>();
        Battle battle;
        if(cursor.moveToFirst()) {
            do {
                battle = new Battle();
                battle.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle._ID));
                battle.type = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MODE));
                battle.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_START_TIME));

                Rule rule = new Rule();
                rule.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RULE));
                battle.rule = rule;

                TeamResult teamResult = new TeamResult();
                teamResult.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RESULT));
                battle.result = teamResult;

                //The stage ids can only be gathered while retrieving the battle info
                stageID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE));
                stageIDs.add(stageID);
                stageManager.addToSelect(stageID);

                ArrayList<PlayerDatabase> players = playerMap.get(battle.id);
                PlayerDatabase player;
                battle.myTeam = new ArrayList<>();
                battle.otherTeam = new ArrayList<>();

                for (int i = 0; i < players.size(); i++) {
                    player = players.get(i);
                    switch (player.playerType) {
                        case 0:
                            battle.user = player.player;
                            break;
                        case 1:
                            battle.myTeam.add(player.player);
                            break;
                        case 2:
                            battle.otherTeam.add(player.player);
                    }
                }

                switch (battle.type) {
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
                battleList.add(battle);
            }while(cursor.moveToNext());
        }
        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        for(int i=0;i<stageIDs.size();i++){
            battle = battleList.get(i);
            battle.stage = stageHashMap.get(stageIDs.get(i));
            battles.add(battle);
        }
        cursor.close();
        database.close();
        toSelect = new ArrayList<>();
        return battles;
    }

    public ArrayList<Battle> getStats(int id, String type){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String[] args = new String[1];
        args[0] = String.valueOf(id);

        StringBuilder builder = new StringBuilder();
        switch (type){
            case "stage":
                builder.append(SplatnetContract.Battle.COLUMN_STAGE+" = ?");
                break;
            case "splatfest":
                builder.append(SplatnetContract.Battle.COLUMN_FES+" = ?");
                break;
        }



        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,whereClause,args,null,null,null);

        ArrayList<Integer> stageIDs = new ArrayList<>();
        int stageID;

        ArrayList<Battle> battles = new ArrayList<>();
        ArrayList<Battle> battleList = new ArrayList<>();
        Battle battle;
        if(cursor.moveToFirst()) {
            do {
                battle = new Battle();
                battle.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle._ID));
                battle.type = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MODE));
                battle.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_START_TIME));

                Rule rule = new Rule();
                rule.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RULE));
                battle.rule = rule;

                TeamResult teamResult = new TeamResult();
                teamResult.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_RESULT));
                battle.result = teamResult;

                //The stage ids can only be gathered while retrieving the battle info
                stageID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_STAGE));
                stageIDs.add(stageID);
                stageManager.addToSelect(stageID);

                playerManager.addToSelect(battle.id);

                switch (battle.type) {
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
                battleList.add(battle);
            }while(cursor.moveToNext());
        }
        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        HashMap<Integer,ArrayList<PlayerDatabase>> playerMap = playerManager.select();
        for(int i=0;i<stageIDs.size();i++){
            battle = battleList.get(i);
            battle.stage = stageHashMap.get(stageIDs.get(i));

            ArrayList<PlayerDatabase> players = playerMap.get(battle.id);
            PlayerDatabase player;
            battle.myTeam = new ArrayList<>();
            battle.otherTeam = new ArrayList<>();
            for (int j = 0; j < players.size(); j++) {
                player = players.get(j);
                switch (player.playerType) {
                    case 0:
                        battle.user = player.player;
                        break;
                    case 1:
                        battle.myTeam.add(player.player);
                        break;
                    case 2:
                        battle.otherTeam.add(player.player);
                }
            }

            battles.add(battle);
        }
        cursor.close();
        database.close();
        toSelect = new ArrayList<>();
        return battles;
    }
}
