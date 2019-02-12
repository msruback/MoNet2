package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.SalmonRunWeapon;
import com.mattrubacky.monet2.deserialized.splatoon.Weapon;

import java.util.List;

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

    public SalmonRunWeapon toDeserialized(){
        SalmonRunWeapon salmonRunWeapon = new SalmonRunWeapon();
        salmonRunWeapon.id = id;
        salmonRunWeapon.weapon = new Weapon();
        salmonRunWeapon.weapon.id = id;
        salmonRunWeapon.weapon.name = name;
        salmonRunWeapon.weapon.url = url;
        return salmonRunWeapon;
    }

    public Weapon toDeserialized(List<SpecialRoom> specials,List<SubRoom> subs){
        Weapon weapon = new Weapon();
        weapon.id = id;
        weapon.name = name;
        weapon.url = url;

        for(SpecialRoom special:specials){
            if(this.special == special.id){
                weapon.special = special.toDeserialized();
            }
        }

        for(SubRoom sub:subs){
            if(this.sub == sub.id){
                weapon.sub = sub.toDeserialized();
            }
        }

        return weapon;
    }
}
