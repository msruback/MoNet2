package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SalmonGearDao {

    void insertGear(RewardGear rewardGear,GearDao gearDao,BrandDao brandDao,SkillDao skillDao){
        try{
            gearDao.insertGear(rewardGear.gear,true,brandDao,skillDao);
            rewardGear.month = RewardGear.generateId(rewardGear.available);
            insert(rewardGear);
        }catch(SQLiteConstraintException e){
        }
    }

    @Insert
    abstract void insert(RewardGear... salmonGear);

    @Update
    abstract void update(RewardGear... salmonGear);

    @Delete
    abstract void delete(RewardGear... salmonGear);
    
    @Query("SELECT * FROM salmon_gear WHERE month=:currentMonth")
    public abstract LiveData<RewardGear> selectCurrentGear(long currentMonth);
}
