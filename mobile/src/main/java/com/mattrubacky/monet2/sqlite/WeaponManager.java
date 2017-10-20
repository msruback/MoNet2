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

class WeaponManager {
    Context context;
    HashMap<Integer,Weapon> toInsert;
    ArrayList<Integer> toSelect;
    SpecialManager specialManager;
    SubManager subManager;

    public WeaponManager(Context context){
        this.context = context;
        //Get managers for tables this table relies on
        specialManager = new SpecialManager(context);
        subManager = new SubManager(context);

        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    public boolean exists(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String whereClause = SplatnetContract.Weapon._ID +" = ?";
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(SplatnetContract.Weapon.TABLE_NAME,null,whereClause,args,null,null,null);

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

    public void addToInsert(Weapon weapon){
        toInsert.put(weapon.id,weapon);
        specialManager.addToInsert(weapon.special);
        subManager.addToInsert(weapon.sub);
    }

    public void addToSelect(int id){
        if(toSelect.contains(id)) {
            toSelect.add(id);
        }
    }

    public void insert(){

        subManager.insert();
        specialManager.insert();

        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            Integer[] keys = (Integer[]) toInsert.keySet().toArray();

            Weapon weapon;

            for(int i=0;i<keys.length;i++) {
                weapon = toInsert.get(keys[i]);

                values.put(SplatnetContract.Weapon._ID, weapon.id);
                values.put(SplatnetContract.Weapon.COLUMN_NAME, weapon.name);
                values.put(SplatnetContract.Weapon.COLUMN_URL, weapon.url);
                values.put(SplatnetContract.Weapon.COLUMN_SUB, weapon.sub.id);
                values.put(SplatnetContract.Weapon.COLUMN_SPECIAL, weapon.special.id);

                database.insert(SplatnetContract.Weapon.TABLE_NAME, null, values);
            }
            database.close();
        }

        toInsert = new HashMap<>();
    }

    public HashMap<Integer,Weapon> select(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Weapon._ID+" = ?");

        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Weapon._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Weapon.TABLE_NAME,null,whereClause,args,null,null,null);

        HashMap<Integer,Weapon> selected = new HashMap<>();

        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<Integer> subIDs = new ArrayList<>();
        ArrayList<Integer> specialIDs= new ArrayList<>();

        int subID,specialID;

        Weapon weapon;

        if(cursor.moveToFirst()){
            do {
                weapon = new Weapon();
                weapon.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon._ID));
                weapon.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_NAME));
                weapon.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_URL));

                subID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB));
                subIDs.add(subID);
                subManager.addToSelect(subID);

                specialID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL));
                specialIDs.add(specialID);
                specialManager.addToSelect(specialID);

                weapons.add(weapon);
            }while(cursor.moveToNext());
        }

        HashMap<Integer,Sub> subHashMap = subManager.select();
        HashMap<Integer,Special> specialHashMap = specialManager.select();

        for(int i=0;i<subIDs.size();i++){
            weapon = weapons.get(i);
            weapon.sub = subHashMap.get(subIDs.get(i));
            weapon.special = specialHashMap.get(specialIDs.get(i));
            selected.put(weapon.id,weapon);
        }

        cursor.close();
        database.close();

        toSelect = new ArrayList<>();

        return selected;
    }

    public ArrayList<Weapon> selectAll(){
        ArrayList<Weapon> selected = new ArrayList<>();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Weapon.TABLE_NAME,null,null,null,null,null,null);

        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<Integer> subIDs = new ArrayList<>();
        ArrayList<Integer> specialIDs= new ArrayList<>();

        int subID,specialID;

        Weapon weapon;

        if(cursor.moveToFirst()){
            do {
                weapon = new Weapon();
                weapon.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon._ID));
                weapon.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_NAME));
                weapon.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_URL));

                subID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB));
                subIDs.add(subID);
                subManager.addToSelect(subID);

                specialID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL));
                specialIDs.add(specialID);
                specialManager.addToSelect(specialID);

                weapons.add(weapon);
            }while(cursor.moveToNext());
        }

        HashMap<Integer,Sub> subHashMap = subManager.select();
        HashMap<Integer,Special> specialHashMap = specialManager.select();

        for(int i=0;i<subIDs.size();i++){
            weapon = weapons.get(i);
            weapon.sub = subHashMap.get(subIDs.get(i));
            weapon.special = specialHashMap.get(specialIDs.get(i));
            selected.add(weapon);
        }

        cursor.close();
        database.close();

        return selected;
    }
}
