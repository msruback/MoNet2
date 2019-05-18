package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Weapon;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.data.combo.WeaponCombo;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class WeaponDaoTest {
    private TestDatabase db;
    private WeaponDao weaponDao;
    private Weapon weapon;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        weaponDao = db.getWeaponDao();
        SubDao subDao = db.getSubDao();
        SpecialDao specialDao = db.getSpecialDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            weapon = gson.fromJson(deserializedHelper.getJSON("weapon.json"), Weapon.class);
            weaponDao.insertWeapon(weapon, subDao, specialDao);
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
        LiveData<WeaponCombo> weaponLiveData = weaponDao.select(weapon.id);
        weaponLiveData.observeForever(new Observer<WeaponCombo>() {
            @Override
            public void onChanged(WeaponCombo weaponCombo) {
                Weapon pulledWeapon = weaponCombo.toDeserialized();
                assertThat(pulledWeapon.id).isEqualTo(weapon.id);
                assertThat(pulledWeapon.name).isEqualTo(weapon.name);
                assertThat(pulledWeapon.url).isEqualTo(weapon.url);
                assertThat(pulledWeapon.special.id).isEqualTo(weapon.special.id);
                assertThat(pulledWeapon.special.name).isEqualTo(weapon.special.name);
                assertThat(pulledWeapon.special.url).isEqualTo(weapon.special.url);
                assertThat(pulledWeapon.sub.id).isEqualTo(weapon.sub.id);
                assertThat(pulledWeapon.sub.name).isEqualTo(weapon.sub.name);
                assertThat(pulledWeapon.sub.url).isEqualTo(weapon.sub.url);
            }
        });
    }

    @Test
    public void selectFromSpecial(){
        LiveData<List<WeaponCombo>> weaponLiveData = weaponDao.selectFromSpecial(weapon.special.id);
        weaponLiveData.observeForever(new Observer<List<WeaponCombo>>() {
            @Override
            public void onChanged(List<WeaponCombo> weaponCombos) {
                for(WeaponCombo pulledWeapon :weaponCombos){
                    assertThat(pulledWeapon.special.id).isEqualTo(weapon.special.id);
                }
            }
        });
    }

    @Test
    public void selectFromSub(){
        LiveData<List<WeaponCombo>> weaponLiveData = weaponDao.selectFromSub(weapon.sub.id);
        weaponLiveData.observeForever(new Observer<List<WeaponCombo>>() {
            @Override
            public void onChanged(List<WeaponCombo> weaponCombos) {
                for(WeaponCombo pulledWeapon :weaponCombos){
                    assertThat(pulledWeapon.sub.id).isEqualTo(weapon.sub.id);
                }
            }
        });
    }

    @Test
    public void selectCombo(){
        LiveData<WeaponCombo> weaponComboLiveData = weaponDao.selectCombo(weapon.id);
        weaponComboLiveData.observeForever(new Observer<WeaponCombo>() {
            @Override
            public void onChanged(WeaponCombo weaponCombo) {
                Weapon pulledWeapon = weaponCombo.toDeserialized();

                assertThat(pulledWeapon.id).isEqualTo(weapon.id);
                assertThat(pulledWeapon.name).isEqualTo(weapon.name);
                assertThat(pulledWeapon.url).isEqualTo(weapon.url);

                assertThat(pulledWeapon.special.id).isEqualTo(weapon.special.id);
                assertThat(pulledWeapon.special.name).isEqualTo(weapon.special.name);
                assertThat(pulledWeapon.special.url).isEqualTo(weapon.special.url);

                assertThat(pulledWeapon.sub.id).isEqualTo(weapon.sub.id);
                assertThat(pulledWeapon.sub.name).isEqualTo(weapon.sub.name);
                assertThat(pulledWeapon.sub.url).isEqualTo(weapon.sub.url);
            }
        });
    }

}
