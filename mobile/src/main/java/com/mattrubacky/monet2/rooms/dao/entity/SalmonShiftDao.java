package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SalmonShiftRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SalmonShiftDao {
    @Insert
    void insert(SalmonShiftRoom... shift);

    @Update
    void update(SalmonShiftRoom... shift);

    @Delete
    void delete(SalmonShiftRoom... shift);

    @Query("SELECT * FROM shift")
    LiveData<List<SalmonShiftRoom>> selectAll();
}
