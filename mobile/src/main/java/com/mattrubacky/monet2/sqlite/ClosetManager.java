package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.data.stats.GearStats;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class ClosetManager {
    Context context;
    HashMap<Integer, GearStats> toInsert;
    ArrayList<Integer> toSelect;
    GearManager gearManager;
    SkillManager skillManager;

    public ClosetManager(Context context){
        this.context = context;

        gearManager = new GearManager(context);
        skillManager = new SkillManager(context);

        toInsert = new HashMap<>(); //using a hashmap to prevent duplicate entries, and keep updates low

    }

    public void addToInsert(Gear gear, GearSkills skills, Battle battle){
        GearStats hanger = new GearStats();
        hanger.gear = gear;
        hanger.skills = skills;
        hanger.time = battle.start;

        toInsert.put(gear.id,hanger);
    }
    public void addToInsert(GearStats hanger){
        toInsert.put(hanger.gear.id,hanger);
    }

    public void addToSelect(int id,String kind){
        id*=10;
        switch (kind){
            case "head":
                id+=1;
                break;
            case "clothes":
                id+=2;
                break;
            case "shoes":
                id+=3;
                break;
        }
        toSelect.add(id);
    }

    public void insert(){
        if(toInsert.size()>0){
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Closet._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            int id;

            GearStats gearStats;
            for(int i=0;i<keys.length;i++){
                values = new ContentValues();
                gearStats = toInsert.get(keys[i]);

                id = gearStats.gear.id;
                id*=10;
                switch (gearStats.gear.kind){
                    case "head":
                        id+=1;
                        break;
                    case "clothes":
                        id+=2;
                        break;
                    case "shoes":
                        id+=3;
                        break;
                }

                values.put(SplatnetContract.Closet._ID,id);
                values.put(SplatnetContract.Closet.COLUMN_GEAR, gearStats.gear.id);
                values.put(SplatnetContract.Closet.COLUMN_KIND, gearStats.gear.kind);

                values.put(SplatnetContract.Closet.COLUMN_MAIN, gearStats.skills.main.id);

                values.put(SplatnetContract.Closet.COLUMN_LAST_USE_TIME, gearStats.time);

                if(gearStats.skills.subs.get(0)!=null){
                    values.put(SplatnetContract.Closet.COLUMN_SUB_1, gearStats.skills.subs.get(0).id);
                    if(gearStats.skills.subs.get(1)!=null){
                        values.put(SplatnetContract.Closet.COLUMN_SUB_2, gearStats.skills.subs.get(1).id);
                        if(gearStats.skills.subs.get(2)!=null){
                            values.put(SplatnetContract.Closet.COLUMN_SUB_3, gearStats.skills.subs.get(2).id);
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

                args = new String[] {String.valueOf(id)};
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

    public GearStats select(int id, String kind){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        id*=10;
        switch (kind){
            case "head":
                id+=1;
                break;
            case "clothes":
                id+=2;
                break;
            case "shoes":
                id+=3;
                break;
        }

        String[] args = new String[1];
        args[0] = String.valueOf(id);

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Closet._ID+" = ?");

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Closet.TABLE_NAME,null,whereClause,args,null,null,null);

        GearStats gearStats = new GearStats();

        if(cursor.moveToFirst()){
            gearStats.gear = gearManager.select(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_GEAR)),cursor.getString(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_KIND)));

            skillManager.addToSelect(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_MAIN)));

            skillManager.addToSelect(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_1)));
            skillManager.addToSelect(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_2)));
            skillManager.addToSelect(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_3)));

            HashMap<Integer,Skill> skillHashMap = skillManager.select();

            gearStats.skills = new GearSkills();

            gearStats.skills.main = skillHashMap.get(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_MAIN)));

            gearStats.skills.subs = new ArrayList<>();
            gearStats.skills.subs.add(skillHashMap.get(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_1))));
            gearStats.skills.subs.add(skillHashMap.get(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_2))));
            gearStats.skills.subs.add(skillHashMap.get(cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_3))));

            gearStats.time = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_LAST_USE_TIME));

        }
        cursor.close();
        database.close();

        return gearStats;
    }

    public ArrayList<GearStats> selectAll(){
        ArrayList<GearStats> selected = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Closet.TABLE_NAME,null,null,null,null,null,null);

        GearStats gearStats;

        ArrayList<GearStats> hangers = new ArrayList<>();

        ArrayList<Integer> gearIDs = new ArrayList<>();
        int gearID;

        ArrayList<String> gearKinds = new ArrayList<>();
        String gearKind;

        ArrayList<Integer> mainIDs = new ArrayList<>();
        int mainID;

        ArrayList<Integer> sub1IDs = new ArrayList<>(), sub2IDs = new ArrayList<>(),sub3IDs = new ArrayList<>();
        int sub1ID,sub2ID,sub3ID;

        if(cursor.moveToFirst()){
            do {
                gearStats = new GearStats();

                gearID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_GEAR));
                gearKind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_KIND));

                gearManager.addToSelect(gearID, gearKind);

                gearIDs.add(gearID);
                gearKinds.add(gearKind);

                mainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_MAIN));
                skillManager.addToSelect(mainID);
                mainIDs.add(mainID);

                sub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_1));
                skillManager.addToSelect(sub1ID);
                sub1IDs.add(sub1ID);

                sub2ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_2));
                skillManager.addToSelect(sub2ID);
                sub2IDs.add(sub2ID);

                sub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_SUB_3));
                skillManager.addToSelect(sub3ID);
                sub3IDs.add(sub3ID);

                gearStats.time = cursor.getLong(cursor.getColumnIndex(SplatnetContract.Closet.COLUMN_LAST_USE_TIME));

                hangers.add(gearStats);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        ArrayList<HashMap<Integer,Gear>> gearList = gearManager.select();
        HashMap<Integer,Gear> gearHashMap = new HashMap<>();
        HashMap<Integer,Skill> skillHashMap = skillManager.select();
        for(int i=0;i<hangers.size();i++){
            gearStats = hangers.get(i);
            switch(gearKinds.get(i)) {
                case "head":
                    gearHashMap = gearList.get(0);
                    break;
                case "clothes":
                    gearHashMap = gearList.get(1);
                    break;
                case "shoes":
                    gearHashMap = gearList.get(2);
            }
            gearStats.gear = gearHashMap.get(gearIDs.get(i));

            gearStats.skills = new GearSkills();

            gearStats.skills.main = skillHashMap.get(mainIDs.get(i));

            gearStats.skills.subs = new ArrayList<>();
            gearStats.skills.subs.add(skillHashMap.get(sub1IDs.get(i)));
            gearStats.skills.subs.add(skillHashMap.get(sub2IDs.get(i)));
            gearStats.skills.subs.add(skillHashMap.get(sub3IDs.get(i)));
            selected.add(gearStats);
        }


        return selected;
    }
}
