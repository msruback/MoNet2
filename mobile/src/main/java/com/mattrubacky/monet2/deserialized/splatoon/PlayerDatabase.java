package com.mattrubacky.monet2.deserialized.splatoon;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.Player;

/**
 * Created by mattr on 10/17/2017.
 * The Player object used by PlayerManager
 * Not exactly something that will ever be serialized or deserialized, but it fits best here
 */

public class PlayerDatabase extends DatabaseObject {
    public PlayerDatabase(){}

    @SerializedName("player")
    public Player player;
    @SerializedName("player_type")
    public int playerType;
    @SerializedName("battle_type")
    public String battleType;
    @SerializedName("battle_id")
    public int battleID;

}
