package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Brand;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;

import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "brand",
        foreignKeys = {
            @ForeignKey(entity = SkillRoom.class,
                    parentColumns = "id",
                    childColumns = "skill"
            )
})
public class BrandRoom {
    @PrimaryKey
    public int id;

    public String name;
    public String url;
    public int skill;

    public BrandRoom(int id,String name,String url,int skill){
        this.id = id;
        this.name = name;
        this.url = url;
        this.skill = skill;
    }

    public Brand toDeserialized(List<SkillRoom> skillRooms){
        Brand brand = new Brand();
        brand.id = id;
        brand.name = name;
        brand.url = url;
        for(SkillRoom skillRoom:skillRooms){
            if(skill==skillRoom.id){
                brand.skill = skillRoom.toDeserialized();
            }
        }
        return brand;
    }
}
