package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.rooms.entity.SplatfestResultRoom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SplatfestResultDao {
    @Insert
    void insert(SplatfestResultRoom... splatfestResult);

    @Update
    void update(SplatfestResultRoom... splatfestResult);

    @Delete
    void delete(SplatfestResultRoom... splatfestResult);

    @Query("SELECT * FROM splatfest_result WHERE id=:id")
    LiveData<SplatfestResultRoom> select(int id);
}
