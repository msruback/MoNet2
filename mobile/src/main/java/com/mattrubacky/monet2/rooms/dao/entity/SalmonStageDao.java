package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SalmonStageRoom;

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
    void insert(SalmonStageRoom... stage);

    @Update
    void update(SalmonStageRoom... stage);

    @Delete
    void delete(SalmonStageRoom... stage);

    @Query("SELECT * FROM salmon_stage")
    LiveData<List<SalmonStageRoom>> selectAll();

    @Query("SELECT * FROM salmon_stage WHERE id=:id")
    SalmonStageRoom select(int id);
}
