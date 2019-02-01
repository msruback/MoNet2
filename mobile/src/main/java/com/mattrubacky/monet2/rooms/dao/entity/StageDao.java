package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.StageRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StageDao {
    @Insert
    void insert(StageRoom... stage);

    @Update
    void update(StageRoom... stage);

    @Delete
    void delete(StageRoom... stage);

    @Query("SELECT * FROM stage")
    LiveData<List<StageRoom>> selectAll();
}
