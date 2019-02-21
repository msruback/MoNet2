package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonStage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift",
        foreignKeys = {
                @ForeignKey(entity = SalmonStage.class,
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
    public SalmonStage stage;

    public SalmonShiftRoom(int id, long startTime,long endTime,SalmonStage stage){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stage = stage;
    }
    @Ignore
    public SalmonShiftRoom(SalmonRunDetail salmonRunDetail){
        this.id = generateId(salmonRunDetail.start);
        this.startTime = salmonRunDetail.start;
        this.endTime = salmonRunDetail.end;
        this.stage = salmonRunDetail.stage;

    }

    @Ignore
    public SalmonShiftRoom(SalmonRun salmonRun){
        this.id = generateId(salmonRun.start);
        this.startTime = salmonRun.start;
        this.endTime = salmonRun.end;
    }

    public SalmonRun toDeserialised(){
        SalmonRun salmonRun = new SalmonRun();
        salmonRun.start = startTime;
        salmonRun.end = endTime;
        return salmonRun;
    }

    public SalmonRunDetail toDeserialised(List<SalmonStage> salmonStages, List<Weapon> weapons){
        SalmonRunDetail salmonRunDetail = new SalmonRunDetail();
        salmonRunDetail.start = startTime;
        salmonRunDetail.end = endTime;
        for(SalmonStage salmonStage:salmonStages){
            if(salmonStage.id == stage.id){
                salmonRunDetail.stage = salmonStage;
            }
        }
        salmonRunDetail.weapons = new ArrayList<>();
        for(Weapon weapon:weapons){
            SalmonRunWeapon salmonRunWeapon = new SalmonRunWeapon();
            salmonRunWeapon.id = weapon.id;
            salmonRunWeapon.weapon = weapon;
            salmonRunDetail.weapons.add(salmonRunWeapon);
        }
        return salmonRunDetail;
    }

    public static int generateId(long startTime){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startTime));
        int id = cal.get(Calendar.YEAR)-2017;
        id *= 1000;
        id += cal.get(Calendar.DAY_OF_YEAR);
        return id;
    }
}
