package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Brand;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BrandDao {
    @Insert
    void insert(Brand... brand);

    @Update
    void update(Brand... brand);

    @Delete
    void delete(Brand... brand);

    @Query("SELECT * FROM brand WHERE id=:id")
    Brand select(int id);
}
