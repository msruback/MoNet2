package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.entity.DayRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class DayDao {

    void insertDayRoom(Long time){
        try{
            insert(new DayRoom(DayRoom.generateId(time)));
        }catch (SQLiteConstraintException ignored){
        }
    }

    @Insert
    abstract void insert(DayRoom ...dayRooms);

    @Update
    abstract void update(DayRoom ...dayRooms);

    @Delete
    abstract void delete(DayRoom ...dayRooms);

    @Query("SELECT * FROM day")
    abstract List<DayRoom> selectAll();
}
