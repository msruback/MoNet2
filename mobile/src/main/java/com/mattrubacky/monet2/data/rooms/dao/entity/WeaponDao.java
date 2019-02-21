package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeaponDao {
    @Insert
    void insert(Weapon... weapon);

    @Update
    void update(Weapon... weapon);

    @Delete
    void delete(Weapon... weapon);

    @Query("SELECT * FROM weapon")
    List<Weapon> selectAll();

    @Query("SELECT * FROM weapon WHERE id=:id")
    Weapon select(int id);

    @Query("SELECT * FROM weapon WHERE special=:special")
    List<Weapon> selectFromSpecial(int special);

    @Query("SELECT * FROM weapon WHERE sub=:sub")
    List<Weapon> selectFromSub(int sub);

    @Query("SELECT id FROM weapon WHERE special=:special")
    List<Integer> selectIdFromSpecial(int special);

    @Query("SELECT id FROM weapon WHERE sub=:sub")
    List<Integer> selectIdFromSub(int sub);

    @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.weapon_id = weapon.id WHERE salmon_weapons.shift_id=:shiftId")
    List<Weapon> selectFromShift(int shiftId);
}
