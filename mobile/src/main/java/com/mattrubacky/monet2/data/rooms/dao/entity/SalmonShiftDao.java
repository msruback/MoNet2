package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.SalmonShiftCombo;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SalmonShiftDao {

    void insertSalmonShift(SalmonRunDetail salmonRunDetail,SalmonStageDao salmonStageDao,SalmonWeaponDao salmonWeaponDao,WeaponDao weaponDao){
        salmonStageDao.insertSalmonStage(salmonRunDetail.stage);
        try{
            insert(new SalmonShiftRoom(salmonRunDetail));
            for(SalmonRunWeapon salmonRunWeapon:salmonRunDetail.weapons){
                salmonRunWeapon.shiftId = SalmonShiftRoom.generateId(salmonRunDetail.start);
                salmonWeaponDao.insertSalmonWeapon(salmonRunWeapon,weaponDao);
            }
        }catch (SQLiteConstraintException ignored){
        }
    }

    void insertSalmonShift(SalmonRun salmonRun){
        try{
            insert(new SalmonShiftRoom(salmonRun));
        }catch (SQLiteConstraintException ignored){
        }
    }
    @Insert
    protected abstract void insert(SalmonShiftRoom... shift);

    @Update
    protected abstract void update(SalmonShiftRoom... shift);

    @Delete
    protected abstract void delete(SalmonShiftRoom... shift);

    @Query("SELECT * FROM shift LEFT JOIN salmon_stage ON  shift_stage = salmon_stage_id LEFT JOIN (SELECT * FROM salmon_weapons JOIN weapon ON salmon_weapon_id=weapon_id) ON shift_id=weapon_shift_id WHERE shift_id = :id")
    public abstract LiveData<SalmonShiftCombo> select(int id);

    @Query("SELECT * FROM shift")
    public abstract LiveData<List<SalmonShiftRoom>> selectAll();

    @Query("SELECT * FROM shift WHERE end_time>:now")
    public abstract LiveData<List<SalmonShiftRoom>> selectUpcoming(long now);

    @Query("SELECT * FROM shift WHERE start_time<:now")
    public abstract LiveData<List<SalmonShiftRoom>> selectPast(long now);
}
