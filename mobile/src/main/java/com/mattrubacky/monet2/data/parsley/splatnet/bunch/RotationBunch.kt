package com.mattrubacky.monet2.data.parsley.splatnet.bunch

import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.data.combo.ShiftWeapon
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.SalmonSchedule
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.Schedules
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.CurrentSplatfest
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear
import com.mattrubacky.monet2.data.deserialized_entities.Stage
import com.mattrubacky.monet2.data.parsley.Bunch
import com.mattrubacky.monet2.data.parsley.Sprig
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase

class RotationBunch :Bunch<SplatnetParsley,SplatnetDatabase>(){
    override var sprigs: ArrayList<Sprig<SplatnetParsley, SplatnetDatabase>> = arrayListOf(Schedules(),SalmonSchedule(),CurrentSplatfest())
    fun schedules() = sprigs[0]
    fun salmonSchedule() = sprigs[1]
    fun splatfest() = sprigs[2]
}