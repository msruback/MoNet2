package com.mattrubacky.monet2.data.rooms.dao.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface BattleDao {
    @Insert
    void insert(Battle... battle);

    @Update
    void update(Battle... battle);

    @Delete
    void delete(Battle... battle);

}
