package com.mattrubacky.monet2.rooms.dao.pojo;

import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;
import com.mattrubacky.monet2.rooms.pojo.TimePeriodPojo;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TimePeriodPojoDao {
    @Query("SELECT * from time_period")
    public List<TimePeriodPojo> getTimePeriodsWithStages();

    @Query("SELECT * FROM time_period WHERE id<100")
    public List<TimePeriodPojo> selectRegular();

    @Query("SELECT * FROM time_period WHERE id>100 AND id<200")
    List<TimePeriodPojo> selectGachi();

    @Query("SELECT * FROM time_period WHERE id>200 AND id<300")
    List<TimePeriodPojo> selectLeague();

    @Query("SELECT * FROM time_period WHERE id>300")
    List<TimePeriodPojo> selectFestival();
}
