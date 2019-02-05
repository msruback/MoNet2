package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.WeaponRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeaponDao {
    @Insert
    void insert(WeaponRoom... weapon);

    @Update
    void update(WeaponRoom... weapon);

    @Delete
    void delete(WeaponRoom... weapon);

    @Query("SELECT * FROM weapon")
    List<WeaponRoom> selectAll();

    @Query("SELECT * FROM weapon WHERE id=:id")
    WeaponRoom select(int id);

    @Query("SELECT * FROM weapon WHERE special=:special")
    List<WeaponRoom> selectFromSpecial(int special);

    @Query("SELECT * FROM weapon WHERE sub=:sub")
    List<WeaponRoom> selectFromSub(int sub);

    @Query("SELECT id FROM weapon WHERE special=:special")
    List<Integer> selectIdFromSpecial(int special);

    @Query("SELECT id FROM weapon WHERE sub=:sub")
    List<Integer> selectIdFromSub(int sub);
}
