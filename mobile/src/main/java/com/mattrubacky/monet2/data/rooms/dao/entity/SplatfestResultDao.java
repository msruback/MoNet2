package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.data.entity.SplatfestResultRoom;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SplatfestResultDao {

    void insertSplatfestResult(SplatfestResult splatfestResult, SplatfestDao splatfestDao, Splatfest splatfest){
        try{
            insert(new SplatfestResultRoom(splatfestResult));
            splatfestDao.update(new SplatfestRoom(splatfest));
        }catch(SQLiteConstraintException e) {
        }
    }

    @Insert
    abstract void insert(SplatfestResultRoom... splatfestResult);

    @Update
    abstract void update(SplatfestResultRoom... splatfestResult);

    @Delete
    abstract void delete(SplatfestResultRoom... splatfestResult);

    @Query("SELECT * FROM splatfest_result WHERE id=:id")
    abstract LiveData<SplatfestResultRoom> select(int id);
}
