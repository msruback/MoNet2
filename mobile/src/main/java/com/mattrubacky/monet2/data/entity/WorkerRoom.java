package com.mattrubacky.monet2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.data.deserialized.splatoon.BossCount;
import com.mattrubacky.monet2.data.deserialized.splatoon.Worker;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.Special;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;

import java.util.ArrayList;

@Entity(tableName = "worker",
        foreignKeys = {
                @ForeignKey(entity = Weapon.class,
                        parentColumns = "weapon_id",
                        childColumns = "weapon_wave_1"),
                @ForeignKey(entity = Weapon.class,
                        parentColumns = "weapon_id",
                        childColumns = "weapon_wave_2"),
                @ForeignKey(entity = Weapon.class,
                        parentColumns = "weapon_id",
                        childColumns = "weapon_wave_3"),
                @ForeignKey(entity = Special.class,
                        parentColumns = "special_id",
                        childColumns = "worker_special")
        },
        indices = {
                @Index(name="worker_weap_1",
                        value = "weapon_wave_1"),
                @Index(name="worker_weap_2",
                        value = "weapon_wave_2"),
                @Index(name="worker_weap_3",
                        value = "weapon_wave_3"),
                @Index(name="worker_special",
                        value = "worker_special")
        })
public class WorkerRoom {

    public WorkerRoom(int genId, String id, Integer job, Integer type, String name, Integer powerEggs, Integer goldenEggs, Integer deadCount, Integer helpCount,
                      Special special,Integer wave1Special, Integer wave2Special, Integer wave3Special, Weapon wave1Weapon, Weapon wave2Weapon, Weapon wave3Weapon,
                      BossCount bossKillses){
        this.genId = genId;
        this.id = id;
        this.job = job;
        this.type = type;
        this.name = name;
        this.powerEggs = powerEggs;
        this.goldenEggs = goldenEggs;
        this.deadCount = deadCount;
        this.deadCount = deadCount;
        this.helpCount = helpCount;
        this.special = special;
        this.wave1Special = wave1Special;
        this.wave2Special = wave2Special;
        this.wave3Special = wave3Special;
        this.wave1Weapon = wave1Weapon;
        this.wave2Weapon = wave2Weapon;
        this.wave3Weapon = wave3Weapon;
        this.bossKillses = bossKillses;
    }

    @Ignore
    public WorkerRoom(Worker worker){
        id = worker.id;
        job = worker.job;
        type = worker.type;
        name = worker.name;
        powerEggs = worker.powerEggs;
        goldenEggs = worker.goldenEggs;
        deadCount = worker.deadCount;
        helpCount = worker.helpCount;
        special = worker.special;
        bossKillses = worker.bossKillses;
        if(worker.specialCounts.size()>0){
            wave1Special = worker.specialCounts.get(0);
            if(worker.specialCounts.size()>1){
                wave2Special = worker.specialCounts.get(1);
                if(worker.specialCounts.size()>2){
                    wave3Special = worker.specialCounts.get(2);
                }
            }
        }
        if(worker.weapons.size()>0){
            wave1Weapon = worker.weapons.get(0).weapon;
            if(worker.weapons.size()>1){
                wave2Weapon = worker.weapons.get(1).weapon;
                if(worker.weapons.size()>2){
                    wave3Weapon = worker.weapons.get(2).weapon;
                }
            }
        }
    }

    @PrimaryKey
    @ColumnInfo(name="worker_id")
    public int genId;

    @ColumnInfo(name="worker_pid")
    public String id;

    @ColumnInfo(name="worker_job")
    public Integer job;

    @ColumnInfo(name="worker_type")
    public Integer type;

    @ColumnInfo(name="worker_name")
    public String name;

    @ColumnInfo(name="worker_power_eggs")
    public Integer powerEggs;

    @ColumnInfo(name="worker_golden_eggs")
    public Integer goldenEggs;

    @ColumnInfo(name="worker_deaths")
    public Integer deadCount;

    @ColumnInfo(name="worker_helps")
    public Integer helpCount;

    @ColumnInfo(name="worker_special")
    public Special special;

    @ColumnInfo(name="worker_special_wave_1")
    public Integer wave1Special;

    @ColumnInfo(name="worker_special_wave_2")
    public Integer wave2Special;

    @ColumnInfo(name="worker_special_wave_3")
    public Integer wave3Special;

    @ColumnInfo(name="worker_weapon_wave_1")
    public Weapon wave1Weapon;

    @ColumnInfo(name="worker_weapon_wave_2")
    public Weapon wave2Weapon;

    @ColumnInfo(name="worker_weapon_wave_3")
    public Weapon wave3Weapon;

    @Embedded(prefix = "worker_")
    @SerializedName("boss_kill_counts")
    public BossCount bossKillses;

    public Worker toDeserialized(){
        Worker worker = new Worker();
        worker.id = id;
        worker.job = job;
        worker.type = type;
        worker.name = name;
        worker.powerEggs = powerEggs;
        worker.goldenEggs = goldenEggs;
        worker.deadCount = deadCount;
        worker.deadCount = deadCount;
        worker.helpCount = helpCount;
        worker.special = special;

        worker.specialCounts = new ArrayList<>();
        worker.specialCounts.add(wave1Special);
        worker.specialCounts.add(wave2Special);
        worker.specialCounts.add(wave3Special);

        worker.weapons = new ArrayList<>();
        worker.weapons.add(new SalmonRunWeapon(wave1Weapon));
        worker.weapons.add(new SalmonRunWeapon(wave2Weapon));
        worker.weapons.add(new SalmonRunWeapon(wave3Weapon));
        return worker;
    }
}
