package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class TimePeriodDaoTest {

    private TimePeriodDao timePeriodDao;
    private TestDatabase db;
    private TimePeriod timePeriodRegular,timePeriodRanked,timePeriodLeague;
    private Context context;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        timePeriodDao = db.getTimePeriodDao();
        StageDao stageDao = db.getStageDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            timePeriodRegular = gson.fromJson(deserializedHelper.getJSON("tp_regular.json"), TimePeriod.class);
            timePeriodRanked = gson.fromJson(deserializedHelper.getJSON("tp_ranked.json"),TimePeriod.class);
            timePeriodLeague = gson.fromJson(deserializedHelper.getJSON("tp_league.json"),TimePeriod.class);

            timePeriodRegular.id = TimePeriod.generateId(timePeriodRegular.start,timePeriodRegular.mode.key);
            timePeriodRanked.id = TimePeriod.generateId(timePeriodRanked.start,timePeriodRanked.mode.key);
            timePeriodLeague.id = TimePeriod.generateId(timePeriodLeague.start,timePeriodLeague.mode.key);
            timePeriodDao.insertTimePeriod(timePeriodRegular, stageDao);
            timePeriodDao.insertTimePeriod(timePeriodRanked, stageDao);
            timePeriodDao.insertTimePeriod(timePeriodLeague, stageDao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb(){
        db.close();
    }


    @Test
    public void insert() {
        LiveData<TimePeriod> timePeriodLiveData = timePeriodDao.select(timePeriodRegular.id);
        timePeriodLiveData.observeForever(new Observer<TimePeriod>() {
            @Override
            public void onChanged(TimePeriod timePeriod) {
                assertThat(timePeriod.id).isEqualTo(timePeriodRegular.id);
                assertThat(timePeriod.start).isEqualTo(timePeriodRegular.start);
                assertThat(timePeriod.end).isEqualTo(timePeriodRegular.end);
                assertThat(timePeriod.rule.getName(context)).isEqualTo(timePeriodRegular.rule.getName(context));
                assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodRegular.mode.getName(context));
                assertThat(timePeriod.a.id).isEqualTo(timePeriodRegular.a.id);
                assertThat(timePeriod.a.name).isNull();
                assertThat(timePeriod.a.url).isNull();
                assertThat(timePeriod.b.id).isEqualTo(timePeriodRegular.b.id);
                assertThat(timePeriod.b.name).isNull();
                assertThat(timePeriod.b.url).isNull();
            }
        });
    }

    @Test
    public void selectRegular() {
        Long now = Calendar.getInstance().getTimeInMillis();
        LiveData<List<TimePeriod>> timePeriodLiveData = timePeriodDao.selectRegularLive(now/1000);
        timePeriodLiveData.observeForever(new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                for(TimePeriod timePeriod:timePeriods){
                    assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodRegular.mode.getName(context));
                }
            }
        });
    }

    @Test
    public void selectGachi() {
        Long now = Calendar.getInstance().getTimeInMillis();
        LiveData<List<TimePeriod>> timePeriodLiveData = timePeriodDao.selectGachiLive(now/1000);
        timePeriodLiveData.observeForever( new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                for(TimePeriod timePeriod:timePeriods){
                    assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodRanked.mode.getName(context));
                }
            }
        });
    }

    @Test
    public void selectLeague() {
        Long now = Calendar.getInstance().getTimeInMillis();
        LiveData<List<TimePeriod>> timePeriodLiveData = timePeriodDao.selectLeagueLive(now/1000);
        timePeriodLiveData.observeForever(new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                for(TimePeriod timePeriod:timePeriods){
                    assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodLeague.mode.getName(context));
                }
            }
        });
    }

    @Test
    public void selectFestival() {
        Long now = Calendar.getInstance().getTimeInMillis();
        LiveData<List<TimePeriod>> timePeriodLiveData = timePeriodDao.selectFestivalLive(now/1000);
        timePeriodLiveData.observeForever(new Observer<List<TimePeriod>>() {
            @Override
            public void onChanged(List<TimePeriod> timePeriods) {
                for(TimePeriod timePeriod:timePeriods){
                    assertThat(timePeriod.mode.getName(context)).isEqualTo("Festival");
                }
            }
        });
    }

    @Test
    public void selectOld() {
        Long now = Calendar.getInstance().getTimeInMillis();
        List<TimePeriod> timePeriods = timePeriodDao.selectOld(now/1000);
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.end).isLessThan(now);
        }
    }
}