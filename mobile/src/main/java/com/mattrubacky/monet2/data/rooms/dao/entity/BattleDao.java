package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.combo.BattlePlayer;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.entity.BattleRoom;
import com.mattrubacky.monet2.data.entity.DayRoom;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

@Dao
public abstract class BattleDao {

    void insertBattle(Battle battle, DayDao dayDao, StageDao stageDao, PlayerDao playerDao, ClosetDao closetDao, WeaponDao weaponDao, SubDao subDao,
                      SpecialDao specialDao, GearDao gearDao, BrandDao brandDao, SkillDao skillDao){
        try{
            dayDao.insertDayRoom(battle.start);
            stageDao.insertStage(battle.stage);
            insert(new BattleRoom(battle));
            playerDao.insertPlayer(battle.id,battle.user,0,0,battle.result.key,battle.type, closetDao,weaponDao,subDao,specialDao, gearDao,brandDao,skillDao);
            for(int i=0;i<battle.myTeam.size();i++){
                playerDao.insertPlayer(battle.id,battle.myTeam.get(i),1,i,battle.result.key,battle.type,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
            for(int i=0;i<battle.otherTeam.size();i++){
                playerDao.insertPlayer(battle.id,battle.otherTeam.get(i),2,i,battle.result.key,battle.type,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
        }catch(SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(BattleRoom... battle);

    @Update
    abstract void update(BattleRoom... battle);

    @Delete
    abstract void delete(BattleRoom... battle);

    @Query("SELECT * FROM battle JOIN player ON battleId = id JOIN weapon ON weapon = weapon_id JOIN sub ON weapon_sub = sub_id JOIN special ON weapon_special = special_id JOIN stage ON stage = stage_id WHERE playerType = 0 AND id =:id")
    abstract LiveData<BattlePlayer> select(int id);

    @Query("SELECT * FROM battle JOIN player ON battleId = id JOIN weapon ON weapon = weapon_id JOIN sub ON weapon_sub = sub_id JOIN special ON weapon_special = special_id JOIN stage ON stage = stage_id WHERE playerType = 0")
    abstract LiveData<List<BattlePlayer>> selectAll();

    @Query("SELECT * FROM battle JOIN player ON battleId = id JOIN weapon ON weapon = weapon_id JOIN sub ON weapon_sub = sub_id JOIN special ON weapon_special = special_id JOIN stage ON stage = stage_id JOIN splatfest ON fes_id = splatfest_id WHERE playerType = 0 AND start_time>=:day AND start_time<:end")
    abstract LiveData<List<BattlePlayer>> selectDay(long day,long end);
}
