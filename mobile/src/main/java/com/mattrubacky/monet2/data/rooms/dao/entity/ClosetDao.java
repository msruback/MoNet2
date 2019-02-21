package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.stats.GearStats;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ClosetDao {
    @Insert
    void insert(GearStats... closet);

    @Update
    void update(GearStats... closet);

    @Delete
    void delete(GearStats... closet);
}
