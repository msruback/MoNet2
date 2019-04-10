package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class TimePeriodDao {

    public void insertTimePeriod(TimePeriod timePeriod,StageDao stageDao){
        stageDao.insertStage(timePeriod.a);
        stageDao.insertStage(timePeriod.b);
        try{
            insert(timePeriod);
        }catch (SQLiteConstraintException e){
        }
    }

    @Insert
    protected abstract void insert(TimePeriod... timePeriod);

    @Update
    protected abstract void update(TimePeriod... timePeriod);

    @Delete
    public abstract void delete(TimePeriod... timePeriod);

    @Query("SELECT * FROM time_period WHERE id=:id")
    public abstract TimePeriod select(long id);

    @Query("SELECT * FROM time_period WHERE mode='regular'")
    public abstract LiveData<List<TimePeriod>> selectRegularLive();

    @Query("SELECT * FROM time_period WHERE mode='regular'")
    public abstract List<TimePeriod> selectRegular();

    @Query("SELECT * FROM time_period WHERE mode='gachi'")
    public abstract LiveData<List<TimePeriod>> selectGachiLive();

    @Query("SELECT * FROM time_period WHERE mode='gachi'")
    public abstract List<TimePeriod> selectGachi();

    @Query("SELECT * FROM time_period WHERE mode='league'")
    public abstract LiveData<List<TimePeriod>> selectLeagueLive();

    @Query("SELECT * FROM time_period WHERE mode='league'")
    public abstract List<TimePeriod> selectLeague();

    @Query("SELECT * FROM time_period WHERE mode='fes'")
    public abstract LiveData<List<TimePeriod>> selectFestivalLive();

    @Query("SELECT * FROM time_period WHERE mode='fes'")
    public abstract List<TimePeriod> selectFestival();

    @Query("SELECT * FROM time_period WHERE end_time>:now")
    public abstract List<TimePeriod> selectOld(long now);
}
