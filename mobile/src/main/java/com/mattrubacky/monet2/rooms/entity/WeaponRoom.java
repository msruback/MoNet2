package com.mattrubacky.monet2.rooms.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "weapon",
        foreignKeys = {
                @ForeignKey(entity = SubRoom.class,
                            parentColumns = "id",
                            childColumns = "sub"),
                @ForeignKey(entity = SpecialRoom.class,
                            parentColumns = "id",
                            childColumns = "special")
        })
public class WeaponRoom {
    @PrimaryKey
    public int id;

    public String name;
    public String url;
    public int sub;
    public int special;

    public WeaponRoom(int id, String name, String url, int sub, int special){
        this.id = id;
        this.name = name;
        this.url = url;
        this.sub = sub;
        this.special = special;
    }
}
