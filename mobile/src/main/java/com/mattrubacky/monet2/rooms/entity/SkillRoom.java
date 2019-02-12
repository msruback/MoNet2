package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.Skill;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skill")
public class SkillRoom {
    @PrimaryKey
    public int id;

    public String name;

    public String url;

    public SkillRoom(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Skill toDeserialized(){
        Skill skill = new Skill();
        skill.id = id;
        skill.name = name;
        skill.url = url;
        return skill;
    }
}
