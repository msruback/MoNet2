package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub")
public class SubRoom {
    @PrimaryKey
    public int id;

    public String name;
    public String url;

    public SubRoom(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
