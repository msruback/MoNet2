package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;

/**
 * Created by mattr on 10/18/2017.
 */

class SkillManager {
    Context context;
    HashMap<Integer,Skill> toInsert;
    ArrayList<Integer> toSelect;

    public SkillManager(Context context){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    //Add a skill to be inserted
    public void addToInsert(Skill skill){
        toInsert.put(skill.id,skill);
    }

    //Add gearskills
    public void addToInsert(GearSkills skills){

        if(skills.main!=null){
            toInsert.put(skills.main.id,skills.main);
        }
        for(int i=0;i<skills.subs.size();i++) {
            if (skills.subs.get(i) != null) {
                toInsert.put(skills.subs.get(i).id, skills.subs.get(i));
            }
        }

    }

    //Add a skill to be selected
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        Object[] keys = toInsert.keySet().toArray();

        String whereClause = SplatnetContract.Skill._ID +" = ?";
        String[] args;
        Cursor cursor = null;

        Skill skill;
        for (int i = 0; i < keys.length; i++) {
            skill = toInsert.get(keys[i]);

            args = new String[] {String.valueOf(skill.id)};
            cursor = database.query(SplatnetContract.Skill.TABLE_NAME,null,whereClause,args,null,null,null);
            if(cursor.getCount()==0) {

                values.put(SplatnetContract.Skill._ID, skill.id);
                values.put(SplatnetContract.Skill.COLUMN_NAME, skill.name);
                values.put(SplatnetContract.Skill.COLUMN_URL, skill.url);

                if (skill.id > 13) {
                    values.put(SplatnetContract.Skill.COLUMN_CHUNKABLE, false);
                } else {
                    values.put(SplatnetContract.Skill.COLUMN_CHUNKABLE, true);
                }

                database.insert(SplatnetContract.Skill.TABLE_NAME, null, values);
            }
        }
        toInsert = new HashMap<>();
        database.close();
        if(cursor!=null){
            cursor.close();
        }
    }

    public HashMap<Integer,Skill> select(){
        HashMap<Integer,Skill> selected = new HashMap<>();

        if(toSelect.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Skill._ID + " = ?");

            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Skill._ID + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Skill.TABLE_NAME, null, whereClause, args, null, null, null);

            Skill skill;

            if (cursor.moveToFirst()) {
                do {
                    skill = new Skill();
                    skill.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Skill._ID));
                    skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
                    skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
                    selected.put(skill.id, skill);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        }
        return selected;
    }

    public ArrayList<Skill> selectAll(){
        ArrayList<Skill> skills = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Skill.TABLE_NAME,null,null,null,null,null,null);

        Skill skill;

        if(cursor.moveToFirst()){
            do{
                skill = new Skill();
                skill.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Skill._ID));
                skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
                skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
                skills.add(skill);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return skills;
    }
    public ArrayList<Skill> selectChunkable(){
        ArrayList<Skill> skills = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Skill.TABLE_NAME,null, SplatnetContract.Skill.COLUMN_CHUNKABLE+" = ?",new String[]{"1"},null,null,null);

        Skill skill;

        if(cursor.moveToFirst()){
            do{
                skill = new Skill();
                skill.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Skill._ID));
                skill.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_NAME));
                skill.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Skill.COLUMN_URL));
                skills.add(skill);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return skills;
    }
}
