package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.BrandSkill;
import com.mattrubacky.monet2.data.deserialized_entities.Brand;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import static com.google.common.truth.Truth.assertThat;
@SmallTest
public class BrandDaoTest {
    private TestDatabase db;
    private BrandDao brandDao;
    private Brand brand,skilless;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        brandDao = db.getBrandDao();
        SkillDao skillDao = db.getSkillDao();
        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            brand = gson.fromJson(deserializedHelper.getJSON("brand.json"),Brand.class);
            skilless = gson.fromJson(deserializedHelper.getJSON("brand_no_skill.json"),Brand.class);

            brandDao.insertBrand(brand, skillDao);
            brandDao.insertBrand(skilless, skillDao);

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
        LiveData<BrandSkill> brandLiveData = brandDao.select(brand.id);
        brandLiveData.observeForever(new Observer<BrandSkill>() {
            @Override
            public void onChanged(BrandSkill brandSkill) {
                Brand pulledBrand = brandSkill.toDeserialized();
                assertThat(pulledBrand.id).isEqualTo(brand.id);
                assertThat(pulledBrand.name).isEqualTo(brand.name);
                assertThat(pulledBrand.url).isEqualTo(brand.url);
                assertThat(pulledBrand.skill.id).isEqualTo(brand.skill.id);
                assertThat(pulledBrand.skill.name).isEqualTo(brand.skill.name);
                assertThat(pulledBrand.skill.url).isEqualTo(brand.skill.url);
            }
        });
    }

    @Test
    public void insertSkilless(){
        LiveData<BrandSkill> brandLiveData = brandDao.select(skilless.id);
        brandLiveData.observeForever(new Observer<BrandSkill>() {
            @Override
            public void onChanged(BrandSkill brandSkill) {
                Brand pulledBrand = brandSkill.toDeserialized();
                assertThat(pulledBrand.id).isEqualTo(skilless.id);
                assertThat(pulledBrand.name).isEqualTo(skilless.name);
                assertThat(pulledBrand.url).isEqualTo(skilless.url);
                assertThat(pulledBrand.skill).isNull();
            }
        });
    }
}
