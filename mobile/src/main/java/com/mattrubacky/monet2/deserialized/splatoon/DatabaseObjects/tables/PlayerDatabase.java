package com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.Player;
import com.mattrubacky.monet2.sqlite.Factory.ColumnName;
import com.mattrubacky.monet2.sqlite.Factory.TableName;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

/**
 * Created by mattr on 10/17/2017.
 * The Player object used by PlayerManager
 * Not exactly something that will ever be serialized or deserialized, but it fits best here
 */
@TableName(SplatnetContract.Player.TABLE_NAME)
public class PlayerDatabase extends DatabaseObject {
    public PlayerDatabase(){}

    @SerializedName("player")
    public Player player;

    @ColumnName(SplatnetContract.Player.COLUMN_TYPE)
    @SerializedName("player_type")
    public int playerType;

    @ColumnName(SplatnetContract.Player.COLUMN_MODE)
    @SerializedName("battle_type")
    public String battleType;

    @ColumnName(SplatnetContract.Player.COLUMN_BATTLE)
    @SerializedName("battle_id")
    public int battleID;

    @Override
    public int getId() {
        return battleID;
    }
}
