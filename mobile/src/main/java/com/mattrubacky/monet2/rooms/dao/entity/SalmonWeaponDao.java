package com.mattrubacky.monet2.rooms.dao.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SalmonWeaponDao {
    @Insert
    void insert(SalmonStageDao... stage);

    @Update
    void update(SalmonStageDao... stage);

    @Delete
    void delete(SalmonStageDao... stage);
}
