package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SubRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SubDao {
    @Insert
    void insert(SubRoom... sub);

    @Update
    void update(SubRoom... sub);

    @Delete
    void delete(SubRoom... sub);

    @Query("SELECT * FROM sub")
    List<SubRoom> selectAll();

    @Query("SELECT * FROM sub WHERE id=:id")
    SubRoom select(int id);
}
