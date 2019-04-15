package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.combo.ClothesCloset;
import com.mattrubacky.monet2.data.combo.HeadCloset;
import com.mattrubacky.monet2.data.combo.ShoeCloset;
import com.mattrubacky.monet2.data.entity.ClosetRoom;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class ClosetDao {

    void insertGear(Gear gear, GearSkills skills){
        try{
            insert(new ClosetRoom(gear,skills));
        }catch(SQLiteConstraintException e){
            update(new ClosetRoom(gear,skills));
        }
    }

    @Insert
    abstract void insert(ClosetRoom... closet);

    @Update
    abstract void update(ClosetRoom... closet);

    @Delete
    abstract void delete(ClosetRoom... closet);

    //Note, I am supressing warnings here because closet_gear isn't in the ClosetCombos

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM closet JOIN gear ON closet_gear = gear_id WHERE kind='head' AND gear_id=:id")
    abstract HeadCloset selectHeadStats(int id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM closet JOIN gear ON closet_gear = gear_id WHERE kind='clothes' AND gear_id=:id")
    abstract ClothesCloset selectClothesStats(int id);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM closet JOIN gear ON closet_gear = gear_id WHERE kind='shoe' AND gear_id=:id")
    abstract ShoeCloset selectShoeStats(int id);
}
