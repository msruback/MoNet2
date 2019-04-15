package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
        TimePeriod timePeriodEnd = timePeriodDao.select(timePeriodRegular.id);
        assertThat(timePeriodEnd.id).isEqualTo(timePeriodRegular.id);
        assertThat(timePeriodEnd.start).isEqualTo(timePeriodRegular.start);
        assertThat(timePeriodEnd.end).isEqualTo(timePeriodRegular.end);
        assertThat(timePeriodEnd.rule.getName(context)).isEqualTo(timePeriodRegular.rule.getName(context));
        assertThat(timePeriodEnd.mode.getName(context)).isEqualTo(timePeriodRegular.mode.getName(context));
        assertThat(timePeriodEnd.a.id).isEqualTo(timePeriodRegular.a.id);
        assertThat(timePeriodEnd.a.name).isNull();
        assertThat(timePeriodEnd.a.url).isNull();
        assertThat(timePeriodEnd.b.id).isEqualTo(timePeriodRegular.b.id);
        assertThat(timePeriodEnd.b.name).isNull();
        assertThat(timePeriodEnd.b.url).isNull();
    }

    @Test
    public void delete() {
        timePeriodDao.delete(timePeriodRegular);
        TimePeriod timePeriodEnd = timePeriodDao.select(timePeriodRegular.id);
        assertThat(timePeriodEnd).isNull();
    }

    @Test
    public void selectRegular() {
        List<TimePeriod> timePeriods =timePeriodDao.selectRegular();
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.mode.getName(context)).isEqualTo("Regular");
        }
    }

    @Test
    public void selectGachi() {
        List<TimePeriod> timePeriods = timePeriodDao.selectGachi();
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodRanked.mode.getName(context));
        }
    }

    @Test
    public void selectLeague() {
        List<TimePeriod> timePeriods =timePeriodDao.selectLeague();
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.mode.getName(context)).isEqualTo(timePeriodLeague.mode.getName(context));
        }
    }

    @Test
    public void selectFestival() {
        List<TimePeriod> timePeriods = timePeriodDao.selectFestival();
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.mode.getName(context)).isEqualTo("Festival");
        }
    }

    @Test
    public void selectOld() {
        Long now = Calendar.getInstance().getTimeInMillis();
        List<TimePeriod> timePeriods = timePeriodDao.selectOld(now);
        for(TimePeriod timePeriod:timePeriods){
            assertThat(timePeriod.end).isLessThan(now);
        }
    }
}