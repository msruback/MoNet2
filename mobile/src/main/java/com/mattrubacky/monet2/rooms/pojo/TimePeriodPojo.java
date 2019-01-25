package com.mattrubacky.monet2.rooms.pojo;

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
}
