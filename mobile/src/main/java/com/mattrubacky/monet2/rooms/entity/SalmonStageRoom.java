package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "salmon_stage")
public class SalmonStageRoom {
    @PrimaryKey
    public int id;

    public String name;
    public String url;

    public SalmonStageRoom(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public SalmonStageRoom(String name, String url){
        switch(name){
            case "Spawning Grounds":
                id = 0;
                break;
            case "Marooner's Bay":
                id = 1;
                break;
            case "Lost Outpost":
                id = 2;
                break;
            case "Salmonid Smokeyard":
                id = 3;
                break;
            case "Ruins of Ark Polaris":
                id = 4;
                break;
            default:
                id = 5;
        }
        this.name = name;
        this.url = url;
    }
}
