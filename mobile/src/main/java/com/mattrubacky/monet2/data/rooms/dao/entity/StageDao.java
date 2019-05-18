package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Stage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class StageDao {
    public void insertStage(Stage stage){
        try{
            insert(stage);
        }catch (SQLiteConstraintException e){
        }
    }

    @Insert(onConflict = OnConflictStrategy.FAIL)
    protected abstract void insert(Stage... stage);

    @Update
    abstract void update(Stage... stage);

    @Delete
    abstract void delete(Stage... stage);

    @Query("SELECT * FROM stage WHERE stage_id=:id")
    public abstract LiveData<Stage> select(int id);

    @Query("SELECT * FROM stage")
    public abstract LiveData<List<Stage>> selectAll();
}
