package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.entity.PlayerRoom;

import java.util.List;

import androidx.room.Relation;

public class ShoeCloset extends ClosetStatCombo{
    @Relation(parentColumn = "gear_id",entityColumn="shoes")
    public List<PlayerRoom> players;

    @Override
    public List<PlayerRoom> getPlayers(){
        return players;
    }
}
