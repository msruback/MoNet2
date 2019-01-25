package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import java.util.List;

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
    List<TimePeriodRoom> selectRegular();

    @Query("SELECT * FROM time_period WHERE id>100 AND id<200")
    List<TimePeriodRoom> selectGachi();

    @Query("SELECT * FROM time_period WHERE id>200 AND id<300")
    List<TimePeriodRoom> selectLeague();

    @Query("SELECT * FROM time_period WHERE id>300")
    List<TimePeriodRoom> selectFestival();

    @Query("SELECT a FROM time_period WHERE end_time>:now")
    List<TimePeriodRoom> selectOld(long now);
}
