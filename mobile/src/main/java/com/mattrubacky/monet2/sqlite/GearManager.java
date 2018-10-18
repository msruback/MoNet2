package com.mattrubacky.monet2.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Brand;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Skill;
import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;

/**
 * Created by mattr on 10/18/2017.
 */

class GearManager{
    Context context;
    TableManager<Brand> brandManager;
    TableManager<Gear> headManager;
    TableManager<Gear> clothesManager;
    TableManager<Gear> shoeManager;
    TableManager<Skill> skillManager;

    public GearManager(Context context){
        this.context = context;

        brandManager = new TableManager<Brand>(context,Brand.class);
        headManager = new TableManager<Gear>(context,Gear.class,SplatnetContract.Head.TABLE_NAME);
        clothesManager = new TableManager<Gear>(context,Gear.class,SplatnetContract.Clothes.TABLE_NAME);
        shoeManager = new TableManager<Gear>(context,Gear.class,SplatnetContract.Shoe.TABLE_NAME);

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
        brandManager.addToInsert(gear.brand);
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
        try {
            switch (kind) {
                case "head":
                    gear = headManager.select(id);
                    break;
                case "clothes":
                    gear = clothesManager.select(id);
                    break;
                case "shoes":
                    gear = shoeManager.select(id);
            }
            gear.brand = brandManager.select(gear.brand.id);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return gear;
    }

    public ArrayList<HashMap<Integer,Gear>> select(){
        ArrayList<HashMap<Integer,Gear>> selected = new ArrayList<>();
        HashMap<Integer,Brand> brandHashMap = new HashMap<>();
        for(Brand brand : brandManager.selectAll()){
            brandHashMap.put(brand.id,brand);
        }

        HashMap<Integer,Gear> head = headManager.select();
        for(Integer key : head.keySet()){
            Gear gear = head.get(key);
            gear.brand = brandHashMap.get(gear.brand.id);
            head.put(gear.id,gear);
        }

        HashMap<Integer,Gear> clothes = clothesManager.select();
        for(Integer key : clothes.keySet()){
            Gear gear = clothes.get(key);
            gear.brand = brandHashMap.get(gear.brand.id);
            clothes.put(gear.id,gear);
        }

        HashMap<Integer,Gear> shoe = shoeManager.select();
        for(Integer key : shoe.keySet()){
            Gear gear = shoe.get(key);
            gear.brand = brandHashMap.get(gear.brand.id);
            shoe.put(gear.id,gear);
        }

        selected.add(head);
        selected.add(clothes);
        selected.add(shoe);
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
