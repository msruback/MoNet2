package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.data.combo.WeaponCombo;

import java.util.List;

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
    public abstract List<Weapon> selectAll();

    @Query("SELECT * FROM weapon WHERE weapon_id=:id")
    public abstract Weapon select(int id);

    @Query("SELECT * FROM weapon WHERE weapon_special=:special")
    public abstract List<Weapon> selectFromSpecial(int special);

    @Query("SELECT * FROM weapon WHERE weapon_sub=:sub")
    public abstract List<Weapon> selectFromSub(int sub);

   // @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.weapon_id = weapon_id WHERE salmon_weapons.shift_id=:shiftId")
   //public abstract List<Weapon> selectFromShift(int shiftId);

    @Query("SELECT * FROM weapon JOIN sub ON sub_id = weapon_sub JOIN special ON special_id = weapon_special WHERE weapon_id = :id")
    public abstract WeaponCombo selectCombo(int id);
}
