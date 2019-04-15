package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class RewardGearManager {

    Context context;
    HashMap<Long,RewardGear> toInsert;
    ArrayList<Long> toSelect;

    public RewardGearManager(Context context){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    public void addToInsert(RewardGear rewardGear){
        toInsert.put(rewardGear.available, rewardGear);
    }
    public void addToSelect(long id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            RewardGear rewardGear;
            for (int i = 0; i < keys.length; i++) {
                values = new ContentValues();
                rewardGear = toInsert.get(keys[i]);

                    values.put(SplatnetContract.RewardGear.COLUMN_GEAR, rewardGear.gear.id);
                    values.put(SplatnetContract.RewardGear.COLUMN_GEAR_KIND, rewardGear.gear.kind);
                    values.put(SplatnetContract.RewardGear.COLUMN_START_TIME, rewardGear.available);

                    database.insert(SplatnetContract.RewardGear.TABLE_NAME, null, values);
            }
            database.close();
        }
        toInsert = new HashMap<>();
    }

    public RewardGear select(long id) {
        RewardGear selected;
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[1];
        args[0] = String.valueOf(id);

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.RewardGear.COLUMN_START_TIME + " = ?");

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.RewardGear.TABLE_NAME, null, whereClause, args, null, null, null);
        GearManager gearManager = new GearManager(context);

        selected = new RewardGear();
        if (cursor.moveToFirst()) {
            do {
                selected.available = cursor.getLong(cursor.getColumnIndex(SplatnetContract.RewardGear.COLUMN_START_TIME));
                int gearId = cursor.getInt(cursor.getColumnIndex(SplatnetContract.RewardGear.COLUMN_GEAR));
                String kind =cursor.getString(cursor.getColumnIndex(SplatnetContract.RewardGear.COLUMN_GEAR_KIND));
                gearManager.addToSelect(gearId,kind);
                int gearKind;
                switch (kind){
                    case "head":
                        gearKind = 0;
                        break;
                    case "clothes":
                        gearKind = 1;
                        break;
                    default:
                        gearKind = 2;
                }
                selected.gear = gearManager.select().get(gearKind).get(gearId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        toSelect = new ArrayList<>();

        return selected;
    }

}
