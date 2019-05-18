package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.filters.SmallTest;

import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
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
public class SkillDaoTest {

    private TestDatabase db;
    private Context context;
    private SkillDao skillDao;
    private Skill chunkableSkill,specialSkill,oldSkill;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        skillDao = db.getSkillDao();
        try{
            DeserializedHelper deserializedHelper = new DeserializedHelper();
            Gson gson = new Gson();
            chunkableSkill = gson.fromJson(deserializedHelper.getJSON("skill_chunkable.json"),Skill.class);
            specialSkill = gson.fromJson(deserializedHelper.getJSON("skill_special.json"),Skill.class);
            oldSkill = gson.fromJson(deserializedHelper.getJSON("skill_old.json"),Skill.class);

            skillDao.insertSkill(chunkableSkill);
            skillDao.insertSkill(specialSkill);
            skillDao.insertSkill(oldSkill);

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
        LiveData<Skill> skillLiveData = skillDao.select(chunkableSkill.id);
        skillLiveData.observeForever(new Observer<Skill>() {
            @Override
            public void onChanged(Skill pulledSkill) {
                assertThat(pulledSkill.id).isEqualTo(chunkableSkill.id);
                assertThat(pulledSkill.name).isEqualTo(chunkableSkill.name);
                assertThat(pulledSkill.url).isEqualTo(chunkableSkill.url);
            }
        });
    }

    @Test
    public void selectChunkable(){
        LiveData<List<Skill>> skillLiveData = skillDao.selectChunkableSkills();
        skillLiveData.observeForever(new Observer<List<Skill>>() {
            @Override
            public void onChanged(List<Skill> skills) {
                for(Skill skill:skills){
                    assertThat(skill.id).isNotIn( Range.openClosed(100,200));
                    assertThat(skill.id).isNotEqualTo(12);
                    assertThat(skill.id).isNotEqualTo(13);
                }
            }
        });
    }

    @Test
    public void selectSpecial(){
        LiveData<List<Skill>> skillLiveData = skillDao.selectSpecialSkills();
        skillLiveData.observeForever(new Observer<List<Skill>>() {
            @Override
            public void onChanged(List<Skill> skills) {
                for(Skill skill:skills){
                    assertThat(skill.id).isIn( Range.openClosed(100,200));
                }
            }
        });
    }
}
