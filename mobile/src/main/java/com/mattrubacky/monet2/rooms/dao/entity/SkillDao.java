package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SkillRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SkillDao {
    @Insert
    void insert(SkillRoom... skill);

    @Update
    void update(SkillRoom... skill);

    @Delete
    void delete(SkillRoom... skill);

    @Query("SELECT * FROM skill WHERE id=:id")
    SkillRoom select(int id);
}
