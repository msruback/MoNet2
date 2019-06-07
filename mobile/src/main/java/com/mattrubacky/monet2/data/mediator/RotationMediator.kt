package com.mattrubacky.monet2.data.mediator

import com.mattrubacky.monet2.data.combo.ShiftWeapon
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonSchedule
import com.mattrubacky.monet2.data.deserialized.splatoon.Schedules
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear
import com.mattrubacky.monet2.data.deserialized_entities.Stage

class RotationMediator {
    var schedules = Schedules()
    var salmonSchedule = SalmonSchedule()
    var rewardGear = RewardGear()
    var splatfest = Splatfest()
    var stages:List<Stage> = ArrayList()
    var weapons:List<ShiftWeapon> = ArrayList()
}