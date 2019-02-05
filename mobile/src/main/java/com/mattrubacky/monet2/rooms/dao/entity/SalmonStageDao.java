package com.mattrubacky.monet2.rooms.dao.entity;

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
    void insert(SalmonStageDao... stage);

    @Update
    void update(SalmonStageDao... stage);

    @Delete
    void delete(SalmonStageDao... stage);

    @Query("SELECT * FROM salmon_stage")
    LiveData<List<SalmonStageDao>> selectAll();

    @Query("SELECT * FROM salmon_stage WHERE id=:id")
    SalmonStageDao select(int id);
}
