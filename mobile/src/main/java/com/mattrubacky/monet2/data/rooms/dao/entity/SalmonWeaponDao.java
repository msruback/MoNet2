package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class SalmonWeaponDao {

    void insertSalmonWeapon(SalmonRunWeapon weapon,WeaponDao weaponDao){
        weaponDao.insert(weapon.weapon);
        try{
            insert(weapon);
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(SalmonRunWeapon... weapon);

    @Update
    abstract void update(SalmonRunWeapon... weapon);

    @Delete
    abstract void delete(SalmonRunWeapon... weapon);

    @Query("SELECT * FROM salmon_weapons WHERE salmon_weapon_id=:id")
    abstract SalmonRunWeapon select(int id);

    @Query("SELECT * FROM salmon_weapons WHERE weapon_shift_id=:id")
    abstract List<SalmonRunWeapon> selectShift(int id);
}
