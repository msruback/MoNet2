package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Sub;

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

    public Sub toDeserialized(){
        Sub sub = new Sub();
        sub.id = id;
        sub.name = name;
        sub.url = url;
        return sub;
    }
}
