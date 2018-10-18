package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class PlayerManager extends TableArrayManager<PlayerDatabase>{
    private GearManager gearManager;
    private TableManager<Skill> skillManager;
    private WeaponManager weaponManager;
    ArrayList<Integer> headIds;
    ArrayList<Integer>


    public PlayerManager(Context context){
        super(context,PlayerDatabase.class);
        gearManager = new GearManager(context);
        skillManager = new TableManager<Skill>(context,Skill.class);
        weaponManager = new WeaponManager(context);
    }

    @Override
    public void addToInsert(int id,PlayerDatabase playerDatabase){
        super.addToInsert(playerDatabase.battleID,playerDatabase);
        gearManager.addToInsert(playerDatabase.player.user.head);
        skillManager.addToInsert(playerDatabase.player.user.headSkills.main);
        for(Skill skill : playerDatabase.player.user.headSkills.subs){
            skillManager.addToInsert(skill);
        }

        gearManager.addToInsert(playerDatabase.player.user.clothes);
        skillManager.addToInsert(playerDatabase.player.user.clothesSkills.main);
        for(Skill skill : playerDatabase.player.user.clothesSkills.subs){
            skillManager.addToInsert(skill);
        }

        gearManager.addToInsert(playerDatabase.player.user.shoes);
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

        int subID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SUB));
        subIDs.add(subID);
        subManager.addToSelect(subID);

        int specialID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Weapon.COLUMN_SPECIAL));
        specialIDs.add(specialID);
        specialManager.addToSelect(specialID);

        return weapon;
    }

    public HashMap<Integer,ArrayList<PlayerDatabase>> select(){
        HashMap<Integer,ArrayList<PlayerDatabase>> selected = new HashMap<>();
        if(toSelect.size()>0) {

            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Player.COLUMN_BATTLE + " = ?");

            //build the select statement
            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Player.COLUMN_BATTLE + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Player.TABLE_NAME, null, whereClause, args, null, null, null);

            ArrayList<PlayerDatabase> players = new ArrayList<>();
            PlayerDatabase player;

            ArrayList<Integer> headIDs = new ArrayList<>(), headMainIDs = new ArrayList<>();
            ArrayList<Integer> headSub1IDs = new ArrayList<>(), headSub2IDs = new ArrayList<>(), headSub3IDs = new ArrayList<>();
            int headID, headMainID, headSub1ID, headSub2ID, headSub3ID;

            ArrayList<Integer> clothesIDs = new ArrayList<>(), clothesMainIDs = new ArrayList<>();
            ArrayList<Integer> clothesSub1IDs = new ArrayList<>(), clothesSub2IDs = new ArrayList<>(), clothesSub3IDs = new ArrayList<>();
            int clothesID, clothesMainID, clothesSub1ID, clothesSub2ID, clothesSub3ID;

            ArrayList<Integer> shoeIDs = new ArrayList<>(), shoeMainIDs = new ArrayList<>();
            ArrayList<Integer> shoeSub1IDs = new ArrayList<>(), shoeSub2IDs = new ArrayList<>(), shoeSub3IDs = new ArrayList<>();
            int shoeID, shoeMainID, shoeSub1ID, shoeSub2ID, shoeSub3ID;

            ArrayList<Integer> weaponIDs = new ArrayList<>();
            int weaponID;

            if (cursor.moveToFirst()) {
                do {
                    player = new PlayerDatabase();
                    player.battleID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_BATTLE));
                    player.playerType = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_TYPE));
                    player.battleType = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_MODE));
                    player.player = new Player();
                    player.player.points = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_POINT));
                    player.player.kills = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_KILL));
                    player.player.assists = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ASSIST));
                    player.player.deaths = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_DEATH));
                    player.player.special = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SPECIAL));


                    User user = new User();
                    user.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_NAME));
                    user.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ID));
                    user.rank = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_LEVEL));
                    Rank rank = new Rank();
                    rank.rank = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_RANK));
                    rank.sPlus = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_S_NUM));
                    user.udamae = rank;
                    SplatfestGrade splatfestGrade = new SplatfestGrade();
                    splatfestGrade.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_FES_GRADE));
                    user.grade = splatfestGrade;
                    user.playerType = new PlayerType();
                    user.playerType.species = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SPECIES));
                    user.playerType.style = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_STYLE));

                    //Head

                    headID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD));
                    gearManager.addToSelect(headID, "head");
                    headIDs.add(headID);

                    headMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_MAIN));
                    skillManager.addToSelect(headMainID);
                    headMainIDs.add(headMainID);

                    headSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_1));
                    skillManager.addToSelect(headSub1ID);
                    headSub1IDs.add(headSub1ID);

                    headSub2ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_2));
                    skillManager.addToSelect(headSub2ID);
                    headSub2IDs.add(headSub2ID);

                    headSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_3));
                    skillManager.addToSelect(headSub3ID);
                    headSub3IDs.add(headSub3ID);

                    //Clothes

                    clothesID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES));
                    gearManager.addToSelect(clothesID, "clothes");
                    clothesIDs.add(clothesID);

                    clothesMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_MAIN));
                    skillManager.addToSelect(clothesMainID);
                    clothesMainIDs.add(clothesMainID);

                    clothesSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1));
                    skillManager.addToSelect(clothesSub1ID);
                    clothesSub1IDs.add(clothesSub1ID);

                    clothesSub2ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2));
                    skillManager.addToSelect(clothesSub2ID);
                    clothesSub2IDs.add(clothesSub2ID);

                    clothesSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3));
                    skillManager.addToSelect(clothesSub3ID);
                    clothesSub3IDs.add(clothesSub3ID);

                    //Shoes

                    shoeID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES));
                    gearManager.addToSelect(shoeID, "shoes");
                    shoeIDs.add(shoeID);

                    shoeMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_MAIN));
                    skillManager.addToSelect(shoeMainID);
                    shoeMainIDs.add(shoeMainID);

                    shoeSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_1));
                    skillManager.addToSelect(shoeSub1ID);
                    shoeSub1IDs.add(shoeSub1ID);

                    shoeSub2ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_2));
                    skillManager.addToSelect(shoeSub2ID);
                    shoeSub2IDs.add(shoeSub2ID);

                    shoeSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_3));
                    skillManager.addToSelect(shoeSub3ID);
                    shoeSub3IDs.add(shoeSub3ID);

                    //Weapon

                    weaponID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_WEAPON));
                    weaponManager.addToSelect(weaponID);
                    weaponIDs.add(weaponID);

                    player.player.user = user;

                    players.add(player);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            ArrayList<HashMap<Integer, Gear>> gearHashMap = gearManager.select();
            HashMap<Integer, Skill> skillHashMap = skillManager.select();
            HashMap<Integer, Weapon> weaponHashMap = weaponManager.select();

            GearSkills headSkills, clothesSkills, shoeSkills;

            ArrayList<PlayerDatabase> battlePlayers;

            for (int i = 0; i < players.size(); i++) {
                player = players.get(i);

                player.player.user.head = gearHashMap.get(0).get(headIDs.get(i));

                headSkills = new GearSkills();
                headSkills.main = skillHashMap.get(headMainIDs.get(i));
                headSkills.subs = new ArrayList<>();
                headSkills.subs.add(skillHashMap.get(headSub1IDs.get(i)));
                headSkills.subs.add(skillHashMap.get(headSub2IDs.get(i)));
                headSkills.subs.add(skillHashMap.get(headSub3IDs.get(i)));
                player.player.user.headSkills = headSkills;

                player.player.user.clothes = gearHashMap.get(1).get(clothesIDs.get(i));

                clothesSkills = new GearSkills();
                clothesSkills.main = skillHashMap.get(clothesMainIDs.get(i));
                clothesSkills.subs = new ArrayList<>();
                clothesSkills.subs.add(skillHashMap.get(clothesSub1IDs.get(i)));
                clothesSkills.subs.add(skillHashMap.get(clothesSub2IDs.get(i)));
                clothesSkills.subs.add(skillHashMap.get(clothesSub3IDs.get(i)));
                player.player.user.clothesSkills = clothesSkills;

                player.player.user.shoes = gearHashMap.get(2).get(shoeIDs.get(i));

                shoeSkills = new GearSkills();
                shoeSkills.main = skillHashMap.get(shoeMainIDs.get(i));
                shoeSkills.subs = new ArrayList<>();
                shoeSkills.subs.add(skillHashMap.get(shoeSub1IDs.get(i)));
                shoeSkills.subs.add(skillHashMap.get(shoeSub2IDs.get(i)));
                shoeSkills.subs.add(skillHashMap.get(shoeSub3IDs.get(i)));
                player.player.user.shoeSkills = shoeSkills;

                player.player.user.weapon = weaponHashMap.get(weaponIDs.get(i));

                if (selected.containsKey(player.battleID)) {
                    battlePlayers = selected.get(player.battleID);
                } else {
                    battlePlayers = new ArrayList<>();
                }
                battlePlayers.add(player);
                selected.put(player.battleID, battlePlayers);
            }
            toSelect = new ArrayList<>();
        }
        return selected;
    }

    public HashMap<Integer,ArrayList<PlayerDatabase>> selectAll(){
        HashMap<Integer,ArrayList<PlayerDatabase>> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Player.TABLE_NAME,null,null,null,null,null,null);

        ArrayList<PlayerDatabase> players = new ArrayList<>();
        PlayerDatabase player;

        ArrayList<Integer> headIDs = new ArrayList<>(),headMainIDs = new ArrayList<>();
        ArrayList<Integer> headSub1IDs = new ArrayList<>(), headSub2IDs = new ArrayList<>(), headSub3IDs= new ArrayList<>();
        int headID, headMainID, headSub1ID, headSub2ID, headSub3ID;

        ArrayList<Integer> clothesIDs = new ArrayList<>(), clothesMainIDs = new ArrayList<>();
        ArrayList<Integer> clothesSub1IDs = new ArrayList<>(), clothesSub2IDs = new ArrayList<>(), clothesSub3IDs = new ArrayList<>();
        int clothesID, clothesMainID, clothesSub1ID, clothesSub2ID, clothesSub3ID;

        ArrayList<Integer> shoeIDs = new ArrayList<>(), shoeMainIDs = new ArrayList<>();
        ArrayList<Integer> shoeSub1IDs = new ArrayList<>(), shoeSub2IDs = new ArrayList<>(), shoeSub3IDs = new ArrayList<>();
        int shoeID, shoeMainID, shoeSub1ID, shoeSub2ID, shoeSub3ID;

        ArrayList<Integer> weaponIDs = new ArrayList<>();
        int weaponID;

        if(cursor.moveToFirst()){
            do{
                player = new PlayerDatabase();
                player.battleID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_BATTLE));
                player.playerType = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_TYPE));
                player.battleType = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_MODE));
                player.player = new Player();
                player.player.points = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_POINT));
                player.player.kills = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_KILL));
                player.player.assists = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ASSIST));
                player.player.deaths = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_DEATH));
                player.player.special = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SPECIAL));


                User user = new User();
                user.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_NAME));
                user.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_ID));
                user.rank = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_LEVEL));
                Rank rank = new Rank();
                rank.rank = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_RANK));
                rank.sPlus = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_S_NUM));
                user.udamae = rank;
                SplatfestGrade splatfestGrade = new SplatfestGrade();
                splatfestGrade.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_FES_GRADE));
                user.grade = splatfestGrade;
                user.playerType = new PlayerType();
                user.playerType.species = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SPECIES));
                user.playerType.style = cursor.getString(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_STYLE));

                //Head

                headID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD));
                gearManager.addToSelect(headID,"head");
                headIDs.add(headID);

                headMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_MAIN));
                skillManager.addToSelect(headMainID);
                headMainIDs.add(headMainID);

                headSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_1));
                skillManager.addToSelect(headSub1ID);
                headSub1IDs.add(headSub1ID);

                headSub2ID =cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_2));
                skillManager.addToSelect(headSub2ID);
                headSub2IDs.add(headSub2ID);

                headSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_HEAD_SUB_3));
                skillManager.addToSelect(headSub3ID);
                headSub3IDs.add(headSub3ID);

                //Clothes

                clothesID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES));
                gearManager.addToSelect(clothesID,"clothes");
                clothesIDs.add(clothesID);

                clothesMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_MAIN));
                skillManager.addToSelect(clothesMainID);
                clothesMainIDs.add(clothesMainID);

                clothesSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1));
                skillManager.addToSelect(clothesSub1ID);
                clothesSub1IDs.add(clothesSub1ID);

                clothesSub2ID =cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2));
                skillManager.addToSelect(clothesSub2ID);
                clothesSub2IDs.add(clothesSub2ID);

                clothesSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3));
                skillManager.addToSelect(clothesSub3ID);
                clothesSub3IDs.add(clothesSub3ID);

                //Shoes

                shoeID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES));
                gearManager.addToSelect(shoeID,"shoes");
                shoeIDs.add(clothesID);

                shoeMainID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_MAIN));
                skillManager.addToSelect(shoeMainID);
                shoeMainIDs.add(shoeMainID);

                shoeSub1ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_1));
                skillManager.addToSelect(shoeSub1ID);
                shoeSub1IDs.add(shoeSub1ID);

                shoeSub2ID =cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_2));
                skillManager.addToSelect(shoeSub2ID);
                shoeSub2IDs.add(shoeSub2ID);

                shoeSub3ID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_SHOES_SUB_3));
                skillManager.addToSelect(shoeSub3ID);
                shoeSub3IDs.add(shoeSub3ID);

                //Weapon

                weaponID = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Player.COLUMN_WEAPON));
                weaponManager.addToSelect(weaponID);
                weaponIDs.add(weaponID);

                player.player.user = user;

                players.add(player);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        ArrayList<HashMap<Integer,Gear>> gearHashMap = gearManager.select();
        HashMap<Integer,Skill> skillHashMap = skillManager.select();
        HashMap<Integer,Weapon> weaponHashMap = weaponManager.select();

        GearSkills headSkills,clothesSkills,shoeSkills;

        ArrayList<PlayerDatabase> battlePlayers;

        for(int i=0;i<players.size();i++){
            player = players.get(i);

            player.player.user.head = gearHashMap.get(0).get(headIDs.get(i));

            headSkills = new GearSkills();
            headSkills.main = skillHashMap.get(headMainIDs.get(i));
            headSkills.subs = new ArrayList<>();
            headSkills.subs.add(skillHashMap.get(headSub1IDs.get(i)));
            headSkills.subs.add(skillHashMap.get(headSub2IDs.get(i)));
            headSkills.subs.add(skillHashMap.get(headSub3IDs.get(i)));
            player.player.user.headSkills = headSkills;

            player.player.user.clothes = gearHashMap.get(1).get(clothesIDs.get(i));

            clothesSkills = new GearSkills();
            clothesSkills.main = skillHashMap.get(clothesMainIDs.get(i));
            clothesSkills.subs = new ArrayList<>();
            clothesSkills.subs.add(skillHashMap.get(clothesSub1IDs.get(i)));
            clothesSkills.subs.add(skillHashMap.get(clothesSub2IDs.get(i)));
            clothesSkills.subs.add(skillHashMap.get(clothesSub3IDs.get(i)));
            player.player.user.clothesSkills = clothesSkills;

            player.player.user.shoes = gearHashMap.get(2).get(shoeIDs.get(i));

            shoeSkills = new GearSkills();
            shoeSkills.main = skillHashMap.get(shoeMainIDs.get(i));
            shoeSkills.subs = new ArrayList<>();
            shoeSkills.subs.add(skillHashMap.get(shoeSub1IDs.get(i)));
            shoeSkills.subs.add(skillHashMap.get(shoeSub2IDs.get(i)));
            shoeSkills.subs.add(skillHashMap.get(shoeSub3IDs.get(i)));
            player.player.user.shoeSkills = shoeSkills;

            player.player.user.weapon = weaponHashMap.get(weaponIDs.get(i));

            if(selected.containsKey(player.battleID)){
                battlePlayers = selected.get(player.battleID);
            }else{
                battlePlayers = new ArrayList<>();
            }
            battlePlayers.add(player);
            selected.put(player.battleID,battlePlayers);
        }
        toSelect = new ArrayList<>();
        return selected;
    }

    public ArrayList<Battle> selectStats(int itemID, String itemType){

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        BattleManager battleManager = new BattleManager(context);

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