package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.Player;
import com.mattrubacky.monet2.deserialized.splatoon.PlayerDatabase;
import com.mattrubacky.monet2.deserialized.splatoon.PlayerType;
import com.mattrubacky.monet2.deserialized.splatoon.Rank;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Skill;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestGrade;
import com.mattrubacky.monet2.deserialized.splatoon.User;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Weapon;
import com.mattrubacky.monet2.sqlite.Factory.DatabaseObjectFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class PlayerManager extends TableArrayManager<PlayerDatabase>{
    private GearManager gearManager;
    private SkillManager skillManager;
    private WeaponManager weaponManager;


    public PlayerManager(Context context){
        super(context,PlayerDatabase.class);
        gearManager = new GearManager(context);
        skillManager = new SkillManager(context);
        weaponManager = new WeaponManager(context);
    }

    @Override
    public void addToInsert(int id,PlayerDatabase playerDatabase){
        super.addToInsert(playerDatabase.battleID,playerDatabase);
        gearManager.addToInsert(playerDatabase.player.user.head);
        skillManager.addToInsert(playerDatabase.player.user.head.brand.skill);
        skillManager.addToInsert(playerDatabase.player.user.headSkills.main);
        for(Skill skill : playerDatabase.player.user.headSkills.subs){
            skillManager.addToInsert(skill);
        }

        gearManager.addToInsert(playerDatabase.player.user.clothes);
        skillManager.addToInsert(playerDatabase.player.user.clothes.brand.skill);
        skillManager.addToInsert(playerDatabase.player.user.clothesSkills.main);
        for(Skill skill : playerDatabase.player.user.clothesSkills.subs){
            skillManager.addToInsert(skill);
        }

        gearManager.addToInsert(playerDatabase.player.user.shoes);
        skillManager.addToInsert(playerDatabase.player.user.shoes.brand.skill);
        skillManager.addToInsert(playerDatabase.player.user.shoeSkills.main);
        for(Skill skill : playerDatabase.player.user.shoeSkills.subs){
            skillManager.addToInsert(skill);
        }

        weaponManager.addToInsert(playerDatabase.player.user.weapon);

    }

    @Override
    public void insert(){
        skillManager.insert();
        weaponManager.insert();
        gearManager.insert();
        super.insert();
    }


    @Override
    protected PlayerDatabase buildObject(Class<PlayerDatabase> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        PlayerDatabase playerDatabase = super.buildObject(type,cursor);
        playerDatabase.player.user = DatabaseObjectFactory.parseObject(new User(),cursor);

        gearManager.addToSelect(playerDatabase.player.user.head.id,"head");
        skillManager.addToSelect(playerDatabase.player.user.headSkills.main.id);
        for(int i=0; i<playerDatabase.player.user.headSkills.subs.size();i++){
            if(playerDatabase.player.user.headSkills.subs.get(i).id!=-1){
                skillManager.addToInsert(playerDatabase.player.user.headSkills.subs.get(i));
            }else{
                playerDatabase.player.user.headSkills.subs.remove(i);
            }
        }

        gearManager.addToSelect(playerDatabase.player.user.clothes.id,"clothes");
        skillManager.addToSelect(playerDatabase.player.user.clothesSkills.main.id);
        for(int i=0; i<playerDatabase.player.user.clothesSkills.subs.size();i++){
            if(playerDatabase.player.user.clothesSkills.subs.get(i).id!=-1){
                skillManager.addToInsert(playerDatabase.player.user.clothesSkills.subs.get(i));
            }else{
                playerDatabase.player.user.clothesSkills.subs.remove(i);
            }
        }

        gearManager.addToSelect(playerDatabase.player.user.shoes.id,"shoes");
        skillManager.addToSelect(playerDatabase.player.user.shoeSkills.main.id);
        for(int i=0; i<playerDatabase.player.user.shoeSkills.subs.size();i++){
            if(playerDatabase.player.user.shoeSkills.subs.get(i).id!=-1){
                skillManager.addToInsert(playerDatabase.player.user.shoeSkills.subs.get(i));
            }else{
                playerDatabase.player.user.shoeSkills.subs.remove(i);
            }
        }

        return playerDatabase;
    }

    public HashMap<Integer,ArrayList<PlayerDatabase>> select(){
        HashMap<Integer,ArrayList<PlayerDatabase>> selected = new HashMap<>();

        HashMap<Integer,ArrayList<PlayerDatabase>> playerHashMap = super.select();
        ArrayList<HashMap<Integer, Gear>> gearHashMap = gearManager.select();
        HashMap<Integer, Skill> skillHashMap = skillManager.select();
        HashMap<Integer, Weapon> weaponHashMap = weaponManager.select();
        for(Integer key : playerHashMap.keySet()) {
            for (PlayerDatabase player : playerHashMap.get(key)) {

                player.player.user.head = gearHashMap.get(0).get(player.player.user.head.id);

                GearSkills headSkills = new GearSkills();
                headSkills.main = skillHashMap.get(player.player.user.headSkills.main.id);
                headSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.headSkills.subs) {
                    headSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.headSkills = headSkills;

                player.player.user.clothes = gearHashMap.get(1).get(player.player.user.clothes.id);

                GearSkills clothesSkills = new GearSkills();
                clothesSkills.main = skillHashMap.get(player.player.user.clothesSkills.main.id);
                clothesSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.headSkills.subs) {
                    headSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.clothesSkills = clothesSkills;

                player.player.user.shoes = gearHashMap.get(2).get(player.player.user.shoes.id);

                GearSkills shoeSkills = new GearSkills();
                shoeSkills.main = skillHashMap.get(player.player.user.shoeSkills.main.id);
                shoeSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.shoeSkills.subs) {
                    shoeSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.shoeSkills = shoeSkills;

                player.player.user.weapon = weaponHashMap.get(player.player.user.weapon.id);

                ArrayList<PlayerDatabase> battlePlayers;
                if (selected.containsKey(player.battleID)) {
                    battlePlayers = selected.get(player.battleID);
                } else {
                    battlePlayers = new ArrayList<>();
                }
                battlePlayers.add(player);
                selected.put(player.battleID, battlePlayers);
            }
        }
        return selected;
    }

    public HashMap<Integer,ArrayList<PlayerDatabase>> selectAll(){
        HashMap<Integer,ArrayList<PlayerDatabase>> selected = new HashMap<>();

        HashMap<Integer,ArrayList<PlayerDatabase>> playerHashMap = super.selectAll();
        ArrayList<HashMap<Integer, Gear>> gearHashMap = gearManager.select();
        HashMap<Integer, Skill> skillHashMap = skillManager.select();
        HashMap<Integer, Weapon> weaponHashMap = weaponManager.select();
        for(Integer key : playerHashMap.keySet()) {
            for (PlayerDatabase player : playerHashMap.get(key)) {

                player.player.user.head = gearHashMap.get(0).get(player.player.user.head.id);

                GearSkills headSkills = new GearSkills();
                headSkills.main = skillHashMap.get(player.player.user.headSkills.main.id);
                headSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.headSkills.subs) {
                    headSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.headSkills = headSkills;

                player.player.user.clothes = gearHashMap.get(1).get(player.player.user.clothes.id);

                GearSkills clothesSkills = new GearSkills();
                clothesSkills.main = skillHashMap.get(player.player.user.clothesSkills.main.id);
                clothesSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.headSkills.subs) {
                    headSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.clothesSkills = clothesSkills;

                player.player.user.shoes = gearHashMap.get(2).get(player.player.user.shoes.id);

                GearSkills shoeSkills = new GearSkills();
                shoeSkills.main = skillHashMap.get(player.player.user.shoeSkills.main.id);
                shoeSkills.subs = new ArrayList<>();
                for(Skill skill : player.player.user.shoeSkills.subs) {
                    shoeSkills.subs.add(skillHashMap.get(skill.id));
                }
                player.player.user.shoeSkills = shoeSkills;

                player.player.user.weapon = weaponHashMap.get(player.player.user.weapon.id);

                ArrayList<PlayerDatabase> battlePlayers;
                if (selected.containsKey(player.battleID)) {
                    battlePlayers = selected.get(player.battleID);
                } else {
                    battlePlayers = new ArrayList<>();
                }
                battlePlayers.add(player);
                selected.put(player.battleID, battlePlayers);
            }
        }
        return selected;
    }

    public HashMap<Integer, Battle> selectStats(int itemID, String itemType){

        SQLiteDatabase database = new SplatnetSQLHelper(super.context).getReadableDatabase();

        BattleManager battleManager = new BattleManager(super.context);

        String[] args;

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Player.COLUMN_TYPE+" = ?");

        //build the select statement
        switch (itemType){
            case "weapon":
                builder.append(" AND "+SplatnetContract.Player.COLUMN_WEAPON+" = ?");
                args = new String[2];
                args[0] = String.valueOf(0);
                args[1] = String.valueOf(itemID);
                break;
            case "head":
                builder.append(" AND "+SplatnetContract.Player.COLUMN_HEAD+" = ?");
                args = new String[2];
                args[0] = String.valueOf(0);
                args[1] = String.valueOf(itemID);
                break;
            case "clothes":
                builder.append(" AND "+SplatnetContract.Player.COLUMN_CLOTHES+" = ?");
                args = new String[2];
                args[0] = String.valueOf(0);
                args[1] = String.valueOf(itemID);
                break;
            case "shoes":
                builder.append(" AND "+SplatnetContract.Player.COLUMN_SHOES+" = ?");
                args = new String[2];
                args[0] = String.valueOf(0);
                args[1] = String.valueOf(itemID);
                break;
            case "all":
                args = new String[1];
                args[0] = String.valueOf(itemID);
                break;
            default:
                args = new String[1];
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Player.TABLE_NAME,null,whereClause,args,null,null,null);

        PlayerDatabase player;

        if(cursor.moveToFirst()){
            do{
                player = new PlayerDatabase();
                player.battleID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_BATTLE));
                battleManager.addToSelect(player.battleID);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return battleManager.select();
    }
}