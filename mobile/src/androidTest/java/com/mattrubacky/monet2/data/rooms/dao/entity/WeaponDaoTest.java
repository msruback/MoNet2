package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;
import androidx.test.filters.SmallTest;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.Weapon;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class WeaponDaoTest {
    private TestDatabase db;
    private Context context;
    private WeaponDao weaponDao;
    private SubDao subDao;
    private SpecialDao specialDao;
    private Weapon weapon;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        weaponDao = db.getWeaponDao();
        subDao = db.getSubDao();
        specialDao = db.getSpecialDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            weapon = gson.fromJson(deserializedHelper.getJSON("weapon.json"), Weapon.class);
            subDao.insertSub(weapon.sub);
            specialDao.insertSpecial(weapon.special);
            weaponDao.insertWeapon(weapon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeDb() {
        weaponDao.delete(weapon);
        specialDao.delete(weapon.special);
        subDao.delete(weapon.sub);
        db.close();
    }

    @Test
    public void insert(){
        Weapon pulledWeapon = weaponDao.select(weapon.id);
        assertThat(pulledWeapon.id).isEqualTo(weapon.id);
        assertThat(pulledWeapon.name).isEqualTo(weapon.name);
        assertThat(pulledWeapon.url).isEqualTo(weapon.url);
    }

    @Test
    public void selectFromSpecial(){
        List<Weapon> pulledWeapons = weaponDao.selectFromSpecial(weapon.special.id);
        for(Weapon pulledWeapon :pulledWeapons){
            assertThat(pulledWeapon.special.id).isEqualTo(weapon.special.id);
        }
    }

    @Test
    public void selectFromSub(){
        List<Weapon> pulledWeapons = weaponDao.selectFromSub(weapon.sub.id);
        for(Weapon pulledWeapon :pulledWeapons){
            assertThat(pulledWeapon.sub.id).isEqualTo(weapon.sub.id);
        }
    }
}
