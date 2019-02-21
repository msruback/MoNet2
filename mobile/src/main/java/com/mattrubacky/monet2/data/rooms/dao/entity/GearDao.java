package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GearDao {
    @Insert
    void insert(Gear... gear);

    @Update
    void update(Gear... gear);

    @Delete
    void delete(Gear... gear);

    @Query("SELECT * FROM gear WHERE id=:id")
    Gear select(int id);

    @Query("SELECT * FROM gear WHERE splatnet_id=:id AND kind=:kind")
    Gear select(int id, String kind);
}
