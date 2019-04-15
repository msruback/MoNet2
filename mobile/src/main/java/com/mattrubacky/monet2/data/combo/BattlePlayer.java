package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.entity.BattleRoom;

public class BattlePlayer {
    BattleRoom battle;
    Stage stage;
    PlayerWeapon player;
    public Battle toDeserialized(){
        Battle battle = this.battle.toDeserialized();
        battle.stage = stage;
        battle.user = player.toDeserialized();
        return battle;
    }
}
