package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.BrandRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BrandDao {
    @Insert
    void insert(BrandRoom... brand);

    @Update
    void update(BrandRoom... brand);

    @Delete
    void delete(BrandRoom... brand);

    @Query("SELECT * FROM brand WHERE id=:id")
    BrandRoom select(int id);
}
