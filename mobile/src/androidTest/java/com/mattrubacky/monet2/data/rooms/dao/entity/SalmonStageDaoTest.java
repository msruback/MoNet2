package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SalmonStageDaoTest {

    private TestDatabase db;
    private SalmonStageDao salmonStageDao;
    private SalmonStage salmonStage;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        salmonStageDao = db.getSalmonStageDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();

            salmonStage = gson.fromJson(deserializedHelper.getJSON("salmon_stage.json"), SalmonStage.class);
            salmonStage.id = SalmonStage.generateId(salmonStage.name);
            salmonStageDao.insertSalmonStage(salmonStage);
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
        SalmonStage pulledStage = salmonStageDao.select(salmonStage.id);
        assertThat(pulledStage.id).isEqualTo(salmonStage.id);
        assertThat(pulledStage.name).isEqualTo(salmonStage.name);
        assertThat(pulledStage.url).isEqualTo(salmonStage.url);
    }

    @Test
    public void selectAll(){
        LiveData<List<SalmonStage>> salmonStagesLiveData = salmonStageDao.selectAll();
        salmonStagesLiveData.observeForever(new Observer<List<SalmonStage>>() {
            @Override
            public void onChanged(List<SalmonStage> salmonStages) {
                assertThat(salmonStages).isNotEmpty();
            }
        });
    }
}
