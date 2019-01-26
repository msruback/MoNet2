package com.mattrubacky.monet2.rooms.dao.pojo;


import com.mattrubacky.monet2.rooms.pojo.TimePeriodPojo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TimePeriodPojoDao {
    @Query("SELECT * from time_period")
    List<TimePeriodPojo> getTimePeriodsWithStages();

    @Query("SELECT * FROM time_period WHERE id<100")
    LiveData<List<TimePeriodPojo>> selectRegular();

    @Query("SELECT * FROM time_period WHERE id>100 AND id<200")
    LiveData<List<TimePeriodPojo>> selectGachi();

    @Query("SELECT * FROM time_period WHERE id>200 AND id<300")
    LiveData<List<TimePeriodPojo>> selectLeague();

    @Query("SELECT * FROM time_period WHERE id>300")
    LiveData<List<TimePeriodPojo>> selectFestival();
}
