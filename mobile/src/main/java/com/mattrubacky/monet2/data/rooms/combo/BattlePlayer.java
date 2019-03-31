package com.mattrubacky.monet2.data.rooms.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.rooms.entity.BattleRoom;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

import java.util.List;

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
