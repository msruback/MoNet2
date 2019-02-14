package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.SalmonStage;

import androidx.room.Entity;
import androidx.room.Ignore;
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

    @Ignore
    public SalmonStageRoom(String name, String url){
        this.id = generateId(name);
        this.name = name;
        this.url = url;
    }

    public SalmonStage toDeserialized(){
        SalmonStage salmonStage = new SalmonStage();
        salmonStage.name = name;
        salmonStage.url = url;
        return salmonStage;
    }

    public int generateId(String name){
        switch(name){
            case "Spawning Grounds":
                return 0;
            case "Marooner's Bay":
                return 1;
            case "Lost Outpost":
                return 2;
            case "Salmonid Smokeyard":
                return 3;
            case "Ruins of Ark Polaris":
                return 4;
            default:
                return -1;
        }
    }
}
