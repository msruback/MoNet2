package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.GearBrand;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class GearDaoTest {

    private TestDatabase db;
    private GearDao gearDao;
    private BrandDao brandDao;
    private SkillDao skillDao;
    private Gear head,clothes,shoes,skilless;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
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
            skilless = gson.fromJson(deserializedHelper.getJSON("gear_skilless.json"),Gear.class);

            head.generatedId = Gear.generateId(head.kind,head.id);
            clothes.generatedId = Gear.generateId(clothes.kind,clothes.id);
            shoes.generatedId = Gear.generateId(shoes.kind,shoes.id);
            skilless.generatedId = Gear.generateId(skilless.kind,skilless.id);

            gearDao.insertGear(head,false,brandDao,skillDao);
            gearDao.insertGear(clothes,false,brandDao,skillDao);
            gearDao.insertGear(shoes,false,brandDao,skillDao);
            gearDao.insertGear(skilless,true,brandDao,skillDao);
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
        LiveData<GearBrand> gearLiveData = gearDao.select(head.generatedId);
        gearLiveData.observeForever(new Observer<GearBrand>() {
            @Override
            public void onChanged(GearBrand gear) {
                Gear pulledGear = gear.toDeserialized();
                assertThat(pulledGear.generatedId).isEqualTo(head.generatedId);
                assertThat(pulledGear.id).isEqualTo(head.id);
                assertThat(pulledGear.name).isEqualTo(head.name);
                assertThat(pulledGear.kind).isEqualTo(head.kind);
                assertThat(pulledGear.url).isEqualTo(head.url);
                assertThat(pulledGear.rarity).isEqualTo(head.rarity);
                assertThat(pulledGear.brand.id).isEqualTo(head.brand.id);
                assertThat(pulledGear.brand.name).isEqualTo(head.brand.name);
                assertThat(pulledGear.brand.url).isEqualTo(head.brand.url);
                assertThat(pulledGear.brand.skill.id).isEqualTo(head.brand.skill.id);
                assertThat(pulledGear.brand.skill.name).isEqualTo(head.brand.skill.name);
                assertThat(pulledGear.brand.skill.url).isEqualTo(head.brand.skill.url);
            }
        });
    }

    @Test
    public void insertSkilless(){
        LiveData<GearBrand> gearLiveData = gearDao.select(skilless.generatedId);
        gearLiveData.observeForever(new Observer<GearBrand>() {
            @Override
            public void onChanged(GearBrand gear) {
                Gear pulledGear = gear.toDeserialized();
                assertThat(pulledGear.generatedId).isEqualTo(skilless.generatedId);
                assertThat(pulledGear.id).isEqualTo(skilless.id);
                assertThat(pulledGear.name).isEqualTo(skilless.name);
                assertThat(pulledGear.kind).isEqualTo(skilless.kind);
                assertThat(pulledGear.url).isEqualTo(skilless.url);
                assertThat(pulledGear.rarity).isEqualTo(skilless.rarity);
                assertThat(pulledGear.brand.id).isEqualTo(skilless.brand.id);
                assertThat(pulledGear.brand.name).isEqualTo(skilless.brand.name);
                assertThat(pulledGear.brand.url).isEqualTo(skilless.brand.url);
                assertThat(pulledGear.brand.skill).isNull();
            }
        });
    }

    @Test
    public void update(){
        Gear editedGear = new Gear(head);
        assertThat(head.rarity).isEqualTo(2);
        editedGear.rarity ++;
        assertThat(head.rarity).isEqualTo(2);
        gearDao.insertGear(editedGear,true,brandDao,skillDao);
        LiveData<GearBrand> gearLiveData = gearDao.select(head.generatedId);
        gearLiveData.observeForever(new Observer<GearBrand>() {
            @Override
            public void onChanged(GearBrand gear) {
                Gear pulledGear = gear.toDeserialized();
                assertThat(pulledGear.rarity).isNotEqualTo(head.rarity);
                assertThat(pulledGear.rarity).isEqualTo(head.rarity+1);
            }
        });
    }

    @Test
    public void selectSplatnet(){
        LiveData<GearBrand> gearLiveData = gearDao.select(head.id,head.kind);
        gearLiveData.observeForever(new Observer<GearBrand>() {
            @Override
            public void onChanged(GearBrand gear) {
                Gear pulledGear = gear.toDeserialized();
                assertThat(pulledGear.generatedId).isEqualTo(head.generatedId);
                assertThat(pulledGear.id).isEqualTo(head.id);
                assertThat(pulledGear.name).isEqualTo(head.name);
                assertThat(pulledGear.kind).isEqualTo(head.kind);
                assertThat(pulledGear.url).isEqualTo(head.url);
                assertThat(pulledGear.rarity).isEqualTo(head.rarity);
                assertThat(pulledGear.brand.id).isEqualTo(head.brand.id);
                assertThat(pulledGear.brand.name).isEqualTo(head.brand.name);
                assertThat(pulledGear.brand.url).isEqualTo(head.brand.url);
                assertThat(pulledGear.brand.skill.id).isEqualTo(head.brand.skill.id);
                assertThat(pulledGear.brand.skill.name).isEqualTo(head.brand.skill.name);
                assertThat(pulledGear.brand.skill.url).isEqualTo(head.brand.skill.url);
            }
        });
    }

    @Test
    public void selectSplatnetSkilless(){
        LiveData<GearBrand> gearLiveData = gearDao.select(skilless.id,skilless.kind);
        gearLiveData.observeForever(new Observer<GearBrand>() {
            @Override
            public void onChanged(GearBrand gear) {
                Gear pulledGear = gear.toDeserialized();
                assertThat(pulledGear.generatedId).isEqualTo(skilless.generatedId);
                assertThat(pulledGear.id).isEqualTo(skilless.id);
                assertThat(pulledGear.name).isEqualTo(skilless.name);
                assertThat(pulledGear.kind).isEqualTo(skilless.kind);
                assertThat(pulledGear.url).isEqualTo(skilless.url);
                assertThat(pulledGear.rarity).isEqualTo(skilless.rarity);
                assertThat(pulledGear.brand.id).isEqualTo(skilless.brand.id);
                assertThat(pulledGear.brand.name).isEqualTo(skilless.brand.name);
                assertThat(pulledGear.brand.url).isEqualTo(skilless.brand.url);
                assertThat(pulledGear.brand.skill).isNull();
            }
        });
    }

    @Test
    public void selectHead(){
        LiveData<List<GearBrand>> gearLiveData = gearDao.selectHead();
        gearLiveData.observeForever(new Observer<List<GearBrand>>() {
            @Override
            public void onChanged(List<GearBrand> gears) {
                for(GearBrand gear:gears){
                    assertThat(gear.gear.kind).isEqualTo(head.kind);
                }
            }
        });
    }

    @Test
    public void selectClothes(){
        LiveData<List<GearBrand>> gearLiveData = gearDao.selectClothes();
        gearLiveData.observeForever(new Observer<List<GearBrand>>() {
            @Override
            public void onChanged(List<GearBrand> gears) {
                for(GearBrand gear:gears){
                    assertThat(gear.gear.kind).isEqualTo(clothes.kind);
                }
            }
        });
    }

    @Test
    public void selectShoes(){
        LiveData<List<GearBrand>> gearLiveData = gearDao.selectShoes();
        gearLiveData.observeForever(new Observer<List<GearBrand>>() {
            @Override
            public void onChanged(List<GearBrand> gears) {
                for(GearBrand gear:gears){
                    assertThat(gear.gear.kind).isEqualTo(shoes.kind);
                }
            }
        });
    }

    @Test
    public void selectMultiple(){
        int[] ids = new int[3];
        ids[0] = head.generatedId;
        ids[1] = clothes.generatedId;
        ids[2] = shoes.generatedId;

        LiveData<List<GearBrand>> gearLiveData = gearDao.selectList(ids);
        gearLiveData.observeForever(new Observer<List<GearBrand>>() {
            @Override
            public void onChanged(List<GearBrand> pulledGears) {
                for(GearBrand gear:pulledGears){
                    Gear pulledGear = gear.toDeserialized();
                    if(pulledGear.generatedId==head.generatedId){
                        assertThat(pulledGear.id).isEqualTo(head.id);
                        assertThat(pulledGear.kind).isEqualTo(head.kind);
                        assertThat(pulledGear.name).isEqualTo(head.name);
                        assertThat(pulledGear.rarity).isEqualTo(head.rarity);
                        assertThat(pulledGear.url).isEqualTo(head.url);
                        assertThat(pulledGear.brand.id).isEqualTo(head.brand.id);
                    } else if(pulledGear.generatedId==clothes.generatedId){
                        assertThat(pulledGear.id).isEqualTo(clothes.id);
                        assertThat(pulledGear.kind).isEqualTo(clothes.kind);
                        assertThat(pulledGear.name).isEqualTo(clothes.name);
                        assertThat(pulledGear.rarity).isEqualTo(clothes.rarity);
                        assertThat(pulledGear.url).isEqualTo(clothes.url);
                        assertThat(pulledGear.brand.id).isEqualTo(clothes.brand.id);
                    } else if(pulledGear.generatedId==shoes.generatedId){
                        assertThat(pulledGear.id).isEqualTo(shoes.id);
                        assertThat(pulledGear.kind).isEqualTo(shoes.kind);
                        assertThat(pulledGear.name).isEqualTo(shoes.name);
                        assertThat(pulledGear.rarity).isEqualTo(shoes.rarity);
                        assertThat(pulledGear.url).isEqualTo(shoes.url);
                        assertThat(pulledGear.brand.id).isEqualTo(shoes.brand.id);
                    }else{
                        assertThat(true).isFalse();
                    }
                }
            }
        });
    }
}
