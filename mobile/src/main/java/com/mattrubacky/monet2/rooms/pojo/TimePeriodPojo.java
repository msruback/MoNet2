package com.mattrubacky.monet2.rooms.pojo;

import com.mattrubacky.monet2.deserialized.splatoon.KeyName;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.rooms.entity.StageRoom;
import com.mattrubacky.monet2.rooms.entity.TimePeriodRoom;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TimePeriodPojo {

    @Embedded
    public TimePeriodRoom timePeriodRoom;

    @Relation(parentColumn = "a",
            entityColumn = "id")
    public StageRoom a;

    @Relation(parentColumn = "b",
            entityColumn = "id")
    public StageRoom b;

    public TimePeriod toDeserialized(){
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.rule = timePeriodRoom.rule;
        timePeriod.gamemode = timePeriodRoom.mode;
        timePeriod.start = timePeriodRoom.start;
        timePeriod.end = timePeriodRoom.end;
        timePeriod.a = a.toDeserialized();
        timePeriod.b = b.toDeserialized();
        return timePeriod;
    }
}
