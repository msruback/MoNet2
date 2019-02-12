package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Special;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "special")
public class SpecialRoom {
    @PrimaryKey
    public int id;

    public String name;
    public String url;

    public SpecialRoom(int id,String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Special toDeserialized(){
        Special special = new Special();
        special.id = id;
        special.name = name;
        special.url = url;
        return special;
    }
}
