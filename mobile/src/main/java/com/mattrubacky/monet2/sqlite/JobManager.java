package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.api.splatnet.Splatnet;
import com.mattrubacky.monet2.deserialized.splatoon.BossCount;
import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.deserialized.splatoon.GrizzCoBossKills;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.deserialized.splatoon.Special;
import com.mattrubacky.monet2.deserialized.splatoon.Wave;
import com.mattrubacky.monet2.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.deserialized.splatoon.Worker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/19/2018.
 */

public class JobManager {
    Context context;
    ArrayList<CoopResult> toInsert;
    ArrayList<Integer> toSelect;
    WorkerManager workerManager;
    WaveManager waveManager;

    public JobManager(Context context){
        this.context = context;
        toInsert = new ArrayList<>(); //using a hashmap to prevent duplicate entries
        toSelect = new ArrayList<>();
        workerManager = new WorkerManager(context);
        waveManager = new WaveManager(context);
    }

    //Add a sub to be inserted
    public void addToInsert(CoopResult job){
        toInsert.add(job);
        workerManager.addToInsert(job.myResult,job.id,0);
        for(Worker worker:job.otherResults){
            workerManager.addToInsert(worker,job.id,1);
        }
        for(int i=0;i<job.waves.size();i++){
            waveManager.addToInsert(job.waves.get(i),job.id,i);
        }
    }

    //Add a sub to be selected
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    //Method to execute the insert
    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;
            workerManager.insert();
            waveManager.insert();
            String whereClause = SplatnetContract.Job._ID + " = ?";
            String[] args;
            Cursor cursor = null;
            for (CoopResult job:toInsert) {
                values = new ContentValues();
                args = new String[]{String.valueOf(job.id)};
                cursor = database.query(SplatnetContract.Job.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values = new ContentValues();

                    values.put(SplatnetContract.Job._ID, job.id);
                    values.put(SplatnetContract.Job.COLUMN_START_TIME, job.startTime);
                    values.put(SplatnetContract.Job.COLUMN_PLAY_TIME, job.playTime);
                    values.put(SplatnetContract.Job.COLUMN_PAY_GRADE, job.jobRate);
                    values.put(SplatnetContract.Job.COLUMN_DANGER_RATE, job.dangerRate);
                    values.put(SplatnetContract.Job.COLUMN_KUMA_POINT, job.money);
                    values.put(SplatnetContract.Job.COLUMN_GRADE_POINT, job.gradePoint);
                    values.put(SplatnetContract.Job.COLUMN_GRADE_POINT_DELTA, job.gradePointDelta);
                    values.put(SplatnetContract.Job.COLUMN_JOB_SCORE, job.jobScore);
                    values.put(SplatnetContract.Job.COLUMN_GRADE, job.grade.name);
                    values.put(SplatnetContract.Job.COLUMN_IS_CLEAR, job.jobResult.isClear);
                    values.put(SplatnetContract.Job.COLUMN_FAILURE_REASON, job.jobResult.failureReason);
                    values.put(SplatnetContract.Job.COLUMN_FAILURE_WAVE, job.jobResult.failureWave);

                    values.put(SplatnetContract.Job.COLUMN_GOLDIE, job.bossCount.goldie.count);
                    values.put(SplatnetContract.Job.COLUMN_STEELHEAD, job.bossCount.steelhead.count);
                    values.put(SplatnetContract.Job.COLUMN_FLYFISH, job.bossCount.flyfish.count);
                    values.put(SplatnetContract.Job.COLUMN_SCRAPPER, job.bossCount.scrapper.count);
                    values.put(SplatnetContract.Job.COLUMN_STEEL_EEL, job.bossCount.steelEel.count);
                    values.put(SplatnetContract.Job.COLUMN_STINGER, job.bossCount.stinger.count);
                    values.put(SplatnetContract.Job.COLUMN_MAWS, job.bossCount.maws.count);
                    values.put(SplatnetContract.Job.COLUMN_GRILLER, job.bossCount.griller.count);
                    values.put(SplatnetContract.Job.COLUMN_DRIZZLER, job.bossCount.drizzler.count);

                    database.insert(SplatnetContract.Job.TABLE_NAME, null, values);
                }
            }
            if(cursor!=null){
                cursor.close();
            }
            database.close();
            toInsert = new ArrayList<>();
        }
    }

    //Method to execute the select
    public HashMap<Long,ArrayList<CoopResult>> select(){
        HashMap<Long,ArrayList<CoopResult>> selected = new HashMap<>();
        if(toSelect.size()>0) {

            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Coworker.COLUMN_JOB + " = ?");

            //build the select statement
            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Coworker.COLUMN_JOB + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Coworker.TABLE_NAME, null, whereClause, args, null, null, null);

            CoopResult job;
            HashMap<Integer,ArrayList<Worker>> workerHashmap = workerManager.select();
            HashMap<Integer,ArrayList<Wave>> waveHashmap = waveManager.select();
            if (cursor.moveToFirst()) {
                do {
                    job = new CoopResult();
                    //Boss Kills
                    job.bossCount = new BossCount();
                    job.bossCount.goldie = new GrizzCoBossKills();
                    job.bossCount.goldie.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDIE));

                    job.bossCount.steelhead = new GrizzCoBossKills();
                    job.bossCount.steelhead.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEELHEAD));

                    job.bossCount.flyfish = new GrizzCoBossKills();
                    job.bossCount.flyfish.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_FLYFISH));

                    job.bossCount.scrapper = new GrizzCoBossKills();
                    job.bossCount.scrapper.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SCRAPPER));

                    job.bossCount.steelEel = new GrizzCoBossKills();
                    job.bossCount.steelEel.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEEL_EEL));

                    job.bossCount.stinger = new GrizzCoBossKills();
                    job.bossCount.stinger.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STINGER));

                    job.bossCount.maws = new GrizzCoBossKills();
                    job.bossCount.maws.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_MAWS));

                    job.bossCount.griller = new GrizzCoBossKills();
                    job.bossCount.griller.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GRILLER));

                    job.bossCount.drizzler = new GrizzCoBossKills();
                    job.bossCount.drizzler.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DRIZZLER));

                    job.otherResults = new ArrayList<>();

                    for(Worker worker:workerHashmap.get(job.id)){
                        if(worker.type==0){
                            job.myResult = worker;
                        }else{
                            job.otherResults.add(worker);
                        }
                    }

                    Wave hold;
                    ArrayList<Wave> waves = waveHashmap.get(job.id);
                    job.waves = Wave.sort(waves);

                    ArrayList<CoopResult> shiftJobs;
                    if(selected.containsKey(job.startTime)){
                        shiftJobs = selected.get(job.startTime);
                    }else{
                        shiftJobs = new ArrayList<>();
                    }
                    shiftJobs.add(job);
                    selected.put(job.startTime,shiftJobs);

                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            toSelect = new ArrayList<>();
        }
        return selected;
    }

    public HashMap<Long,ArrayList<CoopResult>> selectAll(){

        HashMap<Long,ArrayList<CoopResult>> selected = new HashMap<>();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();
        Cursor cursor = database.query(SplatnetContract.Coworker.TABLE_NAME, null, null, null, null, null, null);

        CoopResult job;
        HashMap<Integer,ArrayList<Worker>> workerHashmap = workerManager.select();
        HashMap<Integer,ArrayList<Wave>> waveHashmap = waveManager.select();
        if (cursor.moveToFirst()) {
            do {
                job = new CoopResult();
                //Boss Kills
                job.bossCount = new BossCount();
                job.bossCount.goldie = new GrizzCoBossKills();
                job.bossCount.goldie.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDIE));

                job.bossCount.steelhead = new GrizzCoBossKills();
                job.bossCount.steelhead.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEELHEAD));

                job.bossCount.flyfish = new GrizzCoBossKills();
                job.bossCount.flyfish.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_FLYFISH));

                job.bossCount.scrapper = new GrizzCoBossKills();
                job.bossCount.scrapper.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SCRAPPER));

                job.bossCount.steelEel = new GrizzCoBossKills();
                job.bossCount.steelEel.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEEL_EEL));

                job.bossCount.stinger = new GrizzCoBossKills();
                job.bossCount.stinger.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STINGER));

                job.bossCount.maws = new GrizzCoBossKills();
                job.bossCount.maws.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_MAWS));

                job.bossCount.griller = new GrizzCoBossKills();
                job.bossCount.griller.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GRILLER));

                job.bossCount.drizzler = new GrizzCoBossKills();
                job.bossCount.drizzler.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DRIZZLER));

                job.otherResults = new ArrayList<>();

                for(Worker worker:workerHashmap.get(job.id)){
                    if(worker.type==0){
                        job.myResult = worker;
                    }else{
                        job.otherResults.add(worker);
                    }
                }

                Wave hold;
                ArrayList<Wave> waves = waveHashmap.get(job.id);
                job.waves = Wave.sort(waves);

                ArrayList<CoopResult> shiftJobs;
                if(selected.containsKey(job.startTime)){
                    shiftJobs = selected.get(job.startTime);
                }else{
                    shiftJobs = new ArrayList<>();
                }
                shiftJobs.add(job);
                selected.put(job.startTime,shiftJobs);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        toSelect = new ArrayList<>();
        return selected;
    }
}
