package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.combo.BattlePlayer;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class BattleDao {

    void insertBattle(Battle battle, PlayerDao playerDao, ClosetDao closetDao, WeaponDao weaponDao, SubDao subDao,
                      SpecialDao specialDao, GearDao gearDao, BrandDao brandDao, SkillDao skillDao){
        try{
            insert(battle);
            playerDao.insertPlayer(battle.id,battle.user,0,0,battle.result.key,battle.type, closetDao,weaponDao,subDao,specialDao, gearDao,brandDao,skillDao);
            for(int i=0;i<battle.myTeam.size();i++){
                playerDao.insertPlayer(battle.id,battle.myTeam.get(i),1,i,battle.result.key,battle.type,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
            for(int i=0;i<battle.otherTeam.size();i++){
                playerDao.insertPlayer(battle.id,battle.otherTeam.get(i),2,i,battle.result.key,battle.type,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
        }catch(SQLiteConstraintException ignored){

        }
    }

    @Insert
    abstract void insert(Battle... battle);

    @Update
    abstract void update(Battle... battle);

    @Delete
    abstract void delete(Battle... battle);

    @Query("SELECT * FROM battle JOIN player ON battleId = id JOIN weapon ON weapon = weapon_id JOIN stage ON stage = stage_id WHERE playerType = 0")
    abstract List<BattlePlayer> selectAll();
    
}
