package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.RewardGearCombo;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class SalmonGearDao {

    public void insertGear(RewardGear rewardGear,GearDao gearDao,BrandDao brandDao,SkillDao skillDao){
        gearDao.insertGear(rewardGear.gear,true,brandDao,skillDao);

        rewardGear.gear.generatedId = Gear.generateId(rewardGear.gear.kind,rewardGear.gear.id);
        rewardGear.month = RewardGear.generateId(rewardGear.available);
        try{
            insert(rewardGear);
        }catch(SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(RewardGear... salmonGear);

    @Update
    abstract void update(RewardGear... salmonGear);

    @Delete
    abstract void delete(RewardGear... salmonGear);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM salmon_gear JOIN gear ON monthly_gear=gear_id JOIN brand ON gear_brand=brand_id WHERE month=:currentMonth")
    public abstract LiveData<RewardGearCombo> selectCurrentGear(int currentMonth);

    @Query("SELECT * FROM salmon_gear")
    public abstract List<RewardGear> selectAll();
}
