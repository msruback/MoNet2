package com.mattrubacky.monet2.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Special;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Sub;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Weapon;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class WeaponManager extends TableManager<Weapon>{
    TableManager<Special> specialManager;
    TableManager<Sub> subManager;

    ArrayList<Integer> weaponIDs;
    ArrayList<Integer> specialIDs;
    ArrayList<Integer> subIDs;

    public WeaponManager(Context context){
        super(context,Weapon.class);
        //Get managers for tables this table relies on
        specialManager = new TableManager<>(context,Special.class);
        subManager = new TableManager<>(context,Sub.class);
    }
    @Override
    public void addToInsert(Weapon weapon){
        super.addToInsert(weapon);
        specialManager.addToInsert(weapon.special);
        subManager.addToInsert(weapon.sub);
    }

    @Override
    public void insert(){
        subManager.insert();
        specialManager.insert();
        super.insert();
    }

    @Override
    public HashMap<Integer,Weapon> select(){
        weaponIDs = new ArrayList<>();
        specialIDs = new ArrayList<>();
        subIDs = new ArrayList<>();
        HashMap<Integer,Weapon> selected = new HashMap<>();
        HashMap<Integer, Weapon> weaponHashMap = super.select();
        HashMap<Integer, Sub> subHashMap = subManager.select();
        HashMap<Integer, Special> specialHashMap = specialManager.select();

        for (int i = 0; i<weaponIDs.size();i++) {
            Weapon weapon = weaponHashMap.get(weaponIDs.get(i));
            weapon.sub = subHashMap.get(subIDs.get(i));
            weapon.special = specialHashMap.get(specialIDs.get(i));
            selected.put(weapon.id, weapon);
        }
        return selected;
    }

    @Override
    public ArrayList<Weapon> selectAll(){
        weaponIDs = new ArrayList<>();
        specialIDs = new ArrayList<>();
        subIDs = new ArrayList<>();
        ArrayList<Weapon> selected = new ArrayList<>();
        HashMap<Integer, Weapon> weaponHashMap = super.select();
        HashMap<Integer, Sub> subHashMap = subManager.select();
        HashMap<Integer, Special> specialHashMap = specialManager.select();

        for (int i = 0; i<weaponIDs.size();i++) {
            Weapon weapon = weaponHashMap.get(weaponIDs.get(i));
            weapon.sub = subHashMap.get(subIDs.get(i));
            weapon.special = specialHashMap.get(specialIDs.get(i));
            selected.add(weapon);
        }
        return selected;
    }

    @Override
    protected Weapon buildObject(Class<Weapon> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        Weapon weapon = super.buildObject(type,cursor);
        weaponIDs.add(weapon.id);

        int subID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB));
        subIDs.add(subID);
        subManager.addToSelect(subID);

        int specialID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL));
        specialIDs.add(specialID);
        specialManager.addToSelect(specialID);

        return weapon;
    }
}
