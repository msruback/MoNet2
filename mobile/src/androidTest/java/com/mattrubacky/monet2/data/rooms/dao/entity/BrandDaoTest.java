package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Brand;
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
public class BrandDaoTest {
    private TestDatabase db;
    private Context context;
    private BrandDao brandDao;
    private SkillDao skillDao;
    private Brand brand;

    @Before
    public void createDB(){
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        brandDao = db.getBrandDao();
        skillDao = db.getSkillDao();
        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            brand = gson.fromJson(deserializedHelper.getJSON("brand.json"),Brand.class);

            brandDao.insertBrand(brand,skillDao);

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
        Brand pulledBrand = brandDao.select(brand.id);
        assertThat(pulledBrand.id).isEqualTo(brand.id);
        assertThat(pulledBrand.name).isEqualTo(brand.name);
        assertThat(pulledBrand.url).isEqualTo(brand.url);
        assertThat(pulledBrand.skill.id).isEqualTo(brand.skill.id);
    }

    @Test
    public void delete(){
        brandDao.delete(brand);
        Brand pulledBrand = brandDao.select(brand.id);
        assertThat(pulledBrand).isNull();
    }
}
