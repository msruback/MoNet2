package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class WeaponDao {

    void insertWeapon(Weapon weapon){
        try{
            insert(weapon);
        }catch(SQLiteConstraintException e){

        }
    }

    @Insert
    abstract void insert(Weapon... weapon);

    @Update
    abstract void update(Weapon... weapon);

    @Delete
    abstract void delete(Weapon... weapon);

    @Query("SELECT * FROM weapon")
    abstract List<Weapon> selectAll();

    @Query("SELECT * FROM weapon WHERE id=:id")
    abstract Weapon select(int id);

    @Query("SELECT * FROM weapon WHERE special=:special")
    abstract List<Weapon> selectFromSpecial(int special);

    @Query("SELECT * FROM weapon WHERE sub=:sub")
    abstract List<Weapon> selectFromSub(int sub);

    @Query("SELECT id FROM weapon WHERE special=:special")
    abstract List<Integer> selectIdFromSpecial(int special);

    @Query("SELECT id FROM weapon WHERE sub=:sub")
    abstract List<Integer> selectIdFromSub(int sub);

    @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.weapon_id = weapon.id WHERE salmon_weapons.shift_id=:shiftId")
    abstract List<Weapon> selectFromShift(int shiftId);

    @Query("SELECT * FROM weapon JOIN sub ON sub.id = weapon.sub JOIN special ON special.id = weapon.special WHERE weapon.id = :id")
    abstract Weapon selectTest(int id);
}
