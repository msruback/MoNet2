package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class SplatfestManager {
    Context context;
    ArrayList<SplatfestDatabase> toInsert;
    ArrayList<Integer> toSelect;
    StageManager stageManager;

    public SplatfestManager(Context context){
        this.context = context;
        toInsert = new ArrayList<>();
        toSelect = new ArrayList<>();
        stageManager = new StageManager(context);
    }

    public void addToInsert(Splatfest splatfest){
        SplatfestDatabase splatfestDatabase = new SplatfestDatabase();
        splatfestDatabase.splatfest = splatfest;
        stageManager.addToInsert(splatfest.stage);
        toInsert.add(splatfestDatabase);
    }

    public void addToInsert(Splatfest splatfest,SplatfestResult result){
        SplatfestDatabase splatfestDatabase = new SplatfestDatabase();
        splatfestDatabase.splatfest = splatfest;
        splatfestDatabase.result = result;
        stageManager.addToInsert(splatfest.stage);
        toInsert.add(splatfestDatabase);
    }

    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            String whereClause = SplatnetContract.Splatfest._ID + " = ?";
            String[] args;
            Cursor cursor = null;

            SplatfestDatabase splatfest = new SplatfestDatabase();
            for (int i=0;i<toInsert.size();i++){
                values = new ContentValues();

                splatfest = toInsert.get(i);
                args = new String[] {String.valueOf(splatfest.splatfest.id)};
                cursor = database.query(SplatnetContract.Splatfest.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values.put(SplatnetContract.Splatfest._ID,splatfest.splatfest.id);
                    values.put(SplatnetContract.Splatfest.COLUMN_ALPHA,splatfest.splatfest.names.alpha);
                    values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG,splatfest.splatfest.names.alphaDesc);
                    values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR,splatfest.splatfest.colors.alpha.getColor());
                    values.put(SplatnetContract.Splatfest.COLUMN_BRAVO,splatfest.splatfest.names.bravo);
                    values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG,splatfest.splatfest.names.bravoDesc);
                    values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR,splatfest.splatfest.colors.bravo.getColor());
                    values.put(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME,splatfest.splatfest.times.announce);
                    values.put(SplatnetContract.Splatfest.COLUMN_START_TIME,splatfest.splatfest.times.start);
                    values.put(SplatnetContract.Splatfest.COLUMN_END_TIME,splatfest.splatfest.times.end);
                    values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.splatfest.times.result);

                    values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL,splatfest.splatfest.images.panel);
                    values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA,splatfest.splatfest.images.alpha);
                    values.put(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO,splatfest.splatfest.images.bravo);

                    values.put(SplatnetContract.Splatfest.COLUMN_STAGE,splatfest.splatfest.stage.id);

                    if(splatfest.result!=null) {
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS, splatfest.result.participants.alpha);
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.teamScores.alphaSolo);
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.teamScores.alphaTeam);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS, splatfest.result.participants.bravo);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.teamScores.bravoSolo);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.teamScores.bravoTeam);

                        values.put(SplatnetContract.Splatfest.COLUMN_VOTE, splatfest.result.summary.vote);
                        values.put(SplatnetContract.Splatfest.COLUMN_SOLO, splatfest.result.summary.solo);
                        values.put(SplatnetContract.Splatfest.COLUMN_TEAM, splatfest.result.summary.team);
                        values.put(SplatnetContract.Splatfest.COLUMN_WINNER, splatfest.result.summary.total);
                    }

                    database.insert(SplatnetContract.Splatfest.TABLE_NAME, null, values);
                }else{
                    //If the splatfest is already inserted, but needs to be updated, and an update is available, then the splatfest gets updated
                    cursor.moveToFirst();
                    if(cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_RESULT_TIME))==0&&splatfest.result!=null){
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS, splatfest.result.participants.alpha);
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.teamScores.alphaSolo);
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.teamScores.alphaTeam);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS, splatfest.result.participants.bravo);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.teamScores.bravoSolo);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.teamScores.bravoTeam);

                        values.put(SplatnetContract.Splatfest.COLUMN_VOTE, splatfest.result.summary.vote);
                        values.put(SplatnetContract.Splatfest.COLUMN_SOLO, splatfest.result.summary.solo);
                        values.put(SplatnetContract.Splatfest.COLUMN_TEAM, splatfest.result.summary.team);
                        values.put(SplatnetContract.Splatfest.COLUMN_WINNER, splatfest.result.summary.total);

                        database.update(SplatnetContract.Splatfest.TABLE_NAME, values,whereClause,args);
                    }
                }
            }
            cursor.close();
            database.close();
            toInsert = new ArrayList<>();
        }
    }

    public SplatfestDatabase select(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Splatfest._ID+" = ?");

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Splatfest.TABLE_NAME,null,whereClause,args,null,null,null);

        SplatfestDatabase splatfestDatabase = new SplatfestDatabase();

        if(cursor.moveToFirst()){
            splatfestDatabase.splatfest = new Splatfest();

            splatfestDatabase.splatfest.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest._ID));

            SplatfestNames names = new SplatfestNames();
            names.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA));
            names.alphaDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG));
            names.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO));
            names.bravoDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG));
            splatfestDatabase.splatfest.names = names;

            SplatfestColor color = new SplatfestColor();
            SplatfestColors colors = new SplatfestColors();
            color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR));
            colors.alpha = color;
            color = new SplatfestColor();
            color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR));
            colors.bravo = color;
            splatfestDatabase.splatfest.colors = colors;

            splatfestDatabase.splatfest.stage = stageManager.select(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_STAGE)));

            SplatfestImages images = new SplatfestImages();
            images.panel = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL));
            images.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA));
            images.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO));
            splatfestDatabase.splatfest.images = images;

            SplatfestTimes times = new SplatfestTimes();
            times.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_START_TIME));
            times.end = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_END_TIME));
            times.announce = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME));
            times.result = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_RESULT_TIME));
            splatfestDatabase.splatfest.times = times;

            //Determine if result data is present in the database

            if(times.result!=0){
                splatfestDatabase.result = new SplatfestResult();

                splatfestDatabase.result.id = splatfestDatabase.splatfest.id;

                splatfestDatabase.result.teamScores = new SplatfestTeamScores();

                splatfestDatabase.result.teamScores.alphaSolo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                splatfestDatabase.result.teamScores.alphaTeam = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));

                splatfestDatabase.result.teamScores.bravoSolo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                splatfestDatabase.result.teamScores.bravoTeam = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));

                splatfestDatabase.result.participants = new SplatfestParticipants();

                splatfestDatabase.result.participants.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS));
                splatfestDatabase.result.participants.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS));

                splatfestDatabase.result.summary = new SplatfestSummary();

                splatfestDatabase.result.summary.solo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_SOLO));
                splatfestDatabase.result.summary.team = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_TEAM));
                splatfestDatabase.result.summary.vote = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VOTE));

                splatfestDatabase.result.summary.total = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_WINNER));
            }
        }
        cursor.close();
        database.close();
        return splatfestDatabase;
    }

    public HashMap<Integer,SplatfestDatabase> select(){
        HashMap<Integer,SplatfestDatabase> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Splatfest._ID+" = ?");

        //build the select statement
        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Splatfest._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Splatfest.TABLE_NAME,null,whereClause,args,null,null,null);

        ArrayList<SplatfestDatabase> splatfests = new ArrayList<>();
        SplatfestDatabase splatfestDatabase;

        ArrayList<Integer> stageIDs = new ArrayList<>();
        int stageID;

        if(cursor.moveToFirst()){
            do{
                splatfestDatabase = new SplatfestDatabase();
                splatfestDatabase.splatfest = new Splatfest();

                splatfestDatabase.splatfest.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest._ID));

                SplatfestNames names = new SplatfestNames();
                names.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA));
                names.alphaDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_LONG));
                names.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO));
                names.bravoDesc = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_LONG));
                splatfestDatabase.splatfest.names = names;

                SplatfestColor color = new SplatfestColor();
                SplatfestColors colors = new SplatfestColors();
                color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_COLOR));
                colors.alpha = color;
                color = new SplatfestColor();
                color.color = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_COLOR));
                colors.bravo = color;
                splatfestDatabase.splatfest.colors = colors;

                stageID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_STAGE));
                stageManager.addToSelect(stageID);
                stageIDs.add(stageID);

                SplatfestImages images = new SplatfestImages();
                images.panel = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL));
                images.alpha = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA));
                images.bravo = cursor.getString(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO));
                splatfestDatabase.splatfest.images = images;

                SplatfestTimes times = new SplatfestTimes();
                times.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_START_TIME));
                times.end = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_END_TIME));
                times.announce = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ANNOUNCE_TIME));
                times.result = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_RESULT_TIME));
                splatfestDatabase.splatfest.times = times;

                //Determine if result data is present in the database

                if(times.result!=0){
                    splatfestDatabase.result = new SplatfestResult();

                    splatfestDatabase.result.id = splatfestDatabase.splatfest.id;

                    splatfestDatabase.result.teamScores = new SplatfestTeamScores();

                    splatfestDatabase.result.teamScores.alphaSolo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                    splatfestDatabase.result.teamScores.alphaTeam = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));

                    splatfestDatabase.result.teamScores.bravoSolo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                    splatfestDatabase.result.teamScores.bravoTeam = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));

                    splatfestDatabase.result.participants = new SplatfestParticipants();

                    splatfestDatabase.result.participants.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS));
                    splatfestDatabase.result.participants.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS));

                    splatfestDatabase.result.summary = new SplatfestSummary();

                    splatfestDatabase.result.summary.solo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_SOLO));
                    splatfestDatabase.result.summary.team = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_TEAM));
                    splatfestDatabase.result.summary.vote = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VOTE));

                    splatfestDatabase.result.summary.total = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_WINNER));
                }

                splatfests.add(splatfestDatabase);
            }while(cursor.moveToNext());
        }
        toSelect = new ArrayList<>();
        cursor.close();
        database.close();
        //This is probably only ever going to be stage 9999(Shifty Station), but Nintendo can be tricky sometimes
        HashMap<Integer,Stage> stageHashMap = stageManager.select();
        for(int i=0;i<splatfests.size();i++){
            splatfestDatabase = splatfests.get(i);
            splatfestDatabase.splatfest.stage = stageHashMap.get(stageIDs.get(i));
            selected.put(splatfestDatabase.splatfest.id,splatfestDatabase);
        }
        return selected;
    }

}
