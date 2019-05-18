package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.mattrubacky.monet2.data.deserialized.splatoon.Worker;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.data.entity.WorkerRoom;

@Dao
public abstract class WorkerDao {

    public void insertWorker(Worker worker,WeaponDao weaponDao){
        for(SalmonRunWeapon weapon:worker.weapons){
            weaponDao.insertWeapon(weapon.weapon);
        }
        try{
            insert(new WorkerRoom(worker));
        }catch(SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    @Insert
    abstract void insert(WorkerRoom... workerRoom);

    @Update
    abstract void update(WorkerRoom... workerRoom);

    @Delete
    abstract void delete(WorkerRoom... workerRooms);
}
