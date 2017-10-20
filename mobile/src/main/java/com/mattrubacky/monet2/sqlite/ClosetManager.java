package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class ClosetManager {
    Context context;
    HashMap<Integer,ClosetHanger> toUpdate;
    HashMap<Integer,ClosetHanger> toInsert;
    ArrayList<Integer> toSelect;
    GearManager gearManager;
    SkillManager skillManager;

    public ClosetManager(Context context){
        this.context = context;

        gearManager = new GearManager(context);
        skillManager = new SkillManager(context);

        toInsert = new HashMap<>();  //using a hashmap to prevent duplicate entries
        toUpdate = new HashMap<>();  //using a hashmap to prevent duplicate entries, and keep updates low
        toSelect = new ArrayList<>();

    }

    private boolean exists(int id){
        return true;
    }

    public void addToInsert(Gear gear, GearSkills skills,Battle battle){
        ClosetHanger hanger = new ClosetHanger();
        hanger.gear = gear;
        hanger.skills = skills;
        hanger.time = battle.start;

        toInsert.put(gear.id,hanger);
    }

    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        if(toInsert.size()>0){
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Integer[] keys = (Integer[]) toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Closet._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            ClosetHanger closetHanger;
            for(int i=0;i<keys.length;i++){
                values = new ContentValues();
                closetHanger = toInsert.get(keys[i]);

                values.put(SplatnetContract.Closet._ID,closetHanger.gear.id);
                values.put(SplatnetContract.Closet.COLUMN_GEAR,closetHanger.gear.id);
                values.put(SplatnetContract.Closet.COLUMN_MAIN,closetHanger.skills.main.id);

                values.put(SplatnetContract.Closet.COLUMN_LAST_USE_TIME,closetHanger.time);

                if(closetHanger.skills.subs.get(0)!=null){
                    values.put(SplatnetContract.Closet.COLUMN_SUB_1,closetHanger.skills.subs.get(0).id);
                    if(closetHanger.skills.subs.get(1)!=null){
                        values.put(SplatnetContract.Closet.COLUMN_SUB_2,closetHanger.skills.subs.get(1).id);
                        if(closetHanger.skills.subs.get(2)!=null){
                            values.put(SplatnetContract.Closet.COLUMN_SUB_3,closetHanger.skills.subs.get(2).id);
                        }else{
                            values.put(SplatnetContract.Closet.COLUMN_SUB_3,-1);
                        }
                    }else{
                        values.put(SplatnetContract.Closet.COLUMN_SUB_2,-1);
                        values.put(SplatnetContract.Closet.COLUMN_SUB_3,-1);
                    }
                }else{
                    values.put(SplatnetContract.Closet.COLUMN_SUB_1,-1);
                    values.put(SplatnetContract.Closet.COLUMN_SUB_2,-1);
                    values.put(SplatnetContract.Closet.COLUMN_SUB_3,-1);
                }

                args = new String[] {String.valueOf(closetHanger.gear.id)};
                cursor = database.query(SplatnetContract.Closet.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    database.insert(SplatnetContract.Closet.TABLE_NAME, null, values);
                }else{
                    database.update(SplatnetContract.Closet.TABLE_NAME, values,whereClause,args);
                }
            }
            cursor.close();
        }
    }

    //public ClosetHanger select(int id){}

    //public ArrayList<ClosetHanger> select(){}
}
