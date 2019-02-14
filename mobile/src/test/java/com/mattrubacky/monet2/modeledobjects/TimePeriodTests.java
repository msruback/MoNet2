package com.mattrubacky.monet2.modeledobjects;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TimePeriodTests extends ModeledObjectTest{

    @Test
    public void toDeserialized() {
        List<StageRoom> stages = new LinkedList<>();
        try {
            String[] stage1 = getCSV("stage_1.csv");
            stages.add(new StageRoom(Integer.valueOf(stage1[2]),stage1[1], stage1[0]));

            String[] stage2 = getCSV("stage_2.csv");
            stages.add(new StageRoom(Integer.valueOf(stage2[2]),stage2[1],stage2[0]));

            String[] timePeriodCsv = getCSV("tp_regular.csv");
            TimePeriodRoom timePeriodRoom = new TimePeriodRoom(Integer.valueOf(timePeriodCsv[0]),Long.valueOf(timePeriodCsv[1]),Long.valueOf(timePeriodCsv[2]),timePeriodCsv[4],timePeriodCsv[3],Integer.valueOf(stage1[2]),Integer.valueOf(stage2[2]));

            TimePeriod timePeriod = timePeriodRoom.toDeserialized(stages);

            assertEquals(timePeriodCsv[3],timePeriod.gamemode.key);
            assertEquals(timePeriodCsv[4],timePeriod.rule.key);
            assertEquals(Long.valueOf(timePeriodCsv[1]),timePeriod.start);
            assertEquals(Long.valueOf(timePeriodCsv[2]),timePeriod.end);
            assertEquals((int) Integer.valueOf(stage1[2]),timePeriod.a.id);
            assertEquals(stage1[1],timePeriod.a.url);
            assertEquals(stage1[0],timePeriod.a.name);
            assertEquals((int) Integer.valueOf(stage2[2]),timePeriod.b.id);
            assertEquals(stage2[1],timePeriod.b.url);
            assertEquals(stage2[0],timePeriod.b.name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toRoom(){
        Gson gson = new Gson();
        try {
            TimePeriod timePeriod = gson.fromJson(getJSON("tp_regular.json"), TimePeriod.class);
            TimePeriodRoom timePeriodRoom = timePeriod.toRoom();

            assertEquals(timePeriod.gamemode.key,timePeriodRoom.mode);
            assertEquals(timePeriod.rule.key,timePeriodRoom.rule);
            assertEquals((long)timePeriod.start,timePeriodRoom.start);
            assertEquals((long)timePeriod.end,timePeriodRoom.end);
            assertEquals(timePeriod.a.id,timePeriodRoom.a);
            assertEquals(timePeriod.b.id,timePeriodRoom.b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}