package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.SalmonWeaponRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SalmonWeaponDao {
    @Insert
    void insert(SalmonWeaponRoom... weaponRooms);

    @Update
    void update(SalmonWeaponRoom... weaponRooms);

    @Delete
    void delete(SalmonWeaponRoom... weaponRooms);
}
