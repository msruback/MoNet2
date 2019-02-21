package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonStage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/19/2018.
 */

public class ShiftManager {
    Context context;
    HashMap<Long,SalmonRunDetail> toInsert;
    ArrayList<Long> toSelect;
    WeaponManager weaponManager;

    public ShiftManager(Context context){
        this.context = context;
        toInsert = new HashMap<>(); //using a hashmap to prevent duplicate entries
        toSelect = new ArrayList<>();
        weaponManager = new WeaponManager(context);
    }

    //Add a sub to be inserted
    public void addToInsert(SalmonRunDetail salmonRunDetail){
        toInsert.put(salmonRunDetail.start, salmonRunDetail);
        for(SalmonRunWeapon weapon : salmonRunDetail.weapons){
            if(weapon.id>0){
                weaponManager.addToInsertSalmon(weapon.weapon);
            }
        }
    }

    //Add a sub to be selected
    public void addToSelect(Long id){
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

            String whereClause = SplatnetContract.Shift.COLUMN_START_TIME +" = ?";
            String[] args;
            Cursor cursor = null;

            SalmonRunDetail shift;
            for (Long key :toInsert.keySet()) {
                shift = toInsert.get(key);

                args = new String[] {String.valueOf(shift.start)};
                cursor = database.query(SplatnetContract.Shift.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values = new ContentValues();

                    values.put(SplatnetContract.Shift.COLUMN_START_TIME, shift.start);
                    values.put(SplatnetContract.Shift.COLUMN_END_TIME, shift.end);
                    values.put(SplatnetContract.Shift.COLUMN_STAGE, shift.stage.name);
                    values.put(SplatnetContract.Shift.COLUMN_STAGE_IMAGE, shift.stage.url);
                    values.put(SplatnetContract.Shift.COLUMN_START_TIME, shift.start);
                    values.put(SplatnetContract.Shift.COLUMN_WEAPON_1,shift.weapons.get(0).id);
                    values.put(SplatnetContract.Shift.COLUMN_WEAPON_2,shift.weapons.get(1).id);
                    values.put(SplatnetContract.Shift.COLUMN_WEAPON_3,shift.weapons.get(2).id);
                    values.put(SplatnetContract.Shift.COLUMN_WEAPON_4,shift.weapons.get(3).id);

                    database.insert(SplatnetContract.Shift.TABLE_NAME, null, values);
                }
            }
            if(cursor!=null){
                cursor.close();
            }
            database.close();
            toInsert = new HashMap<>();
        }
    }

    //Method to execute the select
    public HashMap<Long,SalmonRunDetail> select(){
        HashMap<Long,SalmonRunDetail> selected = new HashMap<>();
        HashMap<Long,ArrayList<Integer>> weaponList = new HashMap<>();
        if(toSelect.size()>0) {

            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Shift.COLUMN_START_TIME + " = ?");

            //build the select statement
            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Shift.COLUMN_START_TIME + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Shift.TABLE_NAME, null, whereClause, args, null, null, null);

            SalmonRunDetail shift;
            ArrayList<SalmonRunDetail> salmonRunDetails = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    shift = new SalmonRunDetail();

                    shift.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_START_TIME));
                    shift.end = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_END_TIME));
                    shift.stage = new SalmonStage();
                    shift.stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_STAGE));
                    shift.stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_STAGE_IMAGE));
                    ArrayList<Integer> wepids = new ArrayList<>();

                    int wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_1));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_2));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_3));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_4));
                    wepids.add(wepId);
                    weaponManager.addToSelect(wepId);

                    weaponList.put(shift.start,wepids);
                    salmonRunDetails.add(shift);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            HashMap<Integer,Weapon> weaponHashMap = weaponManager.select();

            for(SalmonRunDetail toMove: salmonRunDetails){
                ArrayList<Integer> weaponIds = weaponList.get(toMove.start);
                toMove.weapons = new ArrayList<>();
                for(Integer key:weaponIds){
                    SalmonRunWeapon weapon = new SalmonRunWeapon();
                    weapon.id = key;
                    if(key>=0){
                        weapon.weapon = weaponHashMap.get(key);
                    }
                    toMove.weapons.add(weapon);
                }
                selected.put(toMove.start,toMove);
            }

            toSelect = new ArrayList<>();
        }
        return selected;
    }

    public ArrayList<SalmonRunDetail> selectAll(){
        HashMap<Long,ArrayList<Integer>> weaponList = new HashMap<>();
        ArrayList<SalmonRunDetail> shifts = new ArrayList<>();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Shift.TABLE_NAME, null, null, null, null, null, null);

        SalmonRunDetail shift;

        ArrayList<SalmonRunDetail> salmonRunDetails = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                shift = new SalmonRunDetail();

                shift.start = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_START_TIME));
                shift.end = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_END_TIME));
                shift.stage = new SalmonStage();
                shift.stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_STAGE));
                shift.stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_STAGE_IMAGE));

                ArrayList<Integer> wepids = new ArrayList<>();

                int wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_1));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_2));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_3));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                wepId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shift.COLUMN_WEAPON_4));
                wepids.add(wepId);
                weaponManager.addToSelect(wepId);

                weaponList.put(shift.start,wepids);
                salmonRunDetails.add(shift);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        HashMap<Integer,Weapon> weaponHashMap = weaponManager.select();
        for(SalmonRunDetail toMove: salmonRunDetails){
            ArrayList<Integer> weaponIds = weaponList.get(toMove.start);
            toMove.weapons = new ArrayList<>();
            for(Integer key:weaponIds){
                SalmonRunWeapon weapon = new SalmonRunWeapon();
                weapon.id = key;
                if(key>=0){
                    weapon.weapon = weaponHashMap.get(key);
                }
                toMove.weapons.add(weapon);
            }
            shifts.add(toMove);
        }
        return shifts;
    }
}
