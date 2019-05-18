package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.RewardGearCombo;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SalmonGearDaoTest {
    private TestDatabase db;
    private SalmonGearDao salmonGearDao;
    private RewardGear salmonHead,salmonClothes,salmonShoe;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();

        salmonGearDao=db.getSalmonGearDao();
        GearDao gearDao = db.getGearDao();
        BrandDao brandDao = db.getBrandDao();
        SkillDao skillDao = db.getSkillDao();

        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            salmonHead = gson.fromJson(deserializedHelper.getJSON("head_reward.json"),RewardGear.class);

            salmonClothes = new RewardGear();

            salmonShoe = new RewardGear();

            salmonGearDao.insertGear(salmonHead, gearDao, brandDao, skillDao);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void deleteDB(){
        db.close();
    }

    @Test
    public void insert(){
        RewardGearCombo pulledGear = salmonGearDao.selectCurrentGear(RewardGear.generateId(salmonHead.available));
        RewardGear resultGear = pulledGear.toDeserialized();
        assertThat(salmonHead.month).isEqualTo(resultGear.month);
        assertThat(salmonHead.available).isEqualTo(resultGear.available);
        assertThat(salmonHead.gear.id).isEqualTo(resultGear.gear.id);
        assertThat(salmonHead.gear.kind).isEqualTo(resultGear.gear.kind);
        assertThat(salmonHead.gear.name).isEqualTo(resultGear.gear.name);
        assertThat(salmonHead.gear.url).isEqualTo(resultGear.gear.url);
        assertThat(salmonHead.gear.rarity).isEqualTo(resultGear.gear.rarity);
        assertThat(salmonHead.gear.brand.id).isEqualTo(resultGear.gear.brand.id);
        assertThat(salmonHead.gear.brand.name).isEqualTo(resultGear.gear.brand.name);
        assertThat(salmonHead.gear.brand.url).isEqualTo(resultGear.gear.brand.url);
    }
}
