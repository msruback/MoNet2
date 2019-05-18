package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Sub;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SubDaoTest {
    private TestDatabase db;
    private SubDao subDao;
    private Sub sub;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        subDao = db.getSubDao();
        try {
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            sub = gson.fromJson(deserializedHelper.getJSON("sub.json"), Sub.class);
            subDao.insertSub(sub);
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
        LiveData<Sub> subLiveData = subDao.select(sub.id);
        subLiveData.observeForever(new Observer<Sub>() {
            @Override
            public void onChanged(Sub pulledSub) {
                assertThat(pulledSub.id).isEqualTo(sub.id);
                assertThat(pulledSub.name).isEqualTo(sub.name);
                assertThat(pulledSub.url).isEqualTo(sub.url);
            }
        });
    }
}
