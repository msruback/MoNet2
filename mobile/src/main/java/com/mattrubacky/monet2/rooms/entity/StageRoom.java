package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StageRoom {
    @PrimaryKey
    public int id;

    public String url;

    public String name;

    public StageRoom(int id, String url, String name){
        this.id = id;
        this.url = url;
        this.name = name;
    }
}
