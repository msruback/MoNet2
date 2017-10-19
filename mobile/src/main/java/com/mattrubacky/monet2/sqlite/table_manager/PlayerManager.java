package com.mattrubacky.monet2.sqlite.table_manager;

import android.content.Context;

import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.PlayerDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

public class PlayerManager {
    Context context;
    ArrayList<PlayerDatabase> toInsert;
    ArrayList<Integer> toSelect;
    HashMap<Integer,ArrayList<Player>> selected;
    public PlayerManager(Context context){
        this.context = context;
        toSelect = new ArrayList<>();
        toInsert = new ArrayList<>();
        selected = new HashMap<>();
    }
    private boolean exists(int id){
        return true;
    }

    public void addToInsert(Player player,String mode,int battleId,int type){
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.player = player;
        playerDatabase.battleType = mode;
        playerDatabase.battleID = battleId;
        playerDatabase.playerType = type;
        toInsert.add(playerDatabase);
    }

    public void addToSelect(int id){
        toSelect.add(id);
    }


}
