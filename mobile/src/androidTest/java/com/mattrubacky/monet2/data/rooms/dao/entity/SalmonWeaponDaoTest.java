package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SalmonWeaponDaoTest {

    private TestDatabase db;
    private SalmonWeaponDao salmonWeaponDao;
    private SalmonRunDetail salmonRunShift;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        salmonWeaponDao = db.getSalmonWeaponDao();
        SalmonShiftDao salmonShiftDao = db.getSalmonShiftDao();
        SalmonStageDao salmonStageDao = db.getSalmonStageDao();
        WeaponDao weaponDao = db.getWeaponDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();

            salmonRunShift = gson.fromJson(deserializedHelper.getJSON("salmon_shift_detail_normal.json"), SalmonRunDetail.class);
            salmonShiftDao.insertSalmonShift(salmonRunShift,salmonStageDao,salmonWeaponDao,weaponDao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert(){
        SalmonRunWeapon pulledWeapon = salmonWeaponDao.select(salmonRunShift.weapons.get(0).id);
        assertThat(pulledWeapon).isNotNull();
    }

    @Test
    public void selectShift(){
        List<SalmonRunWeapon> pulledWeapons = salmonWeaponDao.selectShift(SalmonShiftRoom.generateId(salmonRunShift.start));
        assertThat(pulledWeapons).isNotEmpty();
    }
}
