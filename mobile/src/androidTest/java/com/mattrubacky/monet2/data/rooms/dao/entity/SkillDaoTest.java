package com.mattrubacky.monet2.data.rooms.dao.entity;
import android.content.Context;

import androidx.test.filters.SmallTest;

import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.data.rooms.TestDatabase;
import com.mattrubacky.monet2.testutils.DeserializedHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import static com.google.common.truth.Truth.assertThat;

@SmallTest
public class SkillDaoTest {

    private TestDatabase db;
    private Context context;
    private SkillDao skillDao;
    private Skill chunkableSkill,specialSkill,oldSkill;

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
        skillDao.delete(chunkableSkill);
        skillDao.delete(specialSkill);
        skillDao.delete(oldSkill);
        db.close();
    }

    @Test
    public void insert(){
        Skill pulledSkill = skillDao.select(chunkableSkill.id);
        assertThat(pulledSkill.id).isEqualTo(chunkableSkill.id);
        assertThat(pulledSkill.name).isEqualTo(chunkableSkill.name);
        assertThat(pulledSkill.url).isEqualTo(chunkableSkill.url);
    }

    @Test
    public void delete(){
        skillDao.delete(chunkableSkill);
        Skill pulledSkill = skillDao.select(chunkableSkill.id);
        assertThat(pulledSkill).isNull();
        skillDao.insertSkill(chunkableSkill);
    }

    @Test
    public void selectChunkable(){
        for(Skill skill:skillDao.selectChunkableSkills()){
            assertThat(skill.id).isNotIn( Range.openClosed(100,200));
            assertThat(skill.id).isNotEqualTo(12);
            assertThat(skill.id).isNotEqualTo(13);
        }
    }

    @Test
    public void selectSpecial(){
        for(Skill skill:skillDao.selectSpecialSkills()){
            assertThat(skill.id).isIn( Range.openClosed(100,200));
        }
    }
}
