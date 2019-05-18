package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
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
        db.close();
    }

    @Test
    public void insert() {
        LiveData<Stage> stageLiveData = stageDao.select(stage.id);
        stageLiveData.observeForever(new Observer<Stage>() {
            @Override
            public void onChanged(Stage pulledStage) {
                assertThat(pulledStage.name).isEqualTo(stage.name);
                assertThat(pulledStage.url).isEqualTo(stage.url);
            }
        });
    }
}