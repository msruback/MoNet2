package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Special;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SpecialDao {
    @Insert
    void insert(Special... special);

    @Update
    void update(Special... special);

    @Delete
    void delete(Special... special);

    @Query("SELECT * FROM special")
    List<Special> selectAll();

    @Query("SELECT * FROM special WHERE id=:id")
    Special select(int id);
}
