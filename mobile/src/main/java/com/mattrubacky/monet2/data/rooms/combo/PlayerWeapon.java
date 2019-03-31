package com.mattrubacky.monet2.data.rooms.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;

public class PlayerWeapon {
    public PlayerRoom player;
    public WeaponCombo weapon;

    public Player toDeserialized(){
        Player deserialized = player.toDeserialized();
        deserialized.user.weapon = weapon.toDeserialized();
        return deserialized;
    }
}
