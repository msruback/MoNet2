package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.PlayerWeapon;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Player;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.data.entity.PlayerRoom;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import static com.google.common.truth.Truth.assertThat;

public class PlayerDaoTest {
    private TestDatabase db;
    private PlayerDao playerDao;
    private Battle splatfestBattle,rankedBattle,regularBattle;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        BattleDao battleDao = db.getBattleDao();
        DayDao dayDao = db.getDayDao();
        SplatfestDao splatfestDao = db.getSplatfestDao();
        StageDao stageDao = db.getStageDao();
        playerDao = db.getPlayerDao();
        ClosetDao closetDao = db.getClosetDao();
        GearDao gearDao = db.getGearDao();
        BrandDao brandDao = db.getBrandDao();
        SkillDao skillDao = db.getSkillDao();
        WeaponDao weaponDao = db.getWeaponDao();
        SubDao subDao = db.getSubDao();
        SpecialDao specialDao = db.getSpecialDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            splatfestBattle = gson.fromJson(deserializedHelper.getJSON("battle_splatfest.json"), Battle.class);
            Splatfest splatfest = gson.fromJson(deserializedHelper.getJSON("splatfest_battle.json"),Splatfest.class);
            splatfestDao.insertSplatfest(splatfest,stageDao);
            battleDao.insertBattle(splatfestBattle,dayDao,stageDao,playerDao,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);
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
        LiveData<PlayerWeapon> playerRoomLiveData = playerDao.selectPlayerFromBattle(splatfestBattle.id);
        playerRoomLiveData.observeForever(new Observer<PlayerWeapon>() {
            @Override
            public void onChanged(PlayerWeapon playerWeapon) {
                Player pulledPlayer = playerWeapon.toDeserialized();
                assertThat(pulledPlayer.points).isEqualTo(splatfestBattle.user.points);
                assertThat(pulledPlayer.kills).isEqualTo(splatfestBattle.user.kills);
                assertThat(pulledPlayer.assists).isEqualTo(splatfestBattle.user.assists);
                assertThat(pulledPlayer.deaths).isEqualTo(splatfestBattle.user.deaths);
                assertThat(pulledPlayer.special).isEqualTo(splatfestBattle.user.special);

                assertThat(pulledPlayer.user.id).isEqualTo(splatfestBattle.user.user.id);
                assertThat(pulledPlayer.user.name).isEqualTo(splatfestBattle.user.user.name);
                assertThat(pulledPlayer.user.rank).isEqualTo(splatfestBattle.user.user.rank);
                assertThat(pulledPlayer.user.grade.name).isEqualTo(splatfestBattle.user.user.grade.name);
                assertThat(pulledPlayer.user.starRank).isEqualTo(splatfestBattle.user.user.starRank);
                assertThat(pulledPlayer.user.playerType.style).isEqualTo(splatfestBattle.user.user.playerType.style);
                assertThat(pulledPlayer.user.playerType.species).isEqualTo(splatfestBattle.user.user.playerType.species);

                assertThat(pulledPlayer.user.weapon.id).isEqualTo(splatfestBattle.user.user.weapon.id);
                assertThat(pulledPlayer.user.weapon.name).isEqualTo(splatfestBattle.user.user.weapon.name);
                assertThat(pulledPlayer.user.weapon.url).isEqualTo(splatfestBattle.user.user.weapon.url);

                assertThat(pulledPlayer.user.weapon.sub.id).isEqualTo(splatfestBattle.user.user.weapon.sub.id);
                assertThat(pulledPlayer.user.weapon.sub.name).isEqualTo(splatfestBattle.user.user.weapon.sub.name);
                assertThat(pulledPlayer.user.weapon.sub.url).isEqualTo(splatfestBattle.user.user.weapon.sub.url);

                assertThat(pulledPlayer.user.weapon.special.id).isEqualTo(splatfestBattle.user.user.weapon.special.id);
                assertThat(pulledPlayer.user.weapon.special.name).isEqualTo(splatfestBattle.user.user.weapon.special.name);
                assertThat(pulledPlayer.user.weapon.special.url).isEqualTo(splatfestBattle.user.user.weapon.special.url);
            }
        });
    }

    @Test
    public void splatfestTeammates(){
        final LiveData<List<PlayerWeapon>> playerLiveData = playerDao.selectPlayersFromBattle(splatfestBattle.id,1);
        playerLiveData.observeForever(new Observer<List<PlayerWeapon>>() {
            @Override
            public void onChanged(List<PlayerWeapon> playerRooms) {
                for(PlayerWeapon playerRoom:playerRooms){
                    Player pulledPlayer = playerRoom.toDeserialized();
                    for(Player player:splatfestBattle.myTeam){
                        if(player.user.id.equals(pulledPlayer.user.id)) {
                            assertThat(pulledPlayer.points).isEqualTo(splatfestBattle.user.points);
                            assertThat(pulledPlayer.kills).isEqualTo(splatfestBattle.user.kills);
                            assertThat(pulledPlayer.assists).isEqualTo(splatfestBattle.user.assists);
                            assertThat(pulledPlayer.deaths).isEqualTo(splatfestBattle.user.deaths);
                            assertThat(pulledPlayer.special).isEqualTo(splatfestBattle.user.special);

                            assertThat(pulledPlayer.user.uniqueId).isEqualTo(splatfestBattle.user.user.uniqueId);
                            assertThat(pulledPlayer.user.name).isEqualTo(splatfestBattle.user.user.name);
                            assertThat(pulledPlayer.user.rank).isEqualTo(splatfestBattle.user.user.rank);
                            assertThat(pulledPlayer.user.grade.name).isEqualTo(splatfestBattle.user.user.grade.name);
                            assertThat(pulledPlayer.user.starRank).isEqualTo(splatfestBattle.user.user.starRank);
                            assertThat(pulledPlayer.user.playerType.style).isEqualTo(splatfestBattle.user.user.playerType.style);
                            assertThat(pulledPlayer.user.playerType.species).isEqualTo(splatfestBattle.user.user.playerType.species);

                            assertThat(pulledPlayer.user.weapon.id).isEqualTo(splatfestBattle.user.user.weapon.id);
                            assertThat(pulledPlayer.user.weapon.name).isEqualTo(splatfestBattle.user.user.weapon.name);
                            assertThat(pulledPlayer.user.weapon.url).isEqualTo(splatfestBattle.user.user.weapon.url);

                            assertThat(pulledPlayer.user.weapon.sub.id).isEqualTo(splatfestBattle.user.user.weapon.sub.id);
                            assertThat(pulledPlayer.user.weapon.sub.name).isEqualTo(splatfestBattle.user.user.weapon.sub.name);
                            assertThat(pulledPlayer.user.weapon.sub.url).isEqualTo(splatfestBattle.user.user.weapon.sub.url);

                            assertThat(pulledPlayer.user.weapon.special.id).isEqualTo(splatfestBattle.user.user.weapon.special.id);
                            assertThat(pulledPlayer.user.weapon.special.name).isEqualTo(splatfestBattle.user.user.weapon.special.name);
                            assertThat(pulledPlayer.user.weapon.special.url).isEqualTo(splatfestBattle.user.user.weapon.special.url);
                        }
                    }
                }
            }
        });
    }
    @Test
    public void splatfestOpponents(){
        final LiveData<List<PlayerWeapon>> playerLiveData = playerDao.selectPlayersFromBattle(splatfestBattle.id,2);
        playerLiveData.observeForever(new Observer<List<PlayerWeapon>>() {
            @Override
            public void onChanged(List<PlayerWeapon> playerRooms) {
                for(PlayerWeapon playerRoom:playerRooms){
                    Player pulledPlayer = playerRoom.toDeserialized();
                    for(Player player:splatfestBattle.myTeam){
                        if(player.user.id.equals(pulledPlayer.user.id)) {
                            assertThat(pulledPlayer.points).isEqualTo(splatfestBattle.user.points);
                            assertThat(pulledPlayer.kills).isEqualTo(splatfestBattle.user.kills);
                            assertThat(pulledPlayer.assists).isEqualTo(splatfestBattle.user.assists);
                            assertThat(pulledPlayer.deaths).isEqualTo(splatfestBattle.user.deaths);
                            assertThat(pulledPlayer.special).isEqualTo(splatfestBattle.user.special);

                            assertThat(pulledPlayer.user.uniqueId).isEqualTo(splatfestBattle.user.user.uniqueId);
                            assertThat(pulledPlayer.user.name).isEqualTo(splatfestBattle.user.user.name);
                            assertThat(pulledPlayer.user.rank).isEqualTo(splatfestBattle.user.user.rank);
                            assertThat(pulledPlayer.user.grade.name).isEqualTo(splatfestBattle.user.user.grade.name);
                            assertThat(pulledPlayer.user.starRank).isEqualTo(splatfestBattle.user.user.starRank);
                            assertThat(pulledPlayer.user.playerType.style).isEqualTo(splatfestBattle.user.user.playerType.style);
                            assertThat(pulledPlayer.user.playerType.species).isEqualTo(splatfestBattle.user.user.playerType.species);

                            assertThat(pulledPlayer.user.weapon.id).isEqualTo(splatfestBattle.user.user.weapon.id);
                            assertThat(pulledPlayer.user.weapon.name).isEqualTo(splatfestBattle.user.user.weapon.name);
                            assertThat(pulledPlayer.user.weapon.url).isEqualTo(splatfestBattle.user.user.weapon.url);

                            assertThat(pulledPlayer.user.weapon.sub.id).isEqualTo(splatfestBattle.user.user.weapon.sub.id);
                            assertThat(pulledPlayer.user.weapon.sub.name).isEqualTo(splatfestBattle.user.user.weapon.sub.name);
                            assertThat(pulledPlayer.user.weapon.sub.url).isEqualTo(splatfestBattle.user.user.weapon.sub.url);

                            assertThat(pulledPlayer.user.weapon.special.id).isEqualTo(splatfestBattle.user.user.weapon.special.id);
                            assertThat(pulledPlayer.user.weapon.special.name).isEqualTo(splatfestBattle.user.user.weapon.special.name);
                            assertThat(pulledPlayer.user.weapon.special.url).isEqualTo(splatfestBattle.user.user.weapon.special.url);
                        }
                    }
                }
            }
        });
    }
}
