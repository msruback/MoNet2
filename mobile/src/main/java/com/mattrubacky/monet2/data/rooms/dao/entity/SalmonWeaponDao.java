package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.ShiftWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class SalmonWeaponDao {

    void insertSalmonWeapon(SalmonRunWeapon weapon,WeaponDao weaponDao){
        if(weapon.weapon!=null) {
            weaponDao.insertWeapon(weapon.weapon);
        }
        if(weapon.id<0){
            weapon.isMystery=true;
            if(weapon.id==-2){
                weapon.isGold=true;
            }else{
                weapon.isGold=false;
            }
        }else{
            weapon.isMystery=false;
            weapon.isGold=false;
        }
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
    public abstract SalmonRunWeapon select(Integer id);

    @Query("SELECT * FROM salmon_weapons WHERE weapon_shift_id=:id")
    public abstract List<SalmonRunWeapon> selectShift(int id);

    @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.salmon_weapon_id = weapon_id WHERE salmon_weapons.weapon_shift_id IN(:ids)")
    public abstract LiveData<List<ShiftWeapon>> selectFromShift(List<Integer> ids);

    @Query("SELECT * FROM salmon_weapons INNER JOIN weapon ON salmon_weapons.salmon_weapon_id = weapon_id JOIN shift ON weapon_shift_id = shift_id WHERE end_time>:now")
    public abstract LiveData<List<ShiftWeapon>> selectCurrent(long now);
}
