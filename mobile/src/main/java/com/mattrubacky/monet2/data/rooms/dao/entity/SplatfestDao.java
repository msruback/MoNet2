package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SplatfestDao {

    void insertSplatfest(Splatfest splatfest){
        try{
            insert(new SplatfestRoom(splatfest));
        }catch(SQLiteConstraintException e) {
        }
    }

    @Insert
    abstract void insert(SplatfestRoom... splatfest);

    @Update
    abstract void update(SplatfestRoom... splatfest);

    @Delete
    abstract void delete(SplatfestRoom... splatfest);

    @Query("SELECT * FROM splatfest WHERE splatfest_id=:id")
    public abstract SplatfestRoom select(int id);

    @Query("SELECT * FROM splatfest WHERE end_time>:time")
    public abstract LiveData<SplatfestRoom> selectUpcoming(long time);

    @Query("SELECT * FROM splatfest")
    abstract LiveData<List<SplatfestRoom>> selectAll();
}
