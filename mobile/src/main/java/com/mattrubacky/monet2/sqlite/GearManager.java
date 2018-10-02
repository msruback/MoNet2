package com.mattrubacky.monet2.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.Brand;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;

/**
 * Created by mattr on 10/18/2017.
 */

class GearManager {
    Context context;
    HashMap<Integer,Gear> toInsert;
    ArrayList<Integer> toSelect;
    BrandManager brandManager;
    HeadManager headManager;
    ClothesManager clothesManager;
    ShoeManager shoeManager;

    public GearManager(Context context){
        this.context = context;

        brandManager = new BrandManager(context);
        headManager = new HeadManager(context);
        clothesManager = new ClothesManager(context);
        shoeManager = new ShoeManager(context);

        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    public void addToInsert(Gear gear){
        switch(gear.kind){
            case "head":
                headManager.addToInsert(gear);
                break;
            case "clothes":
                clothesManager.addToInsert(gear);
                break;
            case "shoes":
                shoeManager.addToInsert(gear);
                break;
        }
    }

    public void addToSelect(int id,String kind){
        switch(kind){
            case "head":
                headManager.addToSelect(id);
                break;
            case "clothes":
                clothesManager.addToSelect(id);
                break;
            case "shoes":
                shoeManager.addToSelect(id);
                break;
        }
    }

    public void insert(){

        brandManager.insert();

        headManager.insert();
        clothesManager.insert();
        shoeManager.insert();
    }

    public Gear select(int id,String kind){
        Gear gear = new Gear();
        switch (kind){
            case "head":
                gear = headManager.select(id);
                break;
            case "clothes":
                gear = clothesManager.select(id);
                break;
            case "shoes":
                gear = shoeManager.select(id);
        }
        return gear;
    }

    public ArrayList<HashMap<Integer,Gear>> select(){
        ArrayList<HashMap<Integer,Gear>> selected = new ArrayList<>();
        selected.add(headManager.select());
        selected.add(clothesManager.select());
        selected.add(shoeManager.select());
        return selected;
    }
    public ArrayList<Gear> selectAll() {
        ArrayList<Gear> selected = new ArrayList<>();
        selected.addAll(headManager.selectAll());
        selected.addAll(clothesManager.selectAll());
        selected.addAll(shoeManager.selectAll());
        return selected;
    }
    public void updateTo4(){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Gear._ID+" = ?");

        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Gear._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Gear.TABLE_NAME,null,whereClause,args,null,null,null);

        ArrayList<Gear> gears = new ArrayList<>();
        ArrayList<Gear> selected = new ArrayList<>();

        ArrayList<Integer> brandIDs = new ArrayList<>();
        int brandID;

        Gear gear;

        if(cursor.moveToFirst()){
            do{
                gear = new Gear();

                gear.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear._ID));
                gear.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_NAME));
                gear.kind = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_KIND));
                gear.rarity = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_RARITY));
                gear.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_URL));

                brandID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Gear.COLUMN_BRAND));
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
            switch(gear.kind){
                case "head":
                    headManager.addToInsert(gear);
                    break;
                case "clothes":
                    clothesManager.addToInsert(gear);
                    break;
                case "shoes":
                    shoeManager.addToInsert(gear);
                    break;
            }
        }

        headManager.insert();
        clothesManager.insert();
        shoeManager.insert();

    }
}
