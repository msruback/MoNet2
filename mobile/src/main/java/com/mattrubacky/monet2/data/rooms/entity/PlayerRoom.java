package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.deserialized.splatoon.PlayerType;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestGrade;
import com.mattrubacky.monet2.data.deserialized.splatoon.User;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "player",
        foreignKeys = {
                @ForeignKey(entity = Weapon.class,
                        parentColumns = "id",
                        childColumns = "weapon"),
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "head"),
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "clothes"),
                @ForeignKey(entity = Gear.class,
                        parentColumns = "id",
                        childColumns = "shoes"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "headMain"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "headSub1"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "headSub2"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "headSub3"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "clothesMain"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "clothesSub1"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "clothesSub2"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "clothesSub3"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "shoeMain"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "shoeSub1"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "shoeSub2"),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "shoeSub3")
        })
public class PlayerRoom {
    public int battleId;
    public int playerType;
    public String battleType;

    public String userId;
    public String name;
    public String species;
    public String style;
    public int level;
    public int starLevel;
    public String rank;
    public String sPlus;
    public boolean isX;
    public SplatfestGrade fesGrade;

    public int point;
    public int kill;
    public int assist;
    public int death;
    public int special;

    public Weapon weapon;
    public Gear head;
    public Gear clothes;
    public Gear shoes;

    public Skill headMain;
    public Skill headSub1;
    public Skill headSub2;
    public Skill headSub3;

    public Skill clothesMain;
    public Skill clothesSub1;
    public Skill clothesSub2;
    public Skill clothesSub3;

    public Skill shoeMain;
    public Skill shoeSub1;
    public Skill shoeSub2;
    public Skill shoeSub3;

    public PlayerRoom(int battleId, int playerType, String battleType, String userId, String name, String species, String style,
                      int level, int starLevel, String rank, String sPlus, Boolean isX, SplatfestGrade fesGrade,
                      int point, int kill, int assist, int death, int special, Weapon weapon, Gear head, Gear clothes, Gear shoes,
                      Skill headMain, Skill headSub1, Skill headSub2, Skill headSub3, Skill clothesMain, Skill clothesSub1,
                      Skill clothesSub2, Skill clothesSub3, Skill shoeMain, Skill shoeSub1, Skill shoeSub2, Skill shoeSub3){
        this.battleId = battleId;
        this.playerType = playerType;
        this.battleType = battleType;
        this.userId = userId;
        this.name = name;
        this.species = species;
        this.style = style;
        this.level = level;
        this.starLevel = starLevel;
        this.rank = rank;
        this.sPlus = sPlus;
        this.isX = isX;
        this.fesGrade = fesGrade;
        this.point = point;
        this.kill = kill;
        this.assist = assist;
        this.death = death;
        this.special = special;
        this.weapon = weapon;
        this.head = head;
        this.clothes = clothes;
        this.shoes = shoes;
        this.headMain = headMain;
        this.headSub1 = headSub1;
        this.headSub2 = headSub2;
        this.headSub3 = headSub3;
        this.clothesMain = clothesMain;
        this.clothesSub1 = clothesSub1;
        this.clothesSub2 = clothesSub2;
        this.clothesSub3 = clothesSub3;
    }

    @Ignore
    public PlayerRoom(Battle battle, Player player,int playerType, String battleType){
        battleId = battle.id;
        this.playerType = playerType;
        if(playerType!=0) {
            userId = player.user.uniqueId;
        }else{
            userId = player.user.id;
        }
        this.battleType = battleType;
        switch (battleType) {
            case "gachi":
                this.rank = player.user.udamae.rank;
                this.sPlus = player.user.udamae.sPlus;
                this.isX = player.user.udamae.isX;
                break;
            case "league":
                this.rank = player.user.udamae.rank;
                this.sPlus = player.user.udamae.sPlus;
                this.isX = player.user.udamae.isX;
                break;
            case "fes":
                this.fesGrade = player.user.grade;
                break;
        }
        this.name = player.user.name;
        this.species = player.user.playerType.species;
        this.style = player.user.playerType.style;
        this.level = player.user.rank;
        this.starLevel = player.user.starRank;
        this.point = player.points;
        this.kill = player.kills;
        this.assist = player.assists;
        this.special = player.special;
        this.weapon = player.user.weapon;
        this.head = player.user.head;
        this.clothes = player.user.clothes;
        this.shoes = player.user.shoes;

        this.headMain = player.user.headSkills.main;
        if(player.user.headSkills.subs.size()>0){
            this.headSub1 = player.user.headSkills.subs.get(0);
            if(player.user.headSkills.subs.size()>1){
                this.headSub2 = player.user.headSkills.subs.get(1);
                if(player.user.headSkills.subs.size()>2){
                    this.headSub3 = player.user.headSkills.subs.get(2);
                }
            }
        }

        this.clothesMain = player.user.clothesSkills.main;
        if(player.user.clothesSkills.subs.size()>0){
            this.clothesSub1 = player.user.clothesSkills.subs.get(0);
            if(player.user.clothesSkills.subs.size()>1){
                this.clothesSub2 = player.user.clothesSkills.subs.get(1);
                if(player.user.clothesSkills.subs.size()>2){
                    this.clothesSub3 = player.user.clothesSkills.subs.get(2);
                }
            }
        }

        this.shoeMain = player.user.shoeSkills.main;
        if(player.user.shoeSkills.subs.size()>0){
            this.shoeSub1 = player.user.shoeSkills.subs.get(0);
            if(player.user.shoeSkills.subs.size()>1){
                this.shoeSub2 = player.user.shoeSkills.subs.get(1);
                if(player.user.shoeSkills.subs.size()>2){
                    this.shoeSub3 = player.user.shoeSkills.subs.get(2);
                }
            }
        }
    }

    public Player toDeserialized(){
        Player player = new Player();
        player.user = new User();
        if(playerType==0){
            player.user.uniqueId = userId;
        }else{
            player.user.id = userId;
        }

        switch (battleType) {
            case "gachi":
                player.user.udamae.rank = rank;
                player.user.udamae.sPlus = sPlus;
                player.user.udamae.isX = isX;
                break;
            case "league":
                this.rank = player.user.udamae.rank;
                this.sPlus = player.user.udamae.sPlus;
                this.isX = player.user.udamae.isX;
                break;
            case "fes":
                player.user.grade = fesGrade;
                break;
        }
        player.user.name = name;

        player.user.playerType = new PlayerType();
        player.user.playerType.species = species;
        player.user.playerType.style = style;

        player.user.rank = level;
        player.user.starRank = starLevel;
        player.points = point;
        player.kills = kill;
        player.assists = assist;
        player.special = special;
        player.user.weapon = weapon;
        player.user.head = head;
        player.user.clothes = clothes;
        player.user.shoes = shoes;

        player.user.headSkills = new GearSkills();
        player.user.headSkills.main = headMain;
        player.user.headSkills.subs = new ArrayList<>();
        player.user.headSkills.subs.add(headSub1);
        player.user.headSkills.subs.add(headSub2);
        player.user.headSkills.subs.add(headSub3);

        player.user.clothesSkills = new GearSkills();
        player.user.clothesSkills.main = clothesMain;
        player.user.clothesSkills.subs = new ArrayList<>();
        player.user.clothesSkills.subs.add(clothesSub1);
        player.user.clothesSkills.subs.add(clothesSub2);
        player.user.clothesSkills.subs.add(clothesSub3);

        player.user.shoeSkills = new GearSkills();
        player.user.shoeSkills.main = shoeMain;
        player.user.shoeSkills.subs = new ArrayList<>();
        player.user.shoeSkills.subs.add(shoeSub1);
        player.user.shoeSkills.subs.add(shoeSub2);
        player.user.shoeSkills.subs.add(shoeSub3);

        return player;
    }
}
