package com.mattrubacky.monet2.data.combo;

import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.entity.PlayerRoom;

import androidx.room.Embedded;

public class PlayerWeapon {
    @Embedded
    public PlayerRoom player;
    @Embedded
    public WeaponCombo weapon;

    public Player toDeserialized(){
        Player deserialized = player.toDeserialized();
        deserialized.user.weapon = weapon.toDeserialized();
        return deserialized;
    }
}
