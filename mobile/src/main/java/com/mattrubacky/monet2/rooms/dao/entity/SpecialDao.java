package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SpecialRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SpecialDao {
    @Insert
    void insert(SpecialRoom... special);

    @Update
    void update(SpecialRoom... special);

    @Delete
    void delete(SpecialRoom... special);

    @Query("SELECT * FROM special")
    List<SpecialRoom> selectAll();

    @Query("SELECT * FROM special WHERE id=:id")
    SpecialRoom select(int id);
}
