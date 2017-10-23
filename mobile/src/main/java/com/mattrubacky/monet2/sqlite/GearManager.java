package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.*;

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
}
