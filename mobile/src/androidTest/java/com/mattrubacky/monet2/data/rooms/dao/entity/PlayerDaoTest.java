package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.data.rooms.entity.PlayerRoom;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import static com.google.common.truth.Truth.assertThat;

public class PlayerDaoTest {
    private TestDatabase db;
    private Context context;
    private PlayerDao playerDao;
    private WeaponDao weaponDao;
    private SubDao subDao;
    private SpecialDao specialDao;
    private GearDao gearDao;
    private BrandDao brandDao;
    private SkillDao skillDao;
    private Player player;
    private Battle splatfest;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        weaponDao = db.getWeaponDao();
        subDao = db.getSubDao();
        specialDao = db.getSpecialDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            splatfest = gson.fromJson(deserializedHelper.getJSON("battle_splatfest.json"), Battle.class);
            player = splatfest.user;
            playerDao.insertPlayer(new PlayerRoom(splatfest.id,splatfest.user,0,splatfest.type),weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            for(Player player : splatfest.myTeam){
                playerDao.insertPlayer(new PlayerRoom(splatfest.id,player,1,splatfest.type),weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
            for(Player player : splatfest.otherTeam){
                playerDao.insertPlayer(new PlayerRoom(splatfest.id,player,2,splatfest.type),weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertSplatfest(){
        PlayerRoom pulledPlayer = playerDao.selectPlayerFromBattle(splatfest.id);
        assertThat(pulledPlayer.userId).isEqualTo(splatfest.user.user.uniqueId);
        assertThat(pulledPlayer.name).isEqualTo(splatfest.user.user.name);
        assertThat(pulledPlayer.level).isEqualTo(splatfest.user.user.rank);
        assertThat(pulledPlayer.fesGrade.name).isEqualTo(splatfest.user.user.grade.name);
        assertThat(pulledPlayer.starLevel).isEqualTo(splatfest.user.user.starRank);
        assertThat(pulledPlayer.style).isEqualTo(splatfest.user.user.playerType.style);
        assertThat(pulledPlayer.species).isEqualTo(splatfest.user.user.playerType.species);
        assertThat(pulledPlayer.point).isEqualTo(splatfest.user.points);
        assertThat(pulledPlayer.kill).isEqualTo(splatfest.user.kills);
        assertThat(pulledPlayer.assist).isEqualTo(splatfest.user.assists);
        assertThat(pulledPlayer.death).isEqualTo(splatfest.user.deaths);
        assertThat(pulledPlayer.special).isEqualTo(splatfest.user.special);
    }
}
