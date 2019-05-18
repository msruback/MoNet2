package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.ClothesCloset;
import com.mattrubacky.monet2.data.combo.HeadCloset;
import com.mattrubacky.monet2.data.combo.ShoeCloset;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.entity.ClosetRoom;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class ClosetDaoTest {

    private TestDatabase db;
    private ClosetDao closetDao;
    private Gear head,clothes,shoes;
    private Skill main,sub;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();

        closetDao = db.getClosetDao();
        GearDao gearDao = db.getGearDao();
        SkillDao skillDao = db.getSkillDao();
        BrandDao brandDao = db.getBrandDao();
        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            head = gson.fromJson(deserializedHelper.getJSON("gear_head.json"),Gear.class);
            clothes = gson.fromJson(deserializedHelper.getJSON("gear_clothes.json"),Gear.class);
            shoes = gson.fromJson(deserializedHelper.getJSON("gear_shoes.json"),Gear.class);

            main = gson.fromJson(deserializedHelper.getJSON("skill_special.json"),Skill.class);
            sub = gson.fromJson(deserializedHelper.getJSON("skill_chunkable.json"),Skill.class);

            head.generatedId = Gear.generateId(head.kind, head.id);
            clothes.generatedId = Gear.generateId(clothes.kind,clothes.id);
            shoes.generatedId = Gear.generateId(shoes.kind,shoes.id);

            gearDao.insertGear(head,false,brandDao,skillDao);
            gearDao.insertGear(clothes,false,brandDao,skillDao);
            gearDao.insertGear(shoes,false,brandDao,skillDao);

            skillDao.insertSkill(main);
            skillDao.insertSkill(sub);
            GearSkills skills = new GearSkills();
            skills.main = main;
            skills.subs = new ArrayList();
            skills.subs.add(sub);
            skills.subs.add(sub);

            closetDao.insertGear(head,skills);
            closetDao.insertGear(clothes,skills);
            closetDao.insertGear(shoes,skills);
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
        ClosetRoom closetRoom = closetDao.selectCloset(head.generatedId);
        assertThat(closetRoom.gear.generatedId).isEqualTo(head.generatedId);
        assertThat(closetRoom.main.id).isEqualTo(main.id);
        assertThat(closetRoom.sub1.id).isEqualTo(sub.id);
    }

    @Test
    public void selectHead(){
        LiveData<HeadCloset> headClosetLiveData = closetDao.selectHeadStats(head.generatedId);
        headClosetLiveData.observeForever(new Observer<HeadCloset>() {
            @Override
            public void onChanged(HeadCloset headCloset) {
                Gear pulledHead = headCloset.gear.toDeserialized();
                assertThat(pulledHead.id).isEqualTo(head.id);
                assertThat(pulledHead.name).isEqualTo(head.name);
                assertThat(pulledHead.kind).isEqualTo(head.kind);
                assertThat(pulledHead.url).isEqualTo(head.url);
                assertThat(pulledHead.brand.id).isEqualTo(head.brand.id);
                assertThat(pulledHead.brand.name).isEqualTo(head.brand.name);
                assertThat(pulledHead.brand.url).isEqualTo(head.brand.url);
                assertThat(pulledHead.brand.skill.id).isEqualTo(head.brand.skill.id);
                assertThat(pulledHead.brand.skill.name).isEqualTo(head.brand.skill.name);
                assertThat(pulledHead.brand.skill.url).isEqualTo(head.brand.skill.url);

                assertThat(headCloset.main.id).isEqualTo(main.id);
                assertThat(headCloset.sub1.id).isEqualTo(sub.id);
                assertThat(headCloset.sub2.id).isEqualTo(sub.id);
            }
        });
    }

    @Test
    public void selectClothes(){
        LiveData<ClothesCloset> clothesClosetLiveData = closetDao.selectClothesStats(clothes.generatedId);
        clothesClosetLiveData.observeForever(new Observer<ClothesCloset>() {
            @Override
            public void onChanged(ClothesCloset clothesCloset) {
                Gear pulledClothes = clothesCloset.gear.toDeserialized();
                assertThat(pulledClothes.id).isEqualTo(clothes.id);
                assertThat(pulledClothes.name).isEqualTo(clothes.name);
                assertThat(pulledClothes.kind).isEqualTo(clothes.kind);
                assertThat(pulledClothes.url).isEqualTo(clothes.url);
                assertThat(pulledClothes.brand.id).isEqualTo(clothes.brand.id);
                assertThat(pulledClothes.brand.name).isEqualTo(clothes.brand.name);
                assertThat(pulledClothes.brand.url).isEqualTo(clothes.brand.url);
                assertThat(pulledClothes.brand.skill.id).isEqualTo(clothes.brand.skill.id);
                assertThat(pulledClothes.brand.skill.name).isEqualTo(clothes.brand.skill.name);
                assertThat(pulledClothes.brand.skill.url).isEqualTo(clothes.brand.skill.url);

                assertThat(clothesCloset.main.id).isEqualTo(main.id);
                assertThat(clothesCloset.sub1.id).isEqualTo(sub.id);
                assertThat(clothesCloset.sub2.id).isEqualTo(sub.id);
            }
        });
    }

    @Test
    public void selectShoes(){
        LiveData<ShoeCloset> shoesClosetLiveData = closetDao.selectShoeStats(shoes.generatedId);
        shoesClosetLiveData.observeForever(new Observer<ShoeCloset>() {
            @Override
            public void onChanged(ShoeCloset shoeCloset) {
                Gear pulledShoe = shoeCloset.gear.toDeserialized();
                assertThat(pulledShoe.id).isEqualTo(shoes.id);
                assertThat(pulledShoe.name).isEqualTo(shoes.name);
                assertThat(pulledShoe.kind).isEqualTo(shoes.kind);
                assertThat(pulledShoe.url).isEqualTo(shoes.url);
                assertThat(pulledShoe.brand.id).isEqualTo(shoes.brand.id);
                assertThat(pulledShoe.brand.name).isEqualTo(shoes.brand.name);
                assertThat(pulledShoe.brand.url).isEqualTo(shoes.brand.url);
                assertThat(pulledShoe.brand.skill.id).isEqualTo(shoes.brand.skill.id);
                assertThat(pulledShoe.brand.skill.name).isEqualTo(shoes.brand.skill.name);
                assertThat(pulledShoe.brand.skill.url).isEqualTo(shoes.brand.skill.url);

                assertThat(shoeCloset.main.id).isEqualTo(main.id);
                assertThat(shoeCloset.sub1.id).isEqualTo(sub.id);
                assertThat(shoeCloset.sub2.id).isEqualTo(sub.id);
            }
        });
    }
}
