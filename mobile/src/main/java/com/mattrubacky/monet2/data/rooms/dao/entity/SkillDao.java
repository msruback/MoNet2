package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SkillDao {
    void insertSkill(Skill skill){
        try{
            insert(skill);
        }catch (SQLiteConstraintException e){

        }
    }

    @Insert
    abstract void insert(Skill... skill);

    @Update
    abstract void update(Skill... skill);

    @Delete
    abstract void delete(Skill... skill);

    @Query("SELECT * FROM skill WHERE id=:id")
    abstract Skill select(int id);

    @Query("SELECT * FROM skill")
    abstract LiveData<List<Skill>> selectAllLive();

    @Query("SELECT * FROM skill")
    abstract List<Skill> selectAll();

    @Query("SELECT * FROM skill WHERE id>100 AND id<200")
    abstract LiveData<List<Skill>> selectSpecialSkillsLive();

    @Query("SELECT * FROM skill WHERE id>100 AND id<200")
    abstract List<Skill> selectSpecialSkills();

    @Query("SELECT * FROM skill WHERE (id<100 AND id!=12 AND id!=13) OR (id>200)")
    abstract LiveData<List<Skill>> selectChunkableSkillsLive();

    @Query("SELECT * FROM skill WHERE (id<100 AND id!=12 AND id!=13) OR (id>200)")
    abstract List<Skill> selectChunkableSkills();
}
