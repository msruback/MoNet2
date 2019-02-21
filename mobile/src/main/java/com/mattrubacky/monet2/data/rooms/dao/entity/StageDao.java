package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;

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
    void insert(Stage... stage);

    @Update
    void update(Stage... stage);

    @Delete
    void delete(Stage... stage);

    @Query("SELECT * FROM stage WHERE id=:id")
    Stage select(int id);

    @Query("SELECT * FROM stage")
    LiveData<List<Stage>> selectAll();
}
