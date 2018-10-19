package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Skill;
import com.mattrubacky.monet2.sqlite.Factory.DatabaseObjectFactory;

/**
 * Created by mattr on 10/18/2017.
 */

class SkillManager extends TableManager<Skill>{

    public SkillManager(Context context){
        super(context,Skill.class);
    }

    public ArrayList<Skill> selectChunkable(){
        ArrayList<Skill> skills = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Skill.TABLE_NAME,null, SplatnetContract.Skill.COLUMN_CHUNKABLE+" = ?",new String[]{"1"},null,null,null);

        Skill skill;

        if(cursor.moveToFirst()){
            do{
                skill = DatabaseObjectFactory.parseObject(new Skill(),cursor);
                skills.add(skill);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return skills;
    }
}
