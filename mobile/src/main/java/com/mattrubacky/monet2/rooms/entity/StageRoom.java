package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Stage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stage")
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

    public Stage toDeserialized(){
        Stage stage = new Stage();
        stage.id = id;
        stage.url = url;
        stage.name = name;
        return stage;
    }
}
