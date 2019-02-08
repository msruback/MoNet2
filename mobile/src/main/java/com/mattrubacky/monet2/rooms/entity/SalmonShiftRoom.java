package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.deserialized.splatoon.Weapon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift",
        foreignKeys = {
                @ForeignKey(entity = SalmonStageRoom.class,
                            parentColumns = "id",
                            childColumns = "stage")
        })
public class SalmonShiftRoom {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "start_time")
    public long startTime;
    @ColumnInfo(name = "end_time")
    public long endTime;
    public int stage;

    public SalmonShiftRoom(int id, long startTime,long endTime,int stage){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
    }
    public SalmonShiftRoom(long startTime,long endTime,int stage){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startTime));
        id = cal.get(Calendar.YEAR)-2017;
        id *= 1000;
        id += cal.get(Calendar.DAY_OF_YEAR);
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
    }

    public SalmonRun toDeserialised(){
        SalmonRun salmonRun = new SalmonRun();
        salmonRun.start = startTime;
        salmonRun.end = endTime;
        return salmonRun;
    }

    public SalmonRunDetail toDeserialised(SalmonStageRoom salmonStageRoom, List<WeaponRoom> weaponRooms){
        SalmonRunDetail salmonRunDetail = new SalmonRunDetail();
        salmonRunDetail.start = startTime;
        salmonRunDetail.end = endTime;
        salmonRunDetail.stage =salmonStageRoom.toDeserialized();
        salmonRunDetail.weapons = new ArrayList<>();
        for(WeaponRoom weapon:weaponRooms){
            salmonRunDetail.weapons.add(weapon.toDeserialized());
        }
        return salmonRunDetail;
    }
}
