package com.mattrubacky.monet2.sqlite.table_manager;

import android.content.Context;

import com.mattrubacky.monet2.sqlite.*;
import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

public class ClosetManager {
    Context context;
    HashMap<Integer,ClosetHanger> toUpdate;
    HashMap<Integer,ClosetHanger> toInsert;
    ArrayList<Integer> toSelect;

    public ClosetManager(Context context){
        this.context = context;

        toInsert = new HashMap<>();  //using a hashmap to prevent duplicate entries
        toUpdate = new HashMap<>();  //using a hashmap to prevent duplicate entries, and keep updates low
        toSelect = new ArrayList<>();

    }

    private boolean exists(int id){
        return true;
    }

    public void addToInsert(Gear gear, GearSkills skills,Battle battle){
        ClosetHanger hanger = new ClosetHanger();
        hanger.gear = gear;
        hanger.skills = skills;
        hanger.time = battle.start;

        if(exists(gear.id)){
            toUpdate.put(gear.id,hanger);
        }else{
            toInsert.put(gear.id,hanger);
        }
    }

    public void insert(){}

    //public ClosetHanger select(){}

    //public ArrayList<ClosetHanger> selectMulti(){};
}
