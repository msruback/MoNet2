package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.combo.SalmonShiftCombo;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SalmonShiftDaoTest {

    private TestDatabase db;
    private SalmonShiftDao salmonShiftDao;
    private SalmonRunDetail salmonRunShift;
    private SalmonRun salmonRun;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        salmonShiftDao = db.getSalmonShiftDao();
        SalmonWeaponDao salmonWeaponDao = db.getSalmonWeaponDao();
        SalmonStageDao salmonStageDao = db.getSalmonStageDao();
        WeaponDao weaponDao = db.getWeaponDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();

            salmonRunShift = gson.fromJson(deserializedHelper.getJSON("salmon_shift_detail_normal.json"), SalmonRunDetail.class);
            salmonRun = gson.fromJson(deserializedHelper.getJSON("salmon_shift.json"), SalmonRun.class);

            salmonShiftDao.insertSalmonShift(salmonRunShift,salmonStageDao,salmonWeaponDao,weaponDao);
            salmonShiftDao.insertSalmonShift(salmonRun);
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
        LiveData<SalmonShiftCombo> pulledShiftLiveData = salmonShiftDao.select(SalmonShiftRoom.generateId(salmonRun.start));
        pulledShiftLiveData.observeForever(new Observer<SalmonShiftCombo>() {
            @Override
            public void onChanged(SalmonShiftCombo salmonShiftCombo) {
                assertThat(salmonShiftCombo.salmonShiftRoom.startTime).isEqualTo(salmonRun.start);
                assertThat(salmonShiftCombo.salmonShiftRoom.endTime).isEqualTo(salmonRun.end);
            }
        });
    }

    @Test
    public void insertDetail(){
        LiveData<SalmonShiftCombo> pulledShiftLiveData = salmonShiftDao.select(SalmonShiftRoom.generateId(salmonRunShift.start));
        pulledShiftLiveData.observeForever(new Observer<SalmonShiftCombo>() {
            @Override
            public void onChanged(SalmonShiftCombo salmonShiftRoom) {
                SalmonRunDetail pulledShift = salmonShiftRoom.toDeserialised();
                assertThat(pulledShift.start).isEqualTo(salmonRunShift.start);
                assertThat(pulledShift.end).isEqualTo(salmonRunShift.end);
                assertThat(pulledShift.stage.id).isEqualTo(salmonRunShift.stage.id);
                assertThat(pulledShift.stage.name).isEqualTo(salmonRunShift.stage.name);
                assertThat(pulledShift.stage.url).isEqualTo(salmonRunShift.stage.url);
            }
        });
    }
}
