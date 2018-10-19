package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.deserialized.splatoon.PlayerDatabase;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestGrade;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.TeamTheme;
import com.mattrubacky.monet2.sqlite.Factory.DatabaseObjectFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class BattleManager extends TableManager<Battle>{
    TableManager<Stage> stageManager;
    PlayerManager playerManager;
    ClosetManager closetManager;
    SplatfestManager splatfestManager;

    public BattleManager(Context context){
        super(context,Battle.class);

        //Get tableManagers that this table relies on
        playerManager = new PlayerManager(context);
        stageManager = new TableManager<Stage>(context,Stage.class);

        //Get tableMangers that this table has data for
        closetManager = new ClosetManager(context);
        splatfestManager = new SplatfestManager(context);
    }

    //Add to the list of items to insert
    public void addToInsert(Battle battle){

        stageManager.addToInsert(battle.stage);
        SQLiteDatabase database = new SplatnetSQLHelper(super.context).getWritableDatabase();
        //Need to avoid duplicates, might be a place for further optimization in the future
        if(!exists(database,battle)) {
            super.addToInsert(battle);

            closetManager.addToInsert(battle.user.user.head,battle.user.user.headSkills,battle);

            closetManager.addToInsert(battle.user.user.clothes,battle.user.user.clothesSkills,battle);

            closetManager.addToInsert(battle.user.user.shoes,battle.user.user.shoeSkills,battle);
        }
        database.close();
    }

    //Add to the list of items to select
    public void addToSelect(int id){
        super.addToSelect(id);
        playerManager.addToSelect(id);
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
        //Insert other tables
        playerManager.insert(); //playerManager needs to insert before closetManager to prevent Closet from referencing gear not in the database and to keep things simple
        closetManager.insert();
        stageManager.insert();
        super.insert();
    }

    //Call this method to select one battle(like what is needed for BattleInfo)
    //Performance is still improved as Players are selected in one statement, rather than eight
    public Battle select(int id) {
        Battle battle = select(id);
        try {

            //Only one stage is needed from the database, so further optimisation isn't possible
            battle.stage = stageManager.select(battle.stage.id);

            playerManager.addToSelect(battle.id);

            HashMap<Integer, ArrayList<PlayerDatabase>> playerHashMap = playerManager.select();

            ArrayList<PlayerDatabase> players = playerHashMap.get(battle.id);

            battle.myTeam = new ArrayList<>();
            battle.otherTeam = new ArrayList<>();

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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return battle;
    }

    protected Battle buildObject(Class<Battle> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        Battle battle = super.buildObject(type,cursor);
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
                battle.myFesPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_POWER));
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
                battle.myTeamName = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_OTHER_NAME));
                battle.myConsecutiveWins = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_MY_TEAM_CONSECUTIVE_WINS));

                theme = new TeamTheme();
                color = new SplatfestColor();
                color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR));
                theme.color = color;
                theme.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY));
                theme.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME));
                battle.otherTheme = theme;
                battle.otherTeamName = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_OTHER_NAME));
                battle.otherConsecutiveWins = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_CONSECUTIVE_WINS));
                battle.otherFesPower = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_OTHER_TEAM_FES_POWER));

                battle.fesPoint = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FES_POINT));
                battle.contributionPoints = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_CONTRIBUTION_POINTS));
                battle.uniformBonus = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_UNIFORM_BONUS));

                SplatfestGrade grade = new SplatfestGrade();
                grade.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FES_GRADE));
                battle.grade = grade;

                KeyName fesMode = new KeyName();
                fesMode.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_FES_MODE));
                battle.fesMode = fesMode;

                KeyName eventType = new KeyName();
                eventType.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Battle.COLUMN_EVENT_TYPE));

                break;
        }
        return battle;
    }

    @Override
    protected ContentValues getValues(Battle toInsert,ContentValues values){
        
        return DatabaseObjectFactory.storeObject(toInsert,new ContentValues());
    }

    //Call this method to execute the select with a list
    public HashMap<Integer,Battle> select(){
        HashMap<Integer,Battle> toReturn = new HashMap<>();

        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        HashMap<Integer, ArrayList<PlayerDatabase>> playerHashMap = playerManager.select();
        HashMap<Integer,Battle> battleHashMap = select();

        for (int key : battleHashMap.keySet()) {
            Battle battle = battleHashMap.get(key);
            battle.stage = stageHashMap.get(battle.stage.id);
            ArrayList<PlayerDatabase> players = playerHashMap.get(battle.id);

            battle.myTeam = new ArrayList<>();
            battle.otherTeam = new ArrayList<>();

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
            toReturn.put(key,battle);
        }
        return toReturn;
    }

    @Override
    public ArrayList<Battle> selectAll(){
        ArrayList<Battle> toReturn = new ArrayList<>();

        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        HashMap<Integer, ArrayList<PlayerDatabase>> playerHashMap = playerManager.select();
        ArrayList<Battle> battles= selectAll();

        for (Battle battle : battles) {
            battle.stage = stageHashMap.get(battle.stage.id);
            ArrayList<PlayerDatabase> players = playerHashMap.get(battle.id);

            battle.myTeam = new ArrayList<>();
            battle.otherTeam = new ArrayList<>();

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
            toReturn.add(battle);
        }
        return toReturn;
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

        ArrayList<Battle> battles = new ArrayList<>();
        ArrayList<Battle> battleList = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    battleList.add(buildObject(Battle.class, cursor));
                } while (cursor.moveToNext());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        HashMap<Integer,ArrayList<PlayerDatabase>> playerMap = playerManager.select();
        for(Battle battle : battleList){
            battle.stage = stageHashMap.get(battle.stage.id);

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
        super.toSelect = new ArrayList<>();
        return battles;
    }

}
