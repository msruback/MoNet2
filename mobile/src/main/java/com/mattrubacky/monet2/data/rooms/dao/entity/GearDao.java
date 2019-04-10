package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class GearDao {
    void insertGear(Gear gear,boolean isDefault,BrandDao brandDao,SkillDao skillDao){
        brandDao.insertBrand(gear.brand,skillDao);
        try{
            insert(gear);
        }catch(SQLiteConstraintException e){
            if(isDefault){
                update(gear);
            }
        }
    }

    @Insert
    protected abstract void insert(Gear... gear);

    @Update
    protected abstract void update(Gear... gear);

    @Delete
    protected abstract void delete(Gear... gear);

    @Query("SELECT * FROM gear WHERE id=:id")
    abstract Gear select(int id);

    @Query("SELECT * FROM gear WHERE splatnet_id=:id AND kind=:kind")
    abstract Gear select(int id, String kind);


    @Query("SELECT * FROM gear WHERE kind='head'")
    abstract LiveData<List<Gear>> selectHeadLive();

    @Query("SELECT * FROM gear WHERE kind='head'")
    abstract List<Gear> selectHead();

    @Query("SELECT * FROM gear WHERE kind='clothes'")
    abstract LiveData<List<Gear>> selectClothesLive();

    @Query("SELECT * FROM gear WHERE kind='clothes'")
    abstract List<Gear> selectClothes();

    @Query("SELECT * FROM gear WHERE kind='shoe'")
    abstract LiveData<List<Gear>> selectShoesLive();

    @Query("SELECT * FROM gear WHERE kind='shoe'")
    abstract List<Gear> selectShoes();
}
