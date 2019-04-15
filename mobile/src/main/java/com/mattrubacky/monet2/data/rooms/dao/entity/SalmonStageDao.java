package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SalmonStageDao {

    void insertSalmonStage(SalmonStage salmonStage){
        salmonStage.id = SalmonStage.generateId(salmonStage.name);
        try{
            insert(salmonStage);
        }catch (SQLiteConstraintException e){
        }
    }

    @Insert
    abstract void insert(SalmonStage... stage);

    @Update
    abstract void update(SalmonStage... stage);

    @Delete
    abstract void delete(SalmonStage... stage);

    @Query("SELECT * FROM salmon_stage")
    public abstract LiveData<List<SalmonStage>> selectAll();

    @Query("SELECT * FROM salmon_stage WHERE salmon_stage_id=:id")
    public abstract SalmonStage select(int id);
}
