package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.combo.BattlePlayer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class BattleDao {

    void insertBattle(Battle battle){
        try{
            insert(battle);
        }catch(SQLiteConstraintException e){

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
