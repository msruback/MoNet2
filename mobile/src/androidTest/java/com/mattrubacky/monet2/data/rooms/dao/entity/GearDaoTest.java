package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class GearDaoTest {

    private TestDatabase db;
    private Context context;
    private GearDao gearDao;
    private BrandDao brandDao;
    private SkillDao skillDao;
    private Gear head,clothes,shoes;

    @Before
    public void createDB(){
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        gearDao = db.getGearDao();
        brandDao = db.getBrandDao();
        skillDao = db.getSkillDao();
        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            head = gson.fromJson(deserializedHelper.getJSON("gear_head.json"),Gear.class);
            clothes = gson.fromJson(deserializedHelper.getJSON("gear_clothes.json"),Gear.class);
            shoes = gson.fromJson(deserializedHelper.getJSON("gear_shoes.json"),Gear.class);
            skillDao.insertSkill(head.brand.skill);
            skillDao.insertSkill(clothes.brand.skill);
            skillDao.insertSkill(shoes.brand.skill);

            brandDao.insertBrand(head.brand);
            brandDao.insertBrand(clothes.brand);
            brandDao.insertBrand(shoes.brand);

            head.generatedId = Gear.generateId(head.kind,head.id);
            clothes.generatedId = Gear.generateId(clothes.kind,clothes.id);
            shoes.generatedId = Gear.generateId(shoes.kind,shoes.id);

            gearDao.insertGear(head,false);
            gearDao.insertGear(clothes,false);
            gearDao.insertGear(shoes,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDB(){
        gearDao.delete(head);
        gearDao.delete(clothes);
        gearDao.delete(shoes);

        brandDao.delete(head.brand);
        brandDao.delete(clothes.brand);
        brandDao.delete(shoes.brand);

        skillDao.delete(head.brand.skill);
        skillDao.delete(clothes.brand.skill);
        skillDao.delete(shoes.brand.skill);
        db.close();
    }

    @Test
    public void insert(){
        Gear pulledGear = gearDao.select(head.generatedId);
        assertThat(pulledGear.generatedId).isEqualTo(head.generatedId);
        assertThat(pulledGear.id).isEqualTo(head.id);
        assertThat(pulledGear.name).isEqualTo(head.name);
        assertThat(pulledGear.kind).isEqualTo(head.kind);
        assertThat(pulledGear.url).isEqualTo(head.url);
        assertThat(pulledGear.rarity).isEqualTo(head.rarity);
        assertThat(pulledGear.brand.id).isEqualTo(head.brand.id);
    }

    @Test
    public void update(){
        Gear editedGear = new Gear(head);
        assertThat(head.rarity).isEqualTo(2);
        editedGear.rarity ++;
        assertThat(head.rarity).isEqualTo(2);
        gearDao.insertGear(editedGear,true);
        Gear pulledGear = gearDao.select(head.generatedId);
        assertThat(pulledGear.rarity).isNotEqualTo(head.rarity);
        assertThat(pulledGear.rarity).isEqualTo(editedGear.rarity);
        gearDao.update(head);
    }

    @Test
    public void selectSplatnet(){
        Gear pulledGear = gearDao.select(head.id,head.kind);
        assertThat(pulledGear.generatedId).isEqualTo(head.generatedId);
        assertThat(pulledGear.id).isEqualTo(head.id);
        assertThat(pulledGear.name).isEqualTo(head.name);
        assertThat(pulledGear.kind).isEqualTo(head.kind);
        assertThat(pulledGear.url).isEqualTo(head.url);
        assertThat(pulledGear.rarity).isEqualTo(head.rarity);
        assertThat(pulledGear.brand.id).isEqualTo(head.brand.id);
    }

    @Test
    public void selectHead(){
        for(Gear gear:gearDao.selectHead()){
            assertThat(gear.kind).isEqualTo("head");
        }
    }

    @Test
    public void selectClothes(){
        for(Gear gear:gearDao.selectClothes()){
            assertThat(gear.kind).isEqualTo("clothes");
        }
    }

    @Test
    public void selectShoes(){
        for(Gear gear:gearDao.selectShoes()){
            assertThat(gear.kind).isEqualTo("shoes");
        }
    }
}
