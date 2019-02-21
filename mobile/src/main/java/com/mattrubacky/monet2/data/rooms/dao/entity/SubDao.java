package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Sub;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SubDao {
    @Insert
    void insert(Sub... sub);

    @Update
    void update(Sub... sub);

    @Delete
    void delete(Sub... sub);

    @Query("SELECT * FROM sub")
    List<Sub> selectAll();

    @Query("SELECT * FROM sub WHERE id=:id")
    Sub select(int id);
}
