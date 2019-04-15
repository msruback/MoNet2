package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

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
        for(SalmonRunWeapon salmonRunWeapon:salmonRunDetail.weapons){
            salmonWeaponDao.insertSalmonWeapon(salmonRunWeapon,weaponDao);
        }
        try{
            insert(new SalmonShiftRoom(salmonRunDetail));
        }catch (SQLiteConstraintException ignored){
        }
    }

    @Insert
    protected abstract void insert(SalmonShiftRoom... shift);

    @Update
    protected abstract void update(SalmonShiftRoom... shift);

    @Delete
    protected abstract void delete(SalmonShiftRoom... shift);

    @Query("SELECT * FROM shift")
    public abstract LiveData<List<SalmonShiftRoom>> selectAll();

    @Query("SELECT * FROM shift WHERE end_time>:now")
    public abstract LiveData<List<SalmonShiftRoom>> selectUpcoming(long now);

    @Query("SELECT * FROM shift WHERE start_time<:now")
    public abstract LiveData<List<SalmonShiftRoom>> selectPast(long now);
}
