package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.mattrubacky.monet2.deserialized.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/17/2017.
 */

class PlayerManager {
    Context context;
    private ArrayList<PlayerDatabase> toInsert;
    private ArrayList<Integer> toSelect;
    private GearManager gearManager;
    private SkillManager skillManager;
    private WeaponManager weaponManager;


    public PlayerManager(Context context){
        this.context = context;
        toSelect = new ArrayList<>();
        toInsert = new ArrayList<>();
        gearManager = new GearManager(context);
        skillManager = new SkillManager(context);
        weaponManager = new WeaponManager(context);
    }

    public void addToInsert(Player player,String mode,int battleId,int type){
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.player = player;
        playerDatabase.battleType = mode;
        playerDatabase.battleID = battleId;
        playerDatabase.playerType = type;
        toInsert.add(playerDatabase);

        gearManager.addToInsert(player.user.head);
        skillManager.addToInsert(player.user.headSkills);

        gearManager.addToInsert(player.user.clothes);
        skillManager.addToInsert(player.user.clothesSkills);

        gearManager.addToInsert(player.user.shoes);
        skillManager.addToInsert(player.user.shoeSkills);

        weaponManager.addToInsert(player.user.weapon);
    }

    public void addToSelect(int id){
        toSelect.add(id);
    }

    public void insert(){
        gearManager.insert();
        weaponManager.insert();
        skillManager.insert();
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

            ContentValues values;

            PlayerDatabase player;

            String whereClause = SplatnetContract.Battle._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            for (int i = 0; i < toInsert.size(); i++) {
                player = toInsert.get(i);
                values = new ContentValues();

                args = new String[] {String.valueOf(player.battleID)};
                cursor = database.query(SplatnetContract.Battle.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {

                    values.put(SplatnetContract.Player.COLUMN_MODE, player.battleType);
                    values.put(SplatnetContract.Player.COLUMN_BATTLE, player.battleID);
                    values.put(SplatnetContract.Player.COLUMN_TYPE, player.playerType);
                    values.put(SplatnetContract.Player.COLUMN_ID, player.player.user.id);
                    values.put(SplatnetContract.Player.COLUMN_NAME, player.player.user.name);
                    values.put(SplatnetContract.Player.COLUMN_LEVEL, player.player.user.rank);
                    values.put(SplatnetContract.Player.COLUMN_POINT, player.player.points);
                    values.put(SplatnetContract.Player.COLUMN_KILL, player.player.kills);
                    values.put(SplatnetContract.Player.COLUMN_ASSIST, player.player.assists);
                    values.put(SplatnetContract.Player.COLUMN_DEATH, player.player.deaths);
                    values.put(SplatnetContract.Player.COLUMN_SPECIAL, player.player.special);

                    switch (player.battleType) {
                        case "gachi":
                            values.put(SplatnetContract.Player.COLUMN_RANK, player.player.user.udamae.rank);
                            values.put(SplatnetContract.Player.COLUMN_S_NUM, player.player.user.udamae.sPlus);
                            break;
                        case "fes":
                            values.put(SplatnetContract.Player.COLUMN_FES_GRADE, player.player.user.grade.name);
                    }


                    values.put(SplatnetContract.Player.COLUMN_WEAPON, player.player.user.weapon.id);


                    values.put(SplatnetContract.Player.COLUMN_HEAD, player.player.user.head.id);
                    values.put(SplatnetContract.Player.COLUMN_HEAD_MAIN, player.player.user.headSkills.main.id);

                    //Insert sub skills
                    if (player.player.user.headSkills.subs.get(0) != null) {
                        values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_1, player.player.user.headSkills.subs.get(0).id);
                        if (player.player.user.headSkills.subs.get(1) != null) {
                            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2, player.player.user.headSkills.subs.get(1).id);
                            if (player.player.user.headSkills.subs.get(2) != null) {
                                values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3, player.player.user.headSkills.subs.get(2).id);
                            } else {
                                values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3, -1);
                            }
                        } else {

                            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2, -1);
                            values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3, -1);
                        }
                    } else {
                        values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_1, -1);
                        values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_2, -1);
                        values.put(SplatnetContract.Player.COLUMN_HEAD_SUB_3, -1);
                    }


                    values.put(SplatnetContract.Player.COLUMN_CLOTHES, player.player.user.clothes.id);
                    values.put(SplatnetContract.Player.COLUMN_CLOTHES_MAIN, player.player.user.clothesSkills.main.id);

                    //Insert sub skills
                    if (player.player.user.clothesSkills.subs.get(0) != null) {
                        values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1, player.player.user.clothesSkills.subs.get(0).id);
                        if (player.player.user.clothesSkills.subs.get(1) != null) {
                            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2, player.player.user.clothesSkills.subs.get(1).id);
                            if (player.player.user.clothesSkills.subs.get(2) != null) {
                                values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3, player.player.user.clothesSkills.subs.get(2).id);
                            } else {
                                values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3, -1);
                            }
                        } else {

                            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2, -1);
                            values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3, -1);
                        }
                    } else {
                        values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_1, -1);
                        values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_2, -1);
                        values.put(SplatnetContract.Player.COLUMN_CLOTHES_SUB_3, -1);
                    }

                    values.put(SplatnetContract.Player.COLUMN_SHOES, player.player.user.shoes.id);
                    values.put(SplatnetContract.Player.COLUMN_SHOES_MAIN, player.player.user.shoeSkills.main.id);

                    //Insert sub skills
                    if (player.player.user.shoeSkills.subs.get(0) != null) {
                        values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_1, player.player.user.shoeSkills.subs.get(0).id);
                        if (player.player.user.shoeSkills.subs.get(1) != null) {

                            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2, player.player.user.shoeSkills.subs.get(1).id);
                            if (player.player.user.shoeSkills.subs.get(2) != null) {

                                values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3, player.player.user.shoeSkills.subs.get(2).id);
                            } else {
                                values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3, -1);
                            }
                        } else {

                            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2, -1);
                            values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3, -1);
                        }
                    } else {
                        values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_1, -1);
                        values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_2, -1);
                        values.put(SplatnetContract.Player.COLUMN_SHOES_SUB_3, -1);
                    }


                    database.insert(SplatnetContract.Player.TABLE_NAME, null, values);
                }
            }
            database.close();
            toInsert = new ArrayList<>();
        }
    }

    public HashMap<Integer,ArrayList<PlayerDatabase>> select(){
        HashMap<Integer,ArrayList<PlayerDatabase>> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Player.COLUMN_BATTLE+" = ?");

        //build the select statement
        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Player.COLUMN_BATTLE+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Player.TABLE_NAME,null,whereClause,args,null,null,null);

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


    public ArrayList<Player> selectStats(int itemID,String itemType){
        ArrayList<Player> selected = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[2];
        args[0] = String.valueOf(0);
        args[1] = String.valueOf(itemID);

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Player.COLUMN_TYPE+" = ?");

        //build the select statement
        switch (itemType){
            case "weapon":
                builder.append(SplatnetContract.Player.COLUMN_WEAPON);
                break;
            case "head":
                builder.append(SplatnetContract.Player.COLUMN_HEAD);
                break;
            case "clothes":
                builder.append(SplatnetContract.Player.COLUMN_CLOTHES);
                break;
            case "shoes":
                builder.append(SplatnetContract.Player.COLUMN_SHOES);
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Player.TABLE_NAME,null,whereClause,args,null,null,null);

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

            selected.add(player.player);
        }
        return selected;
    }
}