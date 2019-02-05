package com.mattrubacky.monet2.rooms.entity;

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
}
