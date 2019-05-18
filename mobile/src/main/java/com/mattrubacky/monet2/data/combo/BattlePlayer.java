package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.entity.BattleRoom;

import androidx.room.Embedded;

public class BattlePlayer {
    @Embedded
    public BattleRoom battle;
    @Embedded
    public Stage stage;
    @Embedded
    public PlayerWeapon player;
    public Battle toDeserialized(){
        Battle battle = this.battle.toDeserialized();
        battle.stage = stage;
        battle.user = player.toDeserialized();
        return battle;
    }
}
