package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.data.deserialized_entities.Brand;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/23/2017.
 */

public class ShoeManager {
    Context context;
    HashMap<Integer,Gear> toInsert;
    ArrayList<Integer> toSelect;
    BrandManager brandManager;

    public ShoeManager(Context context){
        this.context = context;

        brandManager = new BrandManager(context);

        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    public void addToInsert(Gear gear){
        toInsert.put(gear.id,gear);
    }

    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){

        brandManager.insert();

        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Shoe._ID + " = ?";
            String[] args;
            Cursor cursor = null;

            Gear gear;

            for(int i=0;i<keys.length;i++){
                gear = toInsert.get(keys[i]);

                args = new String[] {String.valueOf(gear.id)};
                cursor = database.query(SplatnetContract.Shoe.TABLE_NAME,null,whereClause,args,null,null,null);

                values = new ContentValues();
                values.put(SplatnetContract.Shoe._ID,gear.id);
                values.put(SplatnetContract.Shoe.COLUMN_NAME,gear.name);
                values.put(SplatnetContract.Shoe.COLUMN_KIND,gear.kind);
                values.put(SplatnetContract.Shoe.COLUMN_RARITY,gear.rarity);
                values.put(SplatnetContract.Shoe.COLUMN_URL,gear.url);
                values.put(SplatnetContract.Shoe.COLUMN_BRAND,gear.brand.id);

                if(cursor.getCount()==0){
                    database.insert(SplatnetContract.Shoe.TABLE_NAME, null, values);
                }else{
                    database.update(SplatnetContract.Shoe.TABLE_NAME,values,whereClause,args);
                }
            }
            if(cursor!=null) {
                cursor.close();
            }
            database.close();
            toInsert = new HashMap<>();
        }
    }

    public Gear select(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[1];
        args[0] = String.valueOf(id);

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Shoe._ID+" = ?");

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Shoe.TABLE_NAME,null,whereClause,args,null,null,null);

        int brandID;

        Gear gear = new Gear();

        if(cursor.moveToFirst()){

            gear.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe._ID));
            gear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_NAME));
            gear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_KIND));
            gear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_RARITY));
            gear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_URL));

            brandID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_BRAND));
            brandManager.addToSelect(brandID);

            HashMap<Integer,Brand> brandHashMap = brandManager.select();
            gear.brand = brandHashMap.get(brandID);
        }
        cursor.close();
        database.close();
        return gear;
    }

    public HashMap<Integer,Gear> select(){
        HashMap<Integer,Gear> selected = new HashMap<>();
        if(toSelect.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Shoe._ID + " = ?");

            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Shoe._ID + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Shoe.TABLE_NAME, null, whereClause, args, null, null, null);

            ArrayList<Gear> gears = new ArrayList<>();

            ArrayList<Integer> brandIDs = new ArrayList<>();
            int brandID;

            Gear gear;

            if (cursor.moveToFirst()) {
                do {
                    gear = new Gear();

                    gear.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe._ID));
                    gear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_NAME));
                    gear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_KIND));
                    gear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_RARITY));
                    gear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_URL));

                    brandID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_BRAND));
                    brandManager.addToSelect(brandID);
                    brandIDs.add(brandID);

                    gears.add(gear);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
            HashMap<Integer, Brand> brandHashMap = brandManager.select();

            for (int i = 0; i < gears.size(); i++) {
                gear = gears.get(i);
                gear.brand = brandHashMap.get(brandIDs.get(i));
                selected.put(gear.id, gear);
            }

        }
        return selected;
    }
    public ArrayList<Gear> selectAll(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Shoe.TABLE_NAME,null,null,null,null,null,null);

        ArrayList<Gear> gears = new ArrayList<>();
        ArrayList<Gear> selected = new ArrayList<>();

        ArrayList<Integer> brandIDs = new ArrayList<>();
        int brandID;

        Gear gear;

        if(cursor.moveToFirst()){
            do{
                gear = new Gear();

                gear.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe._ID));
                gear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_NAME));
                gear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_KIND));
                gear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_RARITY));
                gear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_URL));

                brandID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Shoe.COLUMN_BRAND));
                brandManager.addToSelect(brandID);
                brandIDs.add(brandID);

                gears.add(gear);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        HashMap<Integer,Brand> brandHashMap = brandManager.select();

        for(int i=0;i<gears.size();i++){
            gear = gears.get(i);
            gear.brand = brandHashMap.get(brandIDs.get(i));
            selected.add(gear);
        }
        return selected;
    }
}
