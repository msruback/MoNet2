package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Special;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SpecialDao {

    void insertSpecial(Special special){
        try{
            insert(special);
        }catch(SQLiteConstraintException e){

        }
    }

    @Insert
    abstract void insert(Special... special);

    @Update
    abstract void update(Special... special);

    @Delete
    abstract void delete(Special... special);

    @Query("SELECT * FROM special")
    abstract LiveData<List<Special>> selectAll();

    @Query("SELECT * FROM special WHERE special_id=:id")
    abstract LiveData<Special> select(int id);
}
