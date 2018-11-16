package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Wave;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/23/2018.
 */

public class WaveManager {
    Context context;
    ArrayList<Wave> toInsert;
    ArrayList<Integer> toSelect;

    public WaveManager(Context context){
        this.context = context;
        toInsert = new ArrayList<>();
        toSelect = new ArrayList<>();
    }

    //Add a skill to be inserted
    public void addToInsert(Wave wave,int job,int num){
        wave.job = job;
        wave.num = num;
        toInsert.add(wave);
    }


    //Add waves to be selected
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Wave wave : toInsert) {

            values.put(SplatnetContract.Wave.COLUMN_JOB,wave.job);
            values.put(SplatnetContract.Wave.COLUMN_NUM, wave.num);
            values.put(SplatnetContract.Wave.COLUMN_QUOTA, wave.quotaNum);
            values.put(SplatnetContract.Wave.COLUMN_IKURA_NUM, wave.powerEggNum);
            values.put(SplatnetContract.Wave.COLUMN_GOLDEN_IKURA_NUM, wave.goldenEggNum);
            values.put(SplatnetContract.Wave.COLUMN_GOLDEN_IKURA_POP, wave.goldenEggPop);
            values.put(SplatnetContract.Wave.COLUMN_WATER_LEVEL, wave.waterLevel.key);
            values.put(SplatnetContract.Wave.COLUMN_EVENT_TYPE, wave.eventType.key);

            database.insert(SplatnetContract.Wave.TABLE_NAME, null, values);

        }
        toInsert = new ArrayList<>();
        database.close();
    }

    public HashMap<Integer,ArrayList<Wave>> select(){
        HashMap<Integer,ArrayList<Wave>> selected = new HashMap<>();

        if(toSelect.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Wave.COLUMN_JOB + " = ?");

            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Wave.COLUMN_JOB + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Wave.TABLE_NAME, null, whereClause, args, null, null, null);

            Wave wave;

            if (cursor.moveToFirst()) {
                do {
                    wave = new Wave();

                    wave.job = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_JOB));
                    wave.num = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_NUM));
                    wave.quotaNum = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_QUOTA));
                    wave.powerEggNum = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_IKURA_NUM));
                    wave.goldenEggNum = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_GOLDEN_IKURA_NUM));
                    wave.goldenEggPop = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_GOLDEN_IKURA_POP));

                    wave.waterLevel = new KeyName();
                    wave.waterLevel.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_WATER_LEVEL));

                    wave.eventType = new KeyName();
                    wave.eventType.key = cursor.getString(cursor.getColumnIndex(SplatnetContract.Wave.COLUMN_EVENT_TYPE));


                    ArrayList<Wave> jobWaves;
                    if(selected.containsKey(wave.job)){
                        jobWaves = selected.get(wave.job);
                    }else{
                        jobWaves = new ArrayList<>();
                    }
                    jobWaves.add(wave);
                    selected.put(wave.job, jobWaves);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        }
        return selected;
    }
}
