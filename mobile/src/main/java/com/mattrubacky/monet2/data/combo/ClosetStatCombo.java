package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.entity.PlayerRoom;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public abstract class ClosetStatCombo {
    @Embedded
    public Gear gear;

    public Skill main;
    @ColumnInfo(name="fr_sub")
    public Skill sub1;
    @ColumnInfo(name="sc_sub")
    public Skill sub2;
    @ColumnInfo(name="tr_sub")
    public Skill sub3;

    public GearSkills getSkills(){
        return new GearSkills(main,sub1,sub2,sub3);
    }
    public Gear getGear(){
        return gear;
    }
    public abstract List<PlayerRoom> getPlayers();
}
