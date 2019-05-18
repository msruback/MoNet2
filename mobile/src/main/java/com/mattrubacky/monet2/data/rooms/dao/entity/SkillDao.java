package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Skill;

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
            if(skill!=null){
                insert(skill);
            }
        }catch (SQLiteConstraintException e){
           //e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(Skill... skill);

    @Update
    abstract void update(Skill... skill);

    @Delete
    abstract void delete(Skill... skill);

    @Query("SELECT * FROM skill WHERE skill_id=:id")
    abstract LiveData<Skill> select(Integer id);

    @Query("SELECT * FROM skill")
    abstract LiveData<List<Skill>> selectAll();

    @Query("SELECT * FROM skill WHERE skill_id>100 AND skill_id<200")
    abstract LiveData<List<Skill>> selectSpecialSkills();

    @Query("SELECT * FROM skill WHERE (skill_id<100 AND skill_id!=12 AND skill_id!=13) OR (skill_id>200)")
    abstract LiveData<List<Skill>> selectChunkableSkills();
}
