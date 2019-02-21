package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface PlayerDao {
    @Insert
    void insert(PlayerRoom... player);

    @Update
    void update(PlayerRoom... player);

    @Delete
    void delete(PlayerRoom... player);
}
