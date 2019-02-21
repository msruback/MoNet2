package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonStage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SalmonStageDao {
    @Insert
    void insert(SalmonStage... stage);

    @Update
    void update(SalmonStage... stage);

    @Delete
    void delete(SalmonStage... stage);

    @Query("SELECT * FROM salmon_stage")
    LiveData<List<SalmonStage>> selectAll();

    @Query("SELECT * FROM salmon_stage WHERE id=:id")
    SalmonStage select(int id);
}
