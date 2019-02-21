package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimePeriodDao {
    @Insert
    void insert(TimePeriod... timePeriod);

    @Update
    void update(TimePeriod... timePeriod);

    @Delete
    void delete(TimePeriod... timePeriod);

    @Query("SELECT * FROM time_period WHERE mode=='regular'")
    LiveData<List<TimePeriod>> selectRegular();

    @Query("SELECT * FROM time_period WHERE mode=='gachi'")
    LiveData<List<TimePeriod>> selectGachi();

    @Query("SELECT * FROM time_period WHERE mode=='league'")
    LiveData<List<TimePeriod>> selectLeague();

    @Query("SELECT * FROM time_period WHERE mode=='fes'")
    LiveData<List<TimePeriod>> selectFestival();

    @Query("SELECT * FROM time_period WHERE end_time>:now")
    List<TimePeriod> selectOld(long now);
}
