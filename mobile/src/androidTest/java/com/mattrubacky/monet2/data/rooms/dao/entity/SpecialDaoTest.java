package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Special;
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
public class SpecialDaoTest {
    private TestDatabase db;
    private Context context;
    private SpecialDao specialDao;
    private Special special;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
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
        specialDao.delete(special);
        db.close();
    }

    @Test
    public void insert(){
        Special pulledSpecial = specialDao.select(special.id);
        assertThat(pulledSpecial.id).isEqualTo(special.id);
        assertThat(pulledSpecial.name).isEqualTo(special.name);
        assertThat(pulledSpecial.url).isEqualTo(special.url);
    }
}
