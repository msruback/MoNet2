package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SplatfestRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SplatfestDao {
    @Insert
    void insert(SplatfestRoom... splatfest);

    @Update
    void update(SplatfestRoom... splatfest);

    @Delete
    void delete(SplatfestRoom... splatfest);

    @Query("SELECT * FROM splatfest WHERE id=:id")
    SplatfestRoom select(int id);

    @Query("SELECT * FROM splatfest WHERE end_time>:time")
    SplatfestRoom selectUpcoming(long time);

    @Query("SELECT * FROM splatfest")
    LiveData<List<SplatfestRoom>> selectAll();
}
