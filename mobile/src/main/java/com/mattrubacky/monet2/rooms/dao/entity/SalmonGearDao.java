package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SalmonGearRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SalmonGearDao {
    @Insert
    void insert(SalmonGearRoom... salmonGear);

    @Update
    void update(SalmonGearRoom... salmonGear);

    @Delete
    void delete(SalmonGearRoom... salmonGear);
}
