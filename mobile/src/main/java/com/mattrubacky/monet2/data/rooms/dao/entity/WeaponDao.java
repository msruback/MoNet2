package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.data.combo.WeaponCombo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class WeaponDao {

    public void insertWeapon(Weapon weapon, SubDao subDao, SpecialDao specialDao){
        subDao.insertSub(weapon.sub);
        specialDao.insertSpecial(weapon.special);
        try{
            insert(weapon);
        }catch(SQLiteConstraintException e){
            update(weapon);
        }
    }

    public void insertWeapon(Weapon weapon){
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
    public abstract LiveData<List<Weapon>> selectAll();

    @Query("SELECT * FROM weapon JOIN special ON weapon_special = special_id JOIN sub ON weapon_sub = sub_id WHERE weapon_id=:id")
    public abstract LiveData<WeaponCombo> select(int id);

    @Query("SELECT * FROM weapon JOIN special ON weapon_special = special_id JOIN sub ON weapon_sub = sub_id WHERE weapon_special=:special")
    public abstract LiveData<List<WeaponCombo>> selectFromSpecial(int special);

    @Query("SELECT * FROM weapon JOIN special ON weapon_special = special_id JOIN sub ON weapon_sub = sub_id WHERE weapon_sub=:sub")
    public abstract LiveData<List<WeaponCombo>> selectFromSub(int sub);

    @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.salmon_weapon_id = weapon_id WHERE salmon_weapons.weapon_shift_id IN(:ids)")
    public abstract LiveData<List<Weapon>> selectFromShift(List<Integer> ids);

    @Query("SELECT * FROM weapon JOIN sub ON sub_id = weapon_sub JOIN special ON special_id = weapon_special WHERE weapon_id = :id")
    public abstract LiveData<WeaponCombo> selectCombo(int id);
}
