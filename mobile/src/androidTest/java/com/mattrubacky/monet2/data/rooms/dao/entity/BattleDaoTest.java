package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.BattlePlayer;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.entity.DayRoom;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class BattleDaoTest {
    private TestDatabase db;
    private BattleDao battleDao;
    private Battle battleRegular,battleRanked,battleSplatfest;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();

        DayDao dayDao = db.getDayDao();
        battleDao = db.getBattleDao();
        SplatfestDao splatfestDao = db.getSplatfestDao();
        StageDao stageDao = db.getStageDao();
        PlayerDao playerDao = db.getPlayerDao();
        ClosetDao closetDao = db.getClosetDao();
        WeaponDao weaponDao = db.getWeaponDao();
        SubDao subDao = db.getSubDao();
        SpecialDao specialDao = db.getSpecialDao();
        GearDao gearDao = db.getGearDao();
        BrandDao brandDao = db.getBrandDao();
        SkillDao skillDao = db.getSkillDao();

        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();

            battleSplatfest = gson.fromJson(deserializedHelper.getJSON("battle_splatfest.json"),Battle.class);
            Splatfest splatfest = gson.fromJson(deserializedHelper.getJSON("splatfest_battle.json"),Splatfest.class);

            splatfestDao.insertSplatfest(splatfest,stageDao);
            battleDao.insertBattle(battleSplatfest,dayDao,stageDao,playerDao,closetDao,weaponDao,subDao,specialDao,gearDao,brandDao,skillDao);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void deleteDB(){
        db.close();
    }

    @Test
    public void insertSplatfest(){
        LiveData<BattlePlayer> battlePlayerLiveData = battleDao.select(battleSplatfest.id);
        battlePlayerLiveData.observeForever(new Observer<BattlePlayer>() {
            @Override
            public void onChanged(BattlePlayer battlePlayer) {
                Battle pulledBattle = battlePlayer.toDeserialized();
                assertThat(pulledBattle.id).isEqualTo(battleSplatfest.id);
                assertThat(pulledBattle.rule.key).isEqualTo(battleSplatfest.rule.key);
                assertThat(pulledBattle.type).isEqualTo(battleSplatfest.type);
                assertThat(pulledBattle.result.key).isEqualTo(battleSplatfest.result.key);
                assertThat(pulledBattle.start).isEqualTo(battleSplatfest.start);

                assertThat(pulledBattle.myTeamPercent).isEqualTo(battleSplatfest.myTeamPercent);
                assertThat(pulledBattle.otherTeamPercent).isEqualTo(battleSplatfest.otherTeamPercent);
                assertThat(pulledBattle.fesMode.key).isEqualTo(battleSplatfest.fesMode.key);
                assertThat(pulledBattle.eventType.key).isEqualTo(battleSplatfest.eventType.key);
                assertThat(pulledBattle.uniformBonus).isEqualTo(battleSplatfest.uniformBonus);
                assertThat(pulledBattle.myFesPower).isEqualTo(battleSplatfest.myFesPower);
                assertThat(pulledBattle.myConsecutiveWins).isEqualTo(battleSplatfest.myConsecutiveWins);
                assertThat(pulledBattle.myTeamName).isEqualTo(battleSplatfest.myTeamName);
                assertThat(pulledBattle.otherFesPower).isEqualTo(battleSplatfest.otherFesPower);
                assertThat(pulledBattle.otherConsecutiveWins).isEqualTo(battleSplatfest.otherConsecutiveWins);
                assertThat(pulledBattle.otherTeamName).isEqualTo(battleSplatfest.otherTeamName);
                assertThat(pulledBattle.fesPoint).isEqualTo(battleSplatfest.fesPoint);
                assertThat(pulledBattle.grade.name).isEqualTo(battleSplatfest.grade.name);
                assertThat(pulledBattle.myTheme.key).isEqualTo(battleSplatfest.myTheme.key);
                assertThat(pulledBattle.myTheme.name).isEqualTo(battleSplatfest.myTheme.name);
                assertThat(pulledBattle.myTheme.color.getColor()).isEqualTo(battleSplatfest.myTheme.color.getColor());
                assertThat(pulledBattle.otherTheme.key).isEqualTo(battleSplatfest.otherTheme.key);
                assertThat(pulledBattle.otherTheme.name).isEqualTo(battleSplatfest.otherTheme.name);
                assertThat(pulledBattle.otherTheme.color.getColor()).isEqualTo(battleSplatfest.otherTheme.color.getColor());

                assertThat(pulledBattle.stage.id).isEqualTo(battleSplatfest.stage.id);
                assertThat(pulledBattle.stage.name).isEqualTo(battleSplatfest.stage.name);
                assertThat(pulledBattle.stage.url).isEqualTo(battleSplatfest.stage.url);

                assertThat(pulledBattle.user.points).isEqualTo(battleSplatfest.user.points);
                assertThat(pulledBattle.user.kills).isEqualTo(battleSplatfest.user.kills);
                assertThat(pulledBattle.user.assists).isEqualTo(battleSplatfest.user.assists);
                assertThat(pulledBattle.user.deaths).isEqualTo(battleSplatfest.user.deaths);
                assertThat(pulledBattle.user.special).isEqualTo(battleSplatfest.user.special);

                assertThat(pulledBattle.user.user.id).isEqualTo(battleSplatfest.user.user.id);
                assertThat(pulledBattle.user.user.name).isEqualTo(battleSplatfest.user.user.name);
                assertThat(pulledBattle.user.user.rank).isEqualTo(battleSplatfest.user.user.rank);
                assertThat(pulledBattle.user.user.grade.name).isEqualTo(battleSplatfest.user.user.grade.name);
                assertThat(pulledBattle.user.user.starRank).isEqualTo(battleSplatfest.user.user.starRank);
                assertThat(pulledBattle.user.user.playerType.style).isEqualTo(battleSplatfest.user.user.playerType.style);
                assertThat(pulledBattle.user.user.playerType.species).isEqualTo(battleSplatfest.user.user.playerType.species);

                assertThat(pulledBattle.user.user.weapon.id).isEqualTo(battleSplatfest.user.user.weapon.id);
                assertThat(pulledBattle.user.user.weapon.name).isEqualTo(battleSplatfest.user.user.weapon.name);
                assertThat(pulledBattle.user.user.weapon.url).isEqualTo(battleSplatfest.user.user.weapon.url);

                assertThat(pulledBattle.user.user.weapon.sub.id).isEqualTo(battleSplatfest.user.user.weapon.sub.id);
                assertThat(pulledBattle.user.user.weapon.sub.name).isEqualTo(battleSplatfest.user.user.weapon.sub.name);
                assertThat(pulledBattle.user.user.weapon.sub.url).isEqualTo(battleSplatfest.user.user.weapon.sub.url);

                assertThat(pulledBattle.user.user.weapon.special.id).isEqualTo(battleSplatfest.user.user.weapon.special.id);
                assertThat(pulledBattle.user.user.weapon.special.name).isEqualTo(battleSplatfest.user.user.weapon.special.name);
                assertThat(pulledBattle.user.user.weapon.special.url).isEqualTo(battleSplatfest.user.user.weapon.special.url);
            }
        });
    }

    @Test
    public void selectAll(){
        LiveData<List<BattlePlayer>> battlePlayerLiveData = battleDao.selectAll();
        battlePlayerLiveData.observeForever(new Observer<List<BattlePlayer>>() {
            @Override
            public void onChanged(List<BattlePlayer> battlePlayers) {
                assertThat(battlePlayers).isNotEmpty();
            }
        });
    }

    @Test
    public void selectDay(){
        LiveData<List<BattlePlayer>> battlePlayerLiveData = battleDao.selectDay(DayRoom.generateId(battleSplatfest.start),DayRoom.generateEnd(battleSplatfest.start));
        battlePlayerLiveData.observeForever(new Observer<List<BattlePlayer>>() {
            @Override
            public void onChanged(List<BattlePlayer> battlePlayers) {
                assertThat(battlePlayers).isNotEmpty();
            }
        });
    }
}
