package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.entity.PlayerRoom;

import java.util.List;

import androidx.room.Relation;

public class HeadCloset extends ClosetStatCombo {
    @Relation(parentColumn = "gear_id",entityColumn="head")
    public List<PlayerRoom> players;

    @Override
    public List<PlayerRoom> getPlayers(){
        return players;
    }
}
