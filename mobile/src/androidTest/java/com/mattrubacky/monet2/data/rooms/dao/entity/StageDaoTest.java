package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
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
public class StageDaoTest {

    private StageDao stageDao;
    private TestDatabase db;
    private Stage stage;
    private Context context;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        stageDao = db.getStageDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            stage = gson.fromJson(deserializedHelper.getJSON("stage_1.json"), Stage.class);
            stageDao.insertStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb() {
        stageDao.delete(stage);
        db.close();
    }

    @Test
    public void insert() {
        Stage pulledStage = stageDao.select(stage.id);
        assertThat(pulledStage.name).isEqualTo(stage.name);
        assertThat(pulledStage.url).isEqualTo(stage.url);
    }

    @Test
    public void delete() {
        stageDao.delete(stage);
        Stage deletedStage = stageDao.select(stage.id);
        assertThat(deletedStage).isNull();
        stageDao.insertStage(stage);
    }
}