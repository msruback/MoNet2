package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.Product;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "gear"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "skill")
        })
public class ProductRoom {

    public ProductRoom(String id, String price, Long endTime,Gear gear,Skill skill){
        this.id = id;
        this.price = price;
        this.endTime = endTime;
        this.gear = gear;
        this.skill = skill;
    }

    @Ignore
    public ProductRoom(Product product){
        this.id = product.id;
        this.price = product.price;
        this.endTime = product.endTime;
        this.gear = product.gear;
        this.skill = product.skill;
    }

    @PrimaryKey
    public String id;

    public String price;

    public Long endTime;

    public Gear gear;

    public Skill skill;

}
