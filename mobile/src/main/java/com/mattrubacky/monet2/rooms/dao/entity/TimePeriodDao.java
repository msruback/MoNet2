package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

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
    void insert(TimePeriodRoom... timePeriod);

    @Update
    void update(TimePeriodRoom... timePeriod);

    @Delete
    void delete(TimePeriodRoom... timePeriod);

    @Query("SELECT * FROM time_period WHERE id<100")
    LiveData<List<TimePeriodRoom>> selectRegular();

    @Query("SELECT * FROM time_period WHERE id>100 AND id<200")
    LiveData<List<TimePeriodRoom>> selectGachi();

    @Query("SELECT * FROM time_period WHERE id>200 AND id<300")
    LiveData<List<TimePeriodRoom>> selectLeague();

    @Query("SELECT * FROM time_period WHERE id>300")
    LiveData<List<TimePeriodRoom>> selectFestival();

    @Query("SELECT * FROM time_period WHERE end_time>:now")
    List<TimePeriodRoom> selectOld(long now);
}
