package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Sub;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SubDao {

    void insertSub(Sub sub){
        try{
            insert(sub);
        }catch(SQLiteConstraintException e){

        }
    }

    @Insert
    abstract void insert(Sub... sub);

    @Update
    abstract void update(Sub... sub);

    @Delete
    abstract void delete(Sub... sub);

    @Query("SELECT * FROM sub")
    abstract List<Sub> selectAll();

    @Query("SELECT * FROM sub WHERE id=:id")
    abstract Sub select(int id);
}
