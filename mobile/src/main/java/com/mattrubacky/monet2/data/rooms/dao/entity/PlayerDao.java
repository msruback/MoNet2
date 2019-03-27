package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class PlayerDao {

    void insertPlayer(PlayerRoom player){
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

    @Query("SELECT * FROM player WHERE battleId=:id")
    abstract void selectPlayerBattles(int id);
}
