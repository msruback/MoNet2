package com.mattrubacky.monet2.data.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * The Player object used by PlayerManager
 * Not exactly something that will ever be serialized or deserialized, but it fits best here
 */

public class PlayerDatabase {
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
