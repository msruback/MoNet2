package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.data.deserialized.splatoon.BossCount;
import com.mattrubacky.monet2.data.deserialized.splatoon.GrizzCoBossKills;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.Special;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.Worker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/19/2018.
 */

public class WorkerManager {
    Context context;
    ArrayList<Worker> toInsert;
    ArrayList<Integer> toSelect;
    WeaponManager weaponManager;
    SpecialManager specialManager;

    public WorkerManager(Context context){
        this.context = context;
        toInsert = new ArrayList<>(); //using a hashmap to prevent duplicate entries
        toSelect = new ArrayList<>();
        weaponManager = new WeaponManager(context);
        specialManager = new SpecialManager(context);
    }

    //Add a sub to be inserted
    public void addToInsert(Worker worker,int job,int type){
        worker.job = job;
        worker.type = type;
        toInsert.add(worker);
        for(SalmonRunWeapon weapon : worker.weapons){
            if(weapon.id>0){
                weaponManager.addToInsertSalmon(weapon.weapon);
            }
        }
        specialManager.addToInsert(worker.special);
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
            weaponManager.insert();
            specialManager.insert();

            SalmonRunDetail shift;
            for (Worker worker:toInsert) {

                values = new ContentValues();

                values.put(SplatnetContract.Coworker.COLUMN_PID,worker.id);
                values.put(SplatnetContract.Coworker.COLUMN_JOB,worker.job);
                values.put(SplatnetContract.Coworker.COLUMN_TYPE,worker.type);
                values.put(SplatnetContract.Coworker.COLUMN_NAME,worker.name);
                values.put(SplatnetContract.Coworker.COLUMN_GOLDEN_IKURA_NUM,worker.goldenEggs);
                values.put(SplatnetContract.Coworker.COLUMN_IKURA_NUM,worker.powerEggs);
                values.put(SplatnetContract.Coworker.COLUMN_DEAD_COUNT, worker.deadCount);
                values.put(SplatnetContract.Coworker.COLUMN_HELP_COUNT, worker.helpCount);

                if(worker.specialCounts.size()>0) {
                    values.put(SplatnetContract.Coworker.COLUMN_SPECIAL_1, worker.specialCounts.get(0));
                    if(worker.specialCounts.size()>1) {
                        values.put(SplatnetContract.Coworker.COLUMN_SPECIAL_2, worker.specialCounts.get(1));
                        if(worker.specialCounts.size()>2) {
                            values.put(SplatnetContract.Coworker.COLUMN_SPECIAL_3, worker.specialCounts.get(2));
                        }
                    }
                }

                values.put(SplatnetContract.Coworker.COLUMN_GOLDIE,worker.bossKillses.goldie.count);
                values.put(SplatnetContract.Coworker.COLUMN_STEELHEAD,worker.bossKillses.steelhead.count);
                values.put(SplatnetContract.Coworker.COLUMN_FLYFISH,worker.bossKillses.flyfish.count);
                values.put(SplatnetContract.Coworker.COLUMN_SCRAPPER,worker.bossKillses.scrapper.count);
                values.put(SplatnetContract.Coworker.COLUMN_STEEL_EEL,worker.bossKillses.steelEel.count);
                values.put(SplatnetContract.Coworker.COLUMN_STINGER,worker.bossKillses.stinger.count);
                values.put(SplatnetContract.Coworker.COLUMN_MAWS,worker.bossKillses.maws.count);
                values.put(SplatnetContract.Coworker.COLUMN_GRILLER,worker.bossKillses.griller.count);
                values.put(SplatnetContract.Coworker.COLUMN_DRIZZLER,worker.bossKillses.drizzler.count);

                values.put(SplatnetContract.Coworker.COLUMN_SPECIAL,worker.special.id);

                if(worker.weapons.size()>0) {
                    values.put(SplatnetContract.Coworker.COLUMN_WEAPON_1, worker.weapons.get(0).id);
                    if (worker.weapons.size() > 1) {
                        values.put(SplatnetContract.Coworker.COLUMN_WEAPON_2, worker.weapons.get(1).id);
                        if (worker.weapons.size() > 2) {
                            values.put(SplatnetContract.Coworker.COLUMN_WEAPON_3, worker.weapons.get(2).id);
                        }
                    }
                }

                database.insert(SplatnetContract.Coworker.TABLE_NAME, null, values);
            }
            database.close();
            toInsert = new ArrayList<>();
        }
    }

    //Method to execute the select
    public HashMap<Integer,ArrayList<Worker>> select(){
        HashMap<Integer,ArrayList<Worker>> selected = new HashMap<>();
        HashMap<Integer,HashMap<String,ArrayList<Integer>>> weaponList = new HashMap<>();
        HashMap<Integer,HashMap<String,Integer>> specials = new HashMap<>();
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

            Worker worker;
            ArrayList<Worker> workerDetails = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    worker = new Worker();

                    worker.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_PID));
                    worker.job = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_JOB));
                    worker.type = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_TYPE));
                    worker.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_NAME));
                    worker.goldenEggs = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDEN_IKURA_NUM));
                    worker.powerEggs = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_IKURA_NUM));
                    worker.deadCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DEAD_COUNT));
                    worker.helpCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_HELP_COUNT));

                    worker.specialCounts = new ArrayList<>();

                    worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_1)));
                    worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_2)));
                    worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_3)));

                    //Boss Kills
                    worker.bossKillses = new BossCount();
                    worker.bossKillses.goldie = new GrizzCoBossKills();
                    worker.bossKillses.goldie.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDIE));

                    worker.bossKillses.steelhead = new GrizzCoBossKills();
                    worker.bossKillses.steelhead.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEELHEAD));

                    worker.bossKillses.flyfish = new GrizzCoBossKills();
                    worker.bossKillses.flyfish.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_FLYFISH));

                    worker.bossKillses.scrapper = new GrizzCoBossKills();
                    worker.bossKillses.scrapper.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SCRAPPER));

                    worker.bossKillses.steelEel = new GrizzCoBossKills();
                    worker.bossKillses.steelEel.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEEL_EEL));

                    worker.bossKillses.stinger = new GrizzCoBossKills();
                    worker.bossKillses.stinger.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STINGER));

                    worker.bossKillses.maws = new GrizzCoBossKills();
                    worker.bossKillses.maws.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_MAWS));

                    worker.bossKillses.griller = new GrizzCoBossKills();
                    worker.bossKillses.griller.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GRILLER));

                    worker.bossKillses.drizzler = new GrizzCoBossKills();
                    worker.bossKillses.drizzler.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DRIZZLER));

                    //Special handling
                    HashMap<String,Integer> specialJobHash;
                    if(specials.containsKey(worker.job)){
                        specialJobHash = specials.get(worker.job);
                    }else{
                        specialJobHash = new HashMap<>();
                    }

                    int specialId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL));
                    specialManager.addToSelect(specialId);
                    specialJobHash.put(worker.id,specialId);
                    specials.put(worker.job,specialJobHash);


                    //Weapon handling
                    HashMap<String,ArrayList<Integer>> jobWorkerWeapons;
                    if(weaponList.containsKey(worker.job)){
                        jobWorkerWeapons = weaponList.get(worker.job);
                    }else{
                        jobWorkerWeapons = new HashMap<>();
                    }
                    ArrayList<Integer> wepids = new ArrayList<>();

                    int wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_1));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_2));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_3));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    jobWorkerWeapons.put(worker.id,wepids);
                    weaponList.put(worker.job,jobWorkerWeapons);

                    workerDetails.add(worker);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            HashMap<Integer,Weapon> weaponHashMap = weaponManager.select();
            HashMap<Integer,Special> specialHashMap = specialManager.select();

            for(Worker toMove:workerDetails){
                ArrayList<Worker> workers;
                if(selected.containsKey(toMove.job)){
                    workers = selected.get(toMove.job);
                }else{
                    workers = new ArrayList<>();
                }
                int specialId = specials.get(toMove.job).get(toMove.id);
                toMove.special = specialHashMap.get(specialId);

                ArrayList<Integer> weaponIds = weaponList.get(toMove.job).get(toMove.id);
                toMove.weapons = new ArrayList<>();
                for(Integer key:weaponIds){
                    SalmonRunWeapon weapon = new SalmonRunWeapon();
                    weapon.id = key;
                    if(key>=0){
                        weapon.weapon = weaponHashMap.get(key);
                    }
                    toMove.weapons.add(weapon);
                }
                workers.add(toMove);
                selected.put(toMove.job,workers);
            }

            toSelect = new ArrayList<>();
        }
        return selected;
    }

    public HashMap<Integer,ArrayList<Worker>> selectAll(){
        HashMap<Integer,ArrayList<Worker>> selected = new HashMap<>();
        HashMap<Integer,HashMap<String,ArrayList<Integer>>> weaponList = new HashMap<>();
        HashMap<Integer,HashMap<String,Integer>> specials = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();


        Cursor cursor = database.query(SplatnetContract.Coworker.TABLE_NAME, null, null, null, null, null, null);

        Worker worker;
        ArrayList<Worker> workerDetails = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                worker = new Worker();

                worker.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_PID));
                worker.job = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_JOB));
                worker.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_NAME));
                worker.goldenEggs = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDEN_IKURA_NUM));
                worker.powerEggs = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_IKURA_NUM));
                worker.deadCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DEAD_COUNT));
                worker.helpCount = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_HELP_COUNT));

                worker.specialCounts = new ArrayList<>();

                worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_1)));
                worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_2)));
                worker.specialCounts.add(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL_3)));

                //Boss Kills
                worker.bossKillses = new BossCount();
                worker.bossKillses.goldie = new GrizzCoBossKills();
                worker.bossKillses.goldie.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GOLDIE));

                worker.bossKillses.steelhead = new GrizzCoBossKills();
                worker.bossKillses.steelhead.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEELHEAD));

                worker.bossKillses.flyfish = new GrizzCoBossKills();
                worker.bossKillses.flyfish.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_FLYFISH));

                worker.bossKillses.scrapper = new GrizzCoBossKills();
                worker.bossKillses.scrapper.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SCRAPPER));

                worker.bossKillses.steelEel = new GrizzCoBossKills();
                worker.bossKillses.steelEel.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STEEL_EEL));

                worker.bossKillses.stinger = new GrizzCoBossKills();
                worker.bossKillses.stinger.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_STINGER));

                worker.bossKillses.maws = new GrizzCoBossKills();
                worker.bossKillses.maws.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_MAWS));

                worker.bossKillses.griller = new GrizzCoBossKills();
                worker.bossKillses.griller.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_GRILLER));

                worker.bossKillses.drizzler = new GrizzCoBossKills();
                worker.bossKillses.drizzler.count = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_DRIZZLER));

                //Special handling
                HashMap<String,Integer> specialJobHash;
                if(specials.containsKey(worker.job)){
                    specialJobHash = specials.get(worker.job);
                }else{
                    specialJobHash = new HashMap<>();
                }

                int specialId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_SPECIAL));
                specialManager.addToSelect(specialId);
                specialJobHash.put(worker.id,specialId);


                //Weapon handling
                HashMap<String,ArrayList<Integer>> jobWorkerWeapons;
                if(weaponList.containsKey(worker.job)){
                    jobWorkerWeapons = weaponList.get(worker.job);
                }else{
                    jobWorkerWeapons = new HashMap<>();
                }
                ArrayList<Integer> wepids = new ArrayList<>();

                int wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_1));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_2));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Coworker.COLUMN_WEAPON_3));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                jobWorkerWeapons.put(worker.id,wepids);
                weaponList.put(worker.job,jobWorkerWeapons);

                workerDetails.add(worker);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        HashMap<Integer,Weapon> weaponHashMap = weaponManager.select();
        HashMap<Integer,Special> specialHashMap = specialManager.select();

        for(Worker toMove:workerDetails){
            ArrayList<Worker> workers;
            if(selected.containsKey(toMove.job)){
                workers = selected.get(toMove.job);
            }else{
                workers = new ArrayList<>();
            }
            int specialId = specials.get(toMove.job).get(toMove.id);
            toMove.special = specialHashMap.get(specialId);

            ArrayList<Integer> weaponIds = weaponList.get(toMove.job).get(toMove.id);
            toMove.weapons = new ArrayList<>();
            for(Integer key:weaponIds){
                SalmonRunWeapon weapon = new SalmonRunWeapon();
                weapon.id = key;
                if(key>=0){
                    weapon.weapon = weaponHashMap.get(key);
                }
                toMove.weapons.add(weapon);
            }
            workers.add(toMove);
            selected.put(toMove.job,workers);
        }

        toSelect = new ArrayList<>();

        return selected;
    }
}
