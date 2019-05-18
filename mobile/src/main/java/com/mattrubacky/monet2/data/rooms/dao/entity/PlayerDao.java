package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.PlayerWeapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.entity.PlayerRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class PlayerDao {

    void insertPlayer(int id, Player player, int playerType, int position, String result, String battleType, ClosetDao closetDao, WeaponDao weaponDao, SubDao subDao, SpecialDao specialDao, GearDao gearDao, BrandDao brandDao, SkillDao skillDao){

        weaponDao.insertWeapon(player.user.weapon,subDao,specialDao);

        gearDao.insertGear(player.user.head,false,brandDao,skillDao);
        skillDao.insertSkill(player.user.headSkills.main);
        for(Skill skill: player.user.headSkills.subs){
                skillDao.insertSkill(skill);
        }

        gearDao.insertGear(player.user.clothes,false,brandDao,skillDao);
        skillDao.insertSkill(player.user.clothesSkills.main);
        for(Skill skill: player.user.clothesSkills.subs){
            skillDao.insertSkill(skill);
        }

        gearDao.insertGear(player.user.shoes,false,brandDao,skillDao);
        skillDao.insertSkill(player.user.shoeSkills.main);
        for(Skill skill: player.user.shoeSkills.subs){
            skillDao.insertSkill(skill);
        }
        try{
            PlayerRoom playerRoom = new PlayerRoom(id,player,playerType,result,battleType);
            playerRoom.generatedPlayerId = PlayerRoom.generateID(id,playerType,position);
            insert(playerRoom);
            if(playerType==0){
                closetDao.insertGear(player.user.head,player.user.headSkills);
                closetDao.insertGear(player.user.clothes,player.user.clothesSkills);
                closetDao.insertGear(player.user.shoes,player.user.shoeSkills);
            }
        }catch(SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(PlayerRoom... player);

    @Update
    abstract void update(PlayerRoom... player);

    @Delete
    abstract void delete(PlayerRoom... player);

    @Query("SELECT * FROM player JOIN weapon ON weapon = weapon_id JOIN sub ON weapon_sub = sub_id JOIN special ON weapon_special = special_id WHERE battleId=:id AND playerType = 0")
    public abstract LiveData<PlayerWeapon> selectPlayerFromBattle(int id);

    @Query("SELECT * FROM player JOIN weapon ON weapon = weapon_id JOIN sub ON weapon_sub = sub_id JOIN special ON weapon_special = special_id WHERE battleId=:id AND playerType=:type")
    public abstract LiveData<List<PlayerWeapon>> selectPlayersFromBattle(int id, int type);

    @Query("SELECT * FROM player JOIN weapon ON weapon = weapon_id WHERE battleId=:id")
    public abstract List<PlayerWeapon> selectWeaponBattles(int id);

    @Query("SELECT * FROM player JOIN battle ON battleId = id WHERE fes_id =:fesId AND playerType=:type")
    public abstract List<PlayerRoom> getSplatStats(int fesId,int type);

    @Query("SELECT * FROM player WHERE battleType =:battleType AND playerType =:playerType")
    public abstract List<PlayerRoom> getStats(String battleType, int playerType);

}
