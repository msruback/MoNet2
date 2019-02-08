package com.mattrubacky.monet2.rooms.dao.entity;

import com.mattrubacky.monet2.rooms.entity.ClosetRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ClosetDao {
    @Insert
    void insert(ClosetRoom... closet);

    @Update
    void update(ClosetRoom... closet);

    @Delete
    void delete(ClosetRoom... closet);
}
