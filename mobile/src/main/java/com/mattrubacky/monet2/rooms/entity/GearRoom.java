package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Gear;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "gear",
        foreignKeys = {
            @ForeignKey(entity = BrandRoom.class,
                        parentColumns = "id",
                        childColumns = "brand")
        }
)
public class GearRoom {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "splatnet_id")
    public int splatnetId;

    public String name;
    public String url;
    public String kind;
    public int rarity;
    public int brand;

    public GearRoom(int id, int splatnetId, String name, String url, String kind, int rarity, int brand){
        this.id = id;
        this.splatnetId = splatnetId;
        this.name = name;
        this.url = url;
        this.kind = kind;
        this.rarity = rarity;
        this.brand = brand;
    }

    @Ignore
    public GearRoom(int splatnetId, String name, String url, String kind, int rarity, int brand){
        id = generateId(splatnetId,kind);
        this.splatnetId = splatnetId;
        this.name = name;
        this.url = url;
        this.kind = kind;
        this.rarity = rarity;
        this.brand = brand;
    }

    public Gear toDeserialized(){
        Gear gear = new Gear();
        gear.id = splatnetId;
        gear.name = name;
        gear.url = url;
        gear.rarity = rarity;
        return gear;
    }

    public Gear toDeserialized(List<BrandRoom> brands,List<SkillRoom> skills){
        Gear gear = new Gear();
        gear.id = splatnetId;
        gear.name = name;
        gear.url = url;
        gear.rarity = rarity;
        for(BrandRoom brandRoom:brands){
            if(brandRoom.id ==brand){
                gear.brand = brandRoom.toDeserialized(skills);
            }
        }
        return gear;
    }

    public static int generateId(int id, String kind){
        int newId;
        switch(kind){
            case "head":
                newId=100000;
                break;
            case "clothes":
                newId=200000;
                break;
            case "shoes":
                newId=300000;
                break;
            default:
                newId=0;
        }
        return newId+id;
    }
}
