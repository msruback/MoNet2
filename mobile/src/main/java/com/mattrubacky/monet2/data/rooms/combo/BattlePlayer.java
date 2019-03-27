package com.mattrubacky.monet2.data.rooms.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.rooms.entity.BattleRoom;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

import java.util.List;

public class BattlePlayer {
    BattleRoom battle;
    PlayerRoom player;
    Weapon weapon;
    public Battle toDeserialized(List<Stage> stageList){
        Battle battle = this.battle.toDeserialized(stageList);
        battle.user = player.toDeserialized();
        battle.user.user.weapon = weapon;
        return battle;
    }
}
