package com.mattrubacky.monet2.rooms.entity;

import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TimePeriodRoomTest {

    @Test
    public void toDeserialized() {
        List<StageRoom> stages = new LinkedList<>();
        stages.add(new StageRoom(1,"url","The Reef"));
        stages.add(new StageRoom(2,"url","Musselforge Fitness"));

        TimePeriodRoom timePeriodRoom = new TimePeriodRoom(1,10,11,"turf_war","regular",1,2);

        TimePeriod timePeriod = timePeriodRoom.toDeserialized(stages);

        assertEquals("regular",timePeriod.gamemode.key);
        assertEquals("turf_war",timePeriod.rule.key);
        assertEquals(Long.valueOf(10),timePeriod.start);
        assertEquals(Long.valueOf(11),timePeriod.end);
        assertEquals(1,timePeriod.a.id);
        assertEquals("The Reef",timePeriod.a.name);
        assertEquals("url",timePeriod.a.url);
        assertEquals(2,timePeriod.b.id);
        assertEquals("Musselforge Fitness",timePeriod.b.name);
        assertEquals("url",timePeriod.b.url);
    }
}