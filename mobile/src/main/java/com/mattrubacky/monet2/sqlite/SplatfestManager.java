package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.Rates;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestColors;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestContribution;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestDatabase;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestImages;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestNames;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestRates;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestSummary;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestTimes;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class SplatfestManager {
    Context context;
    ArrayList<SplatfestDatabase> toInsert;
    ArrayList<Integer> toSelect;
    TableManager<Stage> stageManager;

    public SplatfestManager(Context context){
        this.context = context;
        toInsert = new ArrayList<>();
        toSelect = new ArrayList<>();
        stageManager = new TableManager<Stage>(context,Stage.class);
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
                        values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS, splatfest.result.rates.vote.alpha);
                        values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS, splatfest.result.rates.vote.bravo);

                        values.put(SplatnetContract.Splatfest.COLUMN_VERSION, splatfest.result.version);
                        if(splatfest.result.version ==01) {
                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.rates.solo.alpha);
                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.rates.team.alpha);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.rates.solo.bravo);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.rates.team.bravo);
                        }else{
                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.rates.challenge.alpha);
                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.rates.regular.alpha);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.rates.challenge.bravo);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.rates.regular.bravo);

                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_AVERAGE, splatfest.result.alphaAverages.challenge);
                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_AVERAGE, splatfest.result.alphaAverages.regular);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_AVERAGE, splatfest.result.bravoAverages.challenge);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_AVERAGE, splatfest.result.bravoAverages.regular);
                        }

                        values.put(SplatnetContract.Splatfest.COLUMN_VOTE, splatfest.result.summary.vote);
                        values.put(SplatnetContract.Splatfest.COLUMN_SOLO, splatfest.result.summary.solo);
                        values.put(SplatnetContract.Splatfest.COLUMN_TEAM, splatfest.result.summary.team);
                        values.put(SplatnetContract.Splatfest.COLUMN_WINNER, splatfest.result.summary.total);
                    }

                    database.insert(SplatnetContract.Splatfest.TABLE_NAME, null, values);
                }else{
                    //If the splatfest is already inserted, but needs to be updated, and an update is available, then the splatfest gets updated
                    cursor.moveToFirst();
                        if(splatfest.result!=null&&splatfest.result.rates.vote!=null) {

                            values.put(SplatnetContract.Splatfest.COLUMN_RESULT_TIME,splatfest.splatfest.times.result);


                            values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS, splatfest.result.rates.vote.alpha);
                            values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS, splatfest.result.rates.vote.bravo);

                            values.put(SplatnetContract.Splatfest.COLUMN_VERSION, splatfest.result.version);
                            if(splatfest.result.version ==1) {
                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.rates.solo.alpha);
                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.rates.team.alpha);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.rates.solo.bravo);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.rates.team.bravo);
                            }else{
                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS, splatfest.result.rates.challenge.alpha);
                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS, splatfest.result.rates.regular.alpha);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS, splatfest.result.rates.challenge.bravo);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS, splatfest.result.rates.regular.bravo);

                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_AVERAGE, splatfest.result.alphaAverages.challenge);
                                values.put(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_AVERAGE, splatfest.result.alphaAverages.regular);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_AVERAGE, splatfest.result.bravoAverages.challenge);
                                values.put(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_AVERAGE, splatfest.result.bravoAverages.regular);
                            }

                            values.put(SplatnetContract.Splatfest.COLUMN_VOTE, splatfest.result.summary.vote);
                            values.put(SplatnetContract.Splatfest.COLUMN_SOLO, splatfest.result.summary.solo);
                            values.put(SplatnetContract.Splatfest.COLUMN_TEAM, splatfest.result.summary.team);
                            values.put(SplatnetContract.Splatfest.COLUMN_WINNER, splatfest.result.summary.total);

                            database.update(SplatnetContract.Splatfest.TABLE_NAME, values, whereClause, args);
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

        String[] args = new String[1];
        args[0] = String.valueOf(id);

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

            try {
                splatfestDatabase.splatfest.stage = stageManager.select(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_STAGE)));

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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

                splatfestDatabase.result.rates = new SplatfestRates();

                splatfestDatabase.result.rates.vote = new Rates();
                splatfestDatabase.result.rates.solo = new Rates();
                splatfestDatabase.result.rates.team = new Rates();
                splatfestDatabase.result.rates.regular = new Rates();
                splatfestDatabase.result.rates.challenge = new Rates();

                splatfestDatabase.result.alphaAverages = new SplatfestContribution();
                splatfestDatabase.result.bravoAverages = new SplatfestContribution();

                splatfestDatabase.result.version = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VERSION));

                splatfestDatabase.result.rates.vote.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS));
                splatfestDatabase.result.rates.vote.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS));

                if(splatfestDatabase.result.version ==1){
                    splatfestDatabase.result.rates.solo.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                    splatfestDatabase.result.rates.team.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                    splatfestDatabase.result.rates.solo.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                    splatfestDatabase.result.rates.team.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));
                }else{
                    splatfestDatabase.result.rates.challenge.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                    splatfestDatabase.result.rates.challenge.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                    splatfestDatabase.result.rates.regular.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                    splatfestDatabase.result.rates.regular.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));

                    splatfestDatabase.result.alphaAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_AVERAGE));
                    splatfestDatabase.result.alphaAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_AVERAGE));
                    splatfestDatabase.result.bravoAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_AVERAGE));
                    splatfestDatabase.result.bravoAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_AVERAGE));
                }

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

                    splatfestDatabase.result.rates.vote = new Rates();
                    splatfestDatabase.result.rates.solo = new Rates();
                    splatfestDatabase.result.rates.team = new Rates();
                    splatfestDatabase.result.rates.regular = new Rates();
                    splatfestDatabase.result.rates.challenge = new Rates();

                    splatfestDatabase.result.alphaAverages = new SplatfestContribution();
                    splatfestDatabase.result.bravoAverages = new SplatfestContribution();

                    splatfestDatabase.result.version = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VERSION));

                    splatfestDatabase.result.rates.vote.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS));
                    splatfestDatabase.result.rates.vote.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS));

                    if(splatfestDatabase.result.version ==1){
                        splatfestDatabase.result.rates.solo.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                        splatfestDatabase.result.rates.team.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                        splatfestDatabase.result.rates.solo.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                        splatfestDatabase.result.rates.team.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));
                    }else{
                        splatfestDatabase.result.rates.challenge.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                        splatfestDatabase.result.rates.challenge.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                        splatfestDatabase.result.rates.regular.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                        splatfestDatabase.result.rates.regular.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));

                        splatfestDatabase.result.alphaAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_AVERAGE));
                        splatfestDatabase.result.alphaAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_AVERAGE));
                        splatfestDatabase.result.bravoAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_AVERAGE));
                        splatfestDatabase.result.bravoAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_AVERAGE));
                    }

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

    public ArrayList<SplatfestDatabase> selectAll(){
        ArrayList<SplatfestDatabase> selected = new ArrayList<>();


        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Splatfest.TABLE_NAME,null,null,null,null,null,null);

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

                    splatfestDatabase.result.rates = new SplatfestRates();

                    splatfestDatabase.result.rates.vote = new Rates();
                    splatfestDatabase.result.rates.solo = new Rates();
                    splatfestDatabase.result.rates.team = new Rates();
                    splatfestDatabase.result.rates.regular = new Rates();
                    splatfestDatabase.result.rates.challenge = new Rates();

                    splatfestDatabase.result.alphaAverages = new SplatfestContribution();
                    splatfestDatabase.result.bravoAverages = new SplatfestContribution();

                    splatfestDatabase.result.version = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_VERSION));

                    splatfestDatabase.result.rates.vote.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_PLAYERS));
                    splatfestDatabase.result.rates.vote.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_PLAYERS));

                    if(splatfestDatabase.result.version ==1){
                        splatfestDatabase.result.rates.solo.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                        splatfestDatabase.result.rates.team.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                        splatfestDatabase.result.rates.solo.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                        splatfestDatabase.result.rates.team.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));
                    }else{
                        splatfestDatabase.result.rates.challenge.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_WINS));
                        splatfestDatabase.result.rates.challenge.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_WINS));
                        splatfestDatabase.result.rates.regular.alpha = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_WINS));
                        splatfestDatabase.result.rates.regular.bravo = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_WINS));

                        splatfestDatabase.result.alphaAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_SOLO_AVERAGE));
                        splatfestDatabase.result.alphaAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_ALPHA_TEAM_AVERAGE));
                        splatfestDatabase.result.bravoAverages.challenge = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_SOLO_AVERAGE));
                        splatfestDatabase.result.bravoAverages.regular = cursor.getFloat(cursor.getColumnIndex(SplatnetContract.Splatfest.COLUMN_BRAVO_TEAM_AVERAGE));
                    }

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
            selected.add(splatfestDatabase);
        }
        return selected;
    }

}
