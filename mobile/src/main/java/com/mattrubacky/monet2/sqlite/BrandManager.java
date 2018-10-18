package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Brand;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Skill;

/**
 * Created by mattr on 10/18/2017.
 */

class BrandManager {
    Context context;
    HashMap<Integer,Brand> toInsert;
    ArrayList<Integer> toSelect;
    SkillManager skillManager;

    public BrandManager(Context context){
        this.context = context;

        toInsert = new HashMap<>(); //using a hashmap to prevent duplicate entries
        toSelect = new ArrayList<>();
        skillManager = new SkillManager(context);
    }

    public void addToInsert(Brand brand){
        toInsert.put(brand.id,brand);

        //In case the brand is a neutral brand like Grizz Co. or Cuttlegear
        if(brand.skill!=null){
            skillManager.addToInsert(brand.skill);
        }
    }

    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){

        skillManager.insert();

        if(toInsert.size()>0){
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            skillManager.insert();

            String whereClause = SplatnetContract.Brand._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            Brand brand;

            for(int i=0;i<keys.length;i++) {
                brand = toInsert.get(keys[i]);

                args = new String[] {String.valueOf(brand.id)};
                cursor = database.query(SplatnetContract.Brand.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values = new ContentValues();

                    values.put(SplatnetContract.Brand._ID, brand.id);
                    values.put(SplatnetContract.Brand.COLUMN_NAME, brand.name);
                    values.put(SplatnetContract.Brand.COLUMN_URL, brand.url);

                    //In case the brand is a neutral brand like Grizz Co. or Cuttlegear
                    if(brand.skill!=null){
                        values.put(SplatnetContract.Brand.COLUMN_SKILL, brand.skill.id);
                    }
                    database.insert(SplatnetContract.Brand.TABLE_NAME, null, values);
                }
            }
            toInsert = new HashMap<>();
            database.close();
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    public HashMap<Integer,Brand> select(){
        HashMap<Integer,Brand> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Brand._ID+" = ?");

        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Brand._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Brand.TABLE_NAME,null,whereClause,args,null,null,null);

        ArrayList<Brand> brands = new ArrayList<>();
        ArrayList<Integer> skillIDs = new ArrayList<>();

        int skillID;

        Brand brand;

        if(cursor.moveToFirst()) {
            do{
                brand = new Brand();

                brand.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Brand._ID));
                brand.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_NAME));
                brand.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_URL));

                skillID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Brand.COLUMN_SKILL));
                skillIDs.add(skillID);
                skillManager.addToSelect(skillID);

                brands.add(brand);
            }while(cursor.moveToNext());
        }

        HashMap<Integer,Skill> skillHashMap = skillManager.select();

        for(int i=0;i<skillIDs.size();i++){
            brand = brands.get(i);
            brand.skill = skillHashMap.get(skillIDs.get(i));
            selected.put(brand.id,brand);
        }

        cursor.close();
        database.close();

        return  selected;
    }
}
