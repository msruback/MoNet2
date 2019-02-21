package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.rooms.entity.SalmonGearRoom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SalmonGearDao {
    @Insert
    void insert(SalmonGearRoom... salmonGear);

    @Update
    void update(SalmonGearRoom... salmonGear);

    @Delete
    void delete(SalmonGearRoom... salmonGear);
    
    @Query("SELECT * FROM salmon_gear WHERE month=:currentMonth")
    LiveData<SalmonGearRoom> selectCurrentGear(long currentMonth);
}
