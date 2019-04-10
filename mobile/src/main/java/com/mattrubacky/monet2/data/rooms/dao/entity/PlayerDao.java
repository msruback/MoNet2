package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.rooms.combo.BattlePlayer;
import com.mattrubacky.monet2.data.rooms.combo.PlayerWeapon;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class PlayerDao {

    void insertPlayer(PlayerRoom player,WeaponDao weaponDao,SubDao subDao,SpecialDao specialDao,GearDao gearDao,BrandDao brandDao,SkillDao skillDao){

        weaponDao.insertWeapon(player.weapon,subDao,specialDao);

        gearDao.insertGear(player.head,false,brandDao,skillDao);
        skillDao.insertSkill(player.headMain);
        skillDao.insertSkill(player.headSub1);
        skillDao.insertSkill(player.headSub2);
        skillDao.insertSkill(player.headSub3);

        gearDao.insertGear(player.clothes,false,brandDao,skillDao);
        skillDao.insertSkill(player.clothesMain);
        skillDao.insertSkill(player.clothesSub1);
        skillDao.insertSkill(player.clothesSub2);
        skillDao.insertSkill(player.clothesSub3);

        gearDao.insertGear(player.shoes,false,brandDao,skillDao);
        skillDao.insertSkill(player.shoeMain);
        skillDao.insertSkill(player.shoeSub1);
        skillDao.insertSkill(player.shoeSub2);
        skillDao.insertSkill(player.shoeSub3);
        try{
            insert(player);
        }catch(SQLiteConstraintException e){
        }
    }

    @Insert
    abstract void insert(PlayerRoom... player);

    @Update
    abstract void update(PlayerRoom... player);

    @Delete
    abstract void delete(PlayerRoom... player);

    @Query("SELECT * FROM player WHERE battleId=:id AND playerType = 0")
    abstract PlayerRoom selectPlayerFromBattle(int id);

    @Query("SELECT * FROM player WHERE battleId=:id AND playerType=:type")
    abstract List<PlayerRoom> selectPlayersFromBattle(int id,int type);

    @Query("SELECT * FROM player JOIN weapon ON weapon = weapon_id WHERE battleId=:id")
    abstract List<PlayerWeapon> selectPlayerBattles(int id);

    @Query("SELECT * FROM player JOIN battle ON battleId = id WHERE fes_id =:fesId AND playerType=:type")
    abstract List<PlayerRoom> getSplatStats(int fesId,int type);

    @Query("SELECT * FROM player WHERE battleType =:battleType AND playerType =:playerType")
    abstract List<PlayerRoom> getStats(String battleType, int playerType);

}
