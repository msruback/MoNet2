package com.mattrubacky.monet2.backend.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.mattrubacky.monet2.backend.api.splatnet.*
import com.mattrubacky.monet2.data.combo.RewardGearCombo
import com.mattrubacky.monet2.data.combo.SalmonShiftCombo
import com.mattrubacky.monet2.data.combo.ShiftWeapon
import com.mattrubacky.monet2.data.combo.SplatfestStageCombo
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest
import com.mattrubacky.monet2.data.deserialized_entities.*
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom
import com.mattrubacky.monet2.data.mediator.RotationMediator
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import java.util.*
import kotlin.collections.ArrayList

class RotationViewModel(application: Application) : AndroidViewModel(application){
    private val rotationMediator = MediatorLiveData<RotationMediator>()
    private val db = SplatnetDatabase.getInstance(application)

    init {

        val connector = SplatnetConnector(application)

        connector.addRequest(RecordsRequest(application))
        connector.addRequest(SchedulesRequest(application))
        connector.addRequest(CoopSchedulesRequest(application,true))
        connector.addRequest(TimelineRequest(application))

        connector.execute()

        val now = Calendar.getInstance().timeInMillis/1000

        val stages = db.stageDao.selectAll()

        val regular = db.timePeriodDao.selectRegular(now)
        val ranked = db.timePeriodDao.selectGachi(now)
        val league = db.timePeriodDao.selectLeague(now)
        val festival = db.timePeriodDao.selectFestival(now)

        val salmonSchedule = db.salmonShiftDao.selectUpcoming(now)
        val salmonWeapons = db.salmonWeaponDao.selectCurrent(now)
        val salmonGear = db.salmonGearDao.selectCurrentGear(RewardGear.generateId(now))
        val splatfest = db.splatfestDao.selectUpcoming(now)

        rotationMediator.value = RotationMediator()

        rotationMediator.addSource(stages) { result: List<Stage>? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.stages = result ?: ArrayList()
            if (updatedValue.stages.isNotEmpty()){
                if (!updatedValue.schedules.regular.isNullOrEmpty()) {
                    updatedValue.schedules.regular = insertStages(updatedValue.schedules.regular, updatedValue.stages)
                }
                if(!updatedValue.schedules.ranked.isNullOrEmpty()){
                    updatedValue.schedules.ranked = insertStages(updatedValue.schedules.ranked, updatedValue.stages)
                }
                if(!updatedValue.schedules.league.isNullOrEmpty()){
                    updatedValue.schedules.league = insertStages(updatedValue.schedules.league, updatedValue.stages)
                }
                if(!updatedValue.schedules.splatfest.isNullOrEmpty()){
                    updatedValue.schedules.splatfest = insertStages(updatedValue.schedules.splatfest, updatedValue.stages)
                }
            }
            rotationMediator.setValue(updatedValue)
        }

        rotationMediator.addSource(regular){result: List<TimePeriod>? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.schedules.regular = result
            if(updatedValue.stages.isNotEmpty()&&updatedValue.schedules.regular.isNotEmpty()) {
                updatedValue.schedules.regular = insertStages(updatedValue.schedules.regular, updatedValue.stages)
            }
            rotationMediator.setValue(updatedValue)
        }
        rotationMediator.addSource(ranked){result: List<TimePeriod>? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.schedules.ranked = result
            if(updatedValue.stages.isNotEmpty()&&updatedValue.schedules.ranked.isNotEmpty()) {
                updatedValue.schedules.ranked = insertStages(updatedValue.schedules.ranked, updatedValue.stages)
            }
            rotationMediator.setValue(updatedValue)
        }
        rotationMediator.addSource(league){result: List<TimePeriod>? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.schedules.league = result
            if(updatedValue.stages.isNotEmpty()&&updatedValue.schedules.league.isNotEmpty()) {
                updatedValue.schedules.league = insertStages(updatedValue.schedules.league, updatedValue.stages)
            }
            rotationMediator.setValue(updatedValue)
        }
        rotationMediator.addSource(festival){result: List<TimePeriod>? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.schedules.splatfest = result
            if(updatedValue.stages.isNotEmpty()&&updatedValue.schedules.splatfest.isNotEmpty()) {
                updatedValue.schedules.splatfest = insertStages(updatedValue.schedules.splatfest, updatedValue.stages)
            }
            rotationMediator.setValue(updatedValue)
        }

        rotationMediator.addSource(salmonSchedule){result: List<SalmonShiftCombo>? ->
            var details = ArrayList<SalmonRunDetail>()
            val times = ArrayList<SalmonRun>()
            val detailIds = ArrayList<Int>()
            result?.forEach { shift: SalmonShiftCombo ->
                if(shift.salmonStage==null){
                    times.add(shift.salmonShiftRoom.toDeserialised())
                }else{
                    details.add(shift.toDeserialised())
                    detailIds.add(shift.salmonShiftRoom.id)
                }
            }
            val updatedValue = rotationMediator.value!!
            if(!updatedValue.weapons.isNullOrEmpty()){
                val updatedDetails = ArrayList<SalmonRunDetail>()
                details.forEach { shift: SalmonRunDetail ->
                    shift.weapons = ArrayList()
                    updatedValue.weapons.forEach { weapon ->
                        if (SalmonShiftRoom.generateId(shift.start) == weapon.id) {
                            shift.weapons.add(SalmonRunWeapon(weapon.weapon))
                        }
                    }
                    updatedDetails.add(shift)
                }
                details = updatedDetails
            }
            updatedValue.salmonSchedule.details = details
            updatedValue.salmonSchedule.times = times
            rotationMediator.setValue(updatedValue)
        }
        rotationMediator.addSource(salmonWeapons){result: List<ShiftWeapon>->
            val weaponedRotation = rotationMediator.value!!
            val weaponShifts = ArrayList<SalmonRunDetail>()
            if(!weaponedRotation.salmonSchedule.details.isNullOrEmpty()) {
                weaponedRotation.salmonSchedule.details.forEach { shift: SalmonRunDetail ->
                    result.forEach { weapon ->
                        if (SalmonShiftRoom.generateId(shift.start) == weapon.id) {
                            shift.weapons.add(SalmonRunWeapon(weapon.weapon))
                        }
                    }
                    weaponShifts.add(shift)
                }
            }
            weaponedRotation.weapons = result
            weaponedRotation.salmonSchedule.details = weaponShifts
            rotationMediator.setValue(weaponedRotation)
        }
        rotationMediator.addSource(salmonGear){result: RewardGearCombo? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.rewardGear = if(result!=null) result.toDeserialized() else RewardGear()
            rotationMediator.setValue(updatedValue)
        }

        rotationMediator.addSource(splatfest){result: SplatfestStageCombo? ->
            val updatedValue = rotationMediator.value!!
            updatedValue.splatfest = if(result!=null) result.toDeserialized() else Splatfest()
            rotationMediator.setValue(updatedValue)
        }
    }
    fun getRotation(): MediatorLiveData<RotationMediator>{
        return rotationMediator
    }

    private fun insertStages(schedule:List<TimePeriod>, stages:List<Stage>): List<TimePeriod>{
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

    @Override
    override fun onCleared() {
        super.onCleared()
        db.close()
    }
}