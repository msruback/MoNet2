package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Brand;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class BrandDao {

    void insertBrand(Brand brand){
        try{
            insert(brand);
        }catch(SQLiteConstraintException e) {
        }
    }

    @Insert
    abstract void insert(Brand... brand);

    @Update
    abstract void update(Brand... brand);

    @Delete
    abstract void delete(Brand... brand);

    @Query("SELECT * FROM brand WHERE id=:id")
    abstract Brand select(int id);
}