package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;

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
        gear.generatedId = Gear.generateId(gear.kind,gear.id);
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

    @Query("SELECT * FROM gear WHERE gear_id=:id")
    public abstract Gear select(int id);

    @Query("SELECT * FROM gear WHERE splatnet_id=:id AND kind=:kind")
    public abstract Gear select(int id, String kind);

    @Query("SELECT * FROM gear WHERE kind='head'")
    public abstract LiveData<List<Gear>> selectHeadLive();

    @Query("SELECT * FROM gear WHERE kind='head'")
    public abstract List<Gear> selectHead();

    @Query("SELECT * FROM gear WHERE kind='clothes'")
    public abstract LiveData<List<Gear>> selectClothesLive();

    @Query("SELECT * FROM gear WHERE kind='clothes'")
    public abstract List<Gear> selectClothes();

    @Query("SELECT * FROM gear WHERE kind='shoe'")
    public abstract LiveData<List<Gear>> selectShoesLive();

    @Query("SELECT * FROM gear WHERE kind='shoe'")
    public abstract List<Gear> selectShoes();
}
