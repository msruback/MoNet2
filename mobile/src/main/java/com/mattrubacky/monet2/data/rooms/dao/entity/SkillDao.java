package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SkillDao {
    @Insert
    void insert(Skill... skill);

    @Update
    void update(Skill... skill);

    @Delete
    void delete(Skill... skill);

    @Query("SELECT * FROM skill WHERE id=:id")
    Skill select(int id);
}
