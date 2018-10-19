package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Brand;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Skill;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Special;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Sub;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Weapon;

/**
 * Created by mattr on 10/18/2017.
 */

class BrandManager extends TableManager<Brand>{
    SkillManager skillManager;
    ArrayList<Skill> skillIDs;

    public BrandManager(Context context){
        super(context,Brand.class);
        skillManager = new SkillManager(context);
    }

    @Override
    public void addToInsert(Brand brand){
        super.addToInsert(brand);
        skillManager.addToInsert(brand.skill);
    }

    @Override
    public void insert(){
        skillManager.insert();
        super.insert();
    }

    @Override
    public HashMap<Integer,Brand> select(){
        skillIDs = new ArrayList<>();
        HashMap<Integer,Brand> selected = new HashMap<>();
        HashMap<Integer, Brand> brandHashMap = super.select();
        HashMap<Integer, Skill> subHashMap = skillManager.select();

        for (Integer key : brandHashMap.keySet()) {
            Brand brand = brandHashMap.get(key);
            brand.skill = subHashMap.get(brand.skill.id);
            selected.put(brand.id,brand);
        }
        return selected;
    }

    @Override
    public ArrayList<Brand> selectAll(){
        skillIDs = new ArrayList<>();
        ArrayList<Brand> selected = new ArrayList<>();
        HashMap<Integer, Brand> brandHashMap = new HashMap<>();
        for(Brand brand : super.selectAll()){
            brandHashMap.put(brand.id,brand);
        }
        HashMap<Integer, Skill> subHashMap = skillManager.select();

        for (Integer key : brandHashMap.keySet()) {
            Brand brand = brandHashMap.get(key);
            brand.skill = subHashMap.get(brand.skill.id);
        }
        return selected;
    }

    @Override
    protected Brand buildObject(Class<Brand> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        Brand brand= super.buildObject(type,cursor);
        skillManager.addToSelect(brand.skill.id);

        return brand;
    }
}
