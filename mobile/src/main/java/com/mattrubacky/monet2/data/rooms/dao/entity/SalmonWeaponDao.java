package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public abstract class SalmonWeaponDao {

    void insertSalmonWeapon(SalmonRunWeapon weapon,WeaponDao weaponDao){
        weaponDao.insert(weapon.weapon);
        try{
            insert(weapon);
        }catch (SQLiteConstraintException e){
        }
    }

    @Insert
    abstract void insert(SalmonRunWeapon... weapon);

    @Update
    abstract void update(SalmonRunWeapon... weapon);

    @Delete
    abstract void delete(SalmonRunWeapon... weapon);
}
