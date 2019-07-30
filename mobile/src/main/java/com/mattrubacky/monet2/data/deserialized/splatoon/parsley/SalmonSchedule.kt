package com.mattrubacky.monet2.data.deserialized.splatoon.parsley

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.annotations.SerializedName
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.backend.getCurrentTime
import com.mattrubacky.monet2.data.combo.SalmonShiftCombo
import com.mattrubacky.monet2.data.combo.ShiftWeapon
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom
import com.mattrubacky.monet2.data.parsley.Sprig
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import java.util.ArrayList

class SalmonSchedule(): Sprig<SplatnetParsley, SplatnetDatabase>, Parcelable {

    private var needsUpdate: Boolean = true
    private var weapons :List<ShiftWeapon> = ArrayList()
    @SerializedName("details")
    var details: ArrayList<SalmonRunDetail> = ArrayList()
    @SerializedName("schedules")
    var times: ArrayList<SalmonRun> = ArrayList()

    override suspend fun getFromSource(source: SplatnetParsley) {
        if(needsUpdate) {
            val schedule = source.getSalmonSchedule().execute()
            if (schedule.isSuccessful) {
                details = schedule.body()!!.details
                times = schedule.body()!!.times
            }
        }
    }

    override suspend fun checkStore(store: SplatnetDatabase) {
        val schedule = store.salmonShiftDao.selectUpcoming(getCurrentTime())
        needsUpdate = schedule.size<5
    }

    override suspend fun putInStore(store: SplatnetDatabase) {
        for(salmonRun in times){
            store.salmonShiftDao.insertSalmonShift(salmonRun)
        }
        for(salmonRun in details){
            store.salmonShiftDao.insertSalmonShift(salmonRun,store.salmonStageDao,store.salmonWeaponDao,store.weaponDao)
        }
    }

    override fun getLiveData(store: SplatnetDatabase): LiveData<Sprig<SplatnetParsley,SplatnetDatabase>> {
        val salmonSchedule = MediatorLiveData<Sprig<SplatnetParsley,SplatnetDatabase>>()
        salmonSchedule.addSource(store.salmonWeaponDao.selectCurrent(getCurrentTime())){ result: List<ShiftWeapon>->
            val weaponedRotation = salmonSchedule.value!! as SalmonSchedule
            val weaponShifts = ArrayList<SalmonRunDetail>()
            if(!weaponedRotation.details.isNullOrEmpty()) {
                weaponedRotation.details.forEach { shift: SalmonRunDetail ->
                    result.forEach { weapon ->
                        if (SalmonShiftRoom.generateId(shift.start) == weapon.id) {
                            shift.weapons.add(SalmonRunWeapon(weapon.weapon))
                        }
                    }
                    weaponShifts.add(shift)
                }
            }
            weaponedRotation.weapons = result
            weaponedRotation.details = weaponShifts
            salmonSchedule.setValue(weaponedRotation)
        }
        salmonSchedule.addSource(store.salmonShiftDao.selectUpcomingLive(getCurrentTime())){
            val updatedValue = salmonSchedule.value as SalmonSchedule
            var details = ArrayList<SalmonRunDetail>()
            val times = ArrayList<SalmonRun>()
            val detailIds = ArrayList<Int>()
            it?.forEach { shift: SalmonShiftCombo ->
                if(shift.salmonStage==null){
                    times.add(shift.salmonShiftRoom.toDeserialised())
                }else{
                    details.add(shift.toDeserialised())
                    detailIds.add(shift.salmonShiftRoom.id)
                }
            }
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
            updatedValue.details = details
            updatedValue.times = times
            salmonSchedule.setValue(updatedValue)
        }
        return salmonSchedule
    }

    constructor(parcel: Parcel) : this() {
        details = parcel.createTypedArrayList(SalmonRunDetail.CREATOR)
        times = parcel.createTypedArrayList(SalmonRun.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(details)
        parcel.writeTypedList(times)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SalmonSchedule> {
        override fun createFromParcel(parcel: Parcel): SalmonSchedule {
            return SalmonSchedule(parcel)
        }

        override fun newArray(size: Int): Array<SalmonSchedule?> {
            return arrayOfNulls(size)
        }
    }
}