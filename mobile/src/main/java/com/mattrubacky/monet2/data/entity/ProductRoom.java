package com.mattrubacky.monet2.data.entity;

import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.Ordered;
import com.mattrubacky.monet2.data.deserialized.splatoon.Product;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        foreignKeys = {
                @ForeignKey(entity = Gear.class,
                        parentColumns = "gear_id",
                        childColumns = "gear"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "skill")
        },
        indices = {
                @Index(name="product_gear",
                        value = "gear"),
                @Index(name="product_skill",
                    value = "skill")
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

    @Ignore
    public ProductRoom(Ordered ordered){
        this.id = "-1";
        this.price = ordered.price;
        this.gear = ordered.gear;
        this.skill = ordered.skill;
    }

    @PrimaryKey
    @NonNull
    public String id;

    public String price;

    public Long endTime;

    public Gear gear;

    public Skill skill;

}
