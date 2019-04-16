package com.mattrubacky.monet2.data.entity;

import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "day_battle",
        primaryKeys = {
            "day_battle_id","day_id"
        },
        foreignKeys = {
                @ForeignKey(entity = BattleRoom.class,
                        parentColumns = "id",
                        childColumns = "day_battle_id"),
                @ForeignKey(entity = SalmonStage.class,
                        parentColumns = "day",
                        childColumns = "day_id")
        })
public class DayBattleRoom {

    public DayBattleRoom(int battleID,long dayId){
        this.battleID = battleID;
        this.dayId = dayId;
    }

    @ColumnInfo(name="day_battle_id")
    public int battleID;

    @ColumnInfo(name="day_id")
    public long dayId;
}
