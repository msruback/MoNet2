package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Special;
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
public class SpecialDaoTest {
    private TestDatabase db;
    private SpecialDao specialDao;
    private Special special;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        specialDao = db.getSpecialDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            special = gson.fromJson(deserializedHelper.getJSON("special.json"), Special.class);
            specialDao.insertSpecial(special);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insert(){
        LiveData<Special> specialLiveData = specialDao.select(special.id);
        specialLiveData.observeForever(new Observer<Special>() {
            @Override
            public void onChanged(Special pulledSpecial) {
                assertThat(pulledSpecial.id).isEqualTo(special.id);
                assertThat(pulledSpecial.name).isEqualTo(special.name);
                assertThat(pulledSpecial.url).isEqualTo(special.url);
            }
        });
    }
}
