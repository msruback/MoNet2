package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.GearRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GearDao {
    @Insert
    void insert(GearRoom... gear);

    @Update
    void update(GearRoom... gear);

    @Delete
    void delete(GearRoom... gear);

    @Query("SELECT * FROM gear WHERE id=:id")
    GearRoom select(int id);

    @Query("SELECT * FROM gear WHERE splatnet_id=:id AND kind=:kind")
    GearRoom select(int id, String kind);
}
