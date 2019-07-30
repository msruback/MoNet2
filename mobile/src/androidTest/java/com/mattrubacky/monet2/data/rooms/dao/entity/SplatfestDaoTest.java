package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.SplatfestStageCombo;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
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
public class SplatfestDaoTest {
    private TestDatabase db;
    private SplatfestDao splatfestDao;
    private Splatfest splatfestV2,splatfestV1;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        splatfestDao = db.getSplatfestDao();
        StageDao stageDao = db.getStageDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            splatfestV1 = gson.fromJson(deserializedHelper.getJSON("splatfest_v1.json"),Splatfest.class);
            splatfestV2 = gson.fromJson(deserializedHelper.getJSON("splatfest_v2.json"),Splatfest.class);
            splatfestDao.insertSplatfest(splatfestV1,stageDao);
            splatfestDao.insertSplatfest(splatfestV2,stageDao);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insert(){
        Splatfest pulledSplatfest = splatfestDao.select(splatfestV2.id).toDeserialized();
        assertThat(pulledSplatfest.id).isEqualTo(splatfestV2.id);

        assertThat(pulledSplatfest.colors.alpha.getColor()).isEqualTo(splatfestV2.colors.alpha.getColor());
        assertThat(pulledSplatfest.colors.bravo.getColor()).isEqualTo(splatfestV2.colors.bravo.getColor());

        assertThat(pulledSplatfest.images.alpha).isEqualTo(splatfestV2.images.alpha);
        assertThat(pulledSplatfest.images.bravo).isEqualTo(splatfestV2.images.bravo);
        assertThat(pulledSplatfest.images.panel).isEqualTo(splatfestV2.images.panel);

        assertThat(pulledSplatfest.names.alpha).isEqualTo(splatfestV2.names.alpha);
        assertThat(pulledSplatfest.names.alphaDesc).isEqualTo(splatfestV2.names.alphaDesc);
        assertThat(pulledSplatfest.names.bravo).isEqualTo(splatfestV2.names.bravo);
        assertThat(pulledSplatfest.names.bravoDesc).isEqualTo(splatfestV2.names.bravoDesc);

        assertThat(pulledSplatfest.times.announce).isEqualTo(splatfestV2.times.announce);
        assertThat(pulledSplatfest.times.start).isEqualTo(splatfestV2.times.start);
        assertThat(pulledSplatfest.times.end).isEqualTo(splatfestV2.times.end);
        assertThat(pulledSplatfest.times.result).isEqualTo(splatfestV2.times.result);

        assertThat(pulledSplatfest.stage.id).isEqualTo(splatfestV2.stage.id);
        assertThat(pulledSplatfest.stage.name).isEqualTo(splatfestV2.stage.name);
        assertThat(pulledSplatfest.stage.url).isEqualTo(splatfestV2.stage.url);
    }

    @Test
    public void selectUpcoming(){
        LiveData<SplatfestStageCombo> stageComboLiveData = splatfestDao.selectUpcomingLive(splatfestV2.times.announce);
        stageComboLiveData.observeForever(new Observer<SplatfestStageCombo>() {
            @Override
            public void onChanged(SplatfestStageCombo splatfestStageCombo) {
                Splatfest pulledSplatfest = splatfestStageCombo.toDeserialized();
                assertThat(pulledSplatfest.id).isEqualTo(splatfestV2.id);

                assertThat(pulledSplatfest.colors.alpha.getColor()).isEqualTo(splatfestV2.colors.alpha.getColor());
                assertThat(pulledSplatfest.colors.bravo.getColor()).isEqualTo(splatfestV2.colors.bravo.getColor());

                assertThat(pulledSplatfest.images.alpha).isEqualTo(splatfestV2.images.alpha);
                assertThat(pulledSplatfest.images.bravo).isEqualTo(splatfestV2.images.bravo);
                assertThat(pulledSplatfest.images.panel).isEqualTo(splatfestV2.images.panel);

                assertThat(pulledSplatfest.names.alpha).isEqualTo(splatfestV2.names.alpha);
                assertThat(pulledSplatfest.names.alphaDesc).isEqualTo(splatfestV2.names.alphaDesc);
                assertThat(pulledSplatfest.names.bravo).isEqualTo(splatfestV2.names.bravo);
                assertThat(pulledSplatfest.names.bravoDesc).isEqualTo(splatfestV2.names.bravoDesc);

                assertThat(pulledSplatfest.times.announce).isEqualTo(splatfestV2.times.announce);
                assertThat(pulledSplatfest.times.start).isEqualTo(splatfestV2.times.start);
                assertThat(pulledSplatfest.times.end).isEqualTo(splatfestV2.times.end);
                assertThat(pulledSplatfest.times.result).isEqualTo(splatfestV2.times.result);

                assertThat(pulledSplatfest.stage.id).isEqualTo(splatfestV2.stage.id);
                assertThat(pulledSplatfest.stage.name).isEqualTo(splatfestV2.stage.name);
                assertThat(pulledSplatfest.stage.url).isEqualTo(splatfestV2.stage.url);
            }
        });
    }

    @Test
    public void selectAll(){
        LiveData<List<SplatfestStageCombo>> stageComboLiveData = splatfestDao.selectAll();

        stageComboLiveData.observeForever(new Observer<List<SplatfestStageCombo>>() {
            @Override
            public void onChanged(List<SplatfestStageCombo> splatfestStageCombos) {
                for(SplatfestStageCombo splatfestStageCombo:splatfestStageCombos){
                    Splatfest pulledSplatfest = splatfestStageCombo.toDeserialized();
                    if(splatfestV1.id == pulledSplatfest.id) {
                        assertThat(pulledSplatfest.id).isEqualTo(splatfestV1.id);

                        assertThat(pulledSplatfest.colors.alpha.getColor()).isEqualTo(splatfestV1.colors.alpha.getColor());
                        assertThat(pulledSplatfest.colors.bravo.getColor()).isEqualTo(splatfestV1.colors.bravo.getColor());

                        assertThat(pulledSplatfest.images.alpha).isEqualTo(splatfestV1.images.alpha);
                        assertThat(pulledSplatfest.images.bravo).isEqualTo(splatfestV1.images.bravo);
                        assertThat(pulledSplatfest.images.panel).isEqualTo(splatfestV1.images.panel);

                        assertThat(pulledSplatfest.names.alpha).isEqualTo(splatfestV1.names.alpha);
                        assertThat(pulledSplatfest.names.alphaDesc).isEqualTo(splatfestV1.names.alphaDesc);
                        assertThat(pulledSplatfest.names.bravo).isEqualTo(splatfestV1.names.bravo);
                        assertThat(pulledSplatfest.names.bravoDesc).isEqualTo(splatfestV1.names.bravoDesc);

                        assertThat(pulledSplatfest.times.announce).isEqualTo(splatfestV1.times.announce);
                        assertThat(pulledSplatfest.times.start).isEqualTo(splatfestV1.times.start);
                        assertThat(pulledSplatfest.times.end).isEqualTo(splatfestV1.times.end);
                        assertThat(pulledSplatfest.times.result).isEqualTo(splatfestV1.times.result);

                        assertThat(pulledSplatfest.stage.id).isEqualTo(splatfestV1.stage.id);
                        assertThat(pulledSplatfest.stage.name).isEqualTo(splatfestV1.stage.name);
                        assertThat(pulledSplatfest.stage.url).isEqualTo(splatfestV1.stage.url);
                    }else if(splatfestV2.id==pulledSplatfest.id){
                        assertThat(pulledSplatfest.id).isEqualTo(splatfestV2.id);

                        assertThat(pulledSplatfest.colors.alpha.getColor()).isEqualTo(splatfestV2.colors.alpha.getColor());
                        assertThat(pulledSplatfest.colors.bravo.getColor()).isEqualTo(splatfestV2.colors.bravo.getColor());

                        assertThat(pulledSplatfest.images.alpha).isEqualTo(splatfestV2.images.alpha);
                        assertThat(pulledSplatfest.images.bravo).isEqualTo(splatfestV2.images.bravo);
                        assertThat(pulledSplatfest.images.panel).isEqualTo(splatfestV2.images.panel);

                        assertThat(pulledSplatfest.names.alpha).isEqualTo(splatfestV2.names.alpha);
                        assertThat(pulledSplatfest.names.alphaDesc).isEqualTo(splatfestV2.names.alphaDesc);
                        assertThat(pulledSplatfest.names.bravo).isEqualTo(splatfestV2.names.bravo);
                        assertThat(pulledSplatfest.names.bravoDesc).isEqualTo(splatfestV2.names.bravoDesc);

                        assertThat(pulledSplatfest.times.announce).isEqualTo(splatfestV2.times.announce);
                        assertThat(pulledSplatfest.times.start).isEqualTo(splatfestV2.times.start);
                        assertThat(pulledSplatfest.times.end).isEqualTo(splatfestV2.times.end);
                        assertThat(pulledSplatfest.times.result).isEqualTo(splatfestV2.times.result);

                        assertThat(pulledSplatfest.stage.id).isEqualTo(splatfestV2.stage.id);
                        assertThat(pulledSplatfest.stage.name).isEqualTo(splatfestV2.stage.name);
                        assertThat(pulledSplatfest.stage.url).isEqualTo(splatfestV2.stage.url);
                    }
                }
            }
        });
    }
}
