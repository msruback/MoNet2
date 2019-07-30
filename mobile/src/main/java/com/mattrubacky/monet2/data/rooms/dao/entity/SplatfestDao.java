package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.SplatfestStageCombo;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SplatfestDao {

    public void insertSplatfest(Splatfest splatfest, StageDao stageDao){
        stageDao.insertStage(splatfest.stage);
        try{
            insert(new SplatfestRoom(splatfest));
        }catch(SQLiteConstraintException e) {
        }
    }

    @Insert
    abstract void insert(SplatfestRoom... splatfest);

    @Update
    abstract void update(SplatfestRoom... splatfest);

    @Delete
    abstract void delete(SplatfestRoom... splatfest);

    @Query("SELECT * FROM splatfest JOIN stage ON splatfest_stage = stage_id WHERE splatfest_id=:id")
    public abstract SplatfestStageCombo select(int id);

    @Query("SELECT * FROM splatfest JOIN stage ON splatfest_stage = stage_id WHERE end_time>:time")
    public abstract LiveData<SplatfestStageCombo> selectUpcomingLive(long time);

    @Query("SELECT * FROM splatfest JOIN stage ON splatfest_stage = stage_id WHERE end_time>:time")
    public abstract SplatfestStageCombo selectUpcoming(long time);

    @Query("SELECT * FROM splatfest JOIN stage ON splatfest_stage = stage_id")
    abstract LiveData<List<SplatfestStageCombo>> selectAll();
}
