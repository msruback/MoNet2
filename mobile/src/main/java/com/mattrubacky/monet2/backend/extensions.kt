package com.mattrubacky.monet2.backend

import com.mattrubacky.monet2.data.deserialized_entities.Stage
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod
import java.util.*

fun getCurrentTime():Long{
    return Calendar.getInstance().timeInMillis / 1000
}

fun insertStages(schedule:List<TimePeriod>, stages:List<Stage>): ArrayList<TimePeriod>{
    val stageSchedule = ArrayList<TimePeriod>()
    schedule.forEach { timePeriod: TimePeriod ->
        stages.forEach { stage: Stage ->
            when(stage.id){
                timePeriod.a.id -> timePeriod.a = stage
                timePeriod.b.id -> timePeriod.b = stage
            }
        }
        stageSchedule.add(timePeriod)
    }
    return stageSchedule
}