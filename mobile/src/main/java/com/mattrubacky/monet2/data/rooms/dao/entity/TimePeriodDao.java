package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;

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
        timePeriod.id = TimePeriod.generateId(timePeriod.start,timePeriod.mode.key);
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

    @Query("SELECT * FROM time_period WHERE time_period_id=:id")
    public abstract LiveData<TimePeriod> select(long id);

    @Query("SELECT * FROM time_period WHERE time_period_mode='regular' AND end_time>:now")
    public abstract LiveData<List<TimePeriod>> selectRegularLive(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='regular' AND end_time>:now")
    public abstract List<TimePeriod> selectRegular(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='gachi' AND end_time>:now")
    public abstract LiveData<List<TimePeriod>> selectGachiLive(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='gachi' AND end_time>:now")
    public abstract List<TimePeriod> selectGachi(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='league' AND end_time>:now")
    public abstract LiveData<List<TimePeriod>> selectLeagueLive(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='league' AND end_time>:now")
    public abstract List<TimePeriod> selectLeague(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='fes' AND end_time>:now")
    public abstract LiveData<List<TimePeriod>> selectFestivalLive(long now);

    @Query("SELECT * FROM time_period WHERE time_period_mode='fes' AND end_time>:now")
    public abstract List<TimePeriod> selectFestival(long now);

    @Query("SELECT * FROM time_period WHERE end_time<:now")
    public abstract List<TimePeriod> selectOld(long now);

    @Query("SELECT COUNT(time_period_id) FROM time_period")
    public abstract Integer count();
}
