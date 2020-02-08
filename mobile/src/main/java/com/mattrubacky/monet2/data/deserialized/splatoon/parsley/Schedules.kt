package com.mattrubacky.monet2.data.deserialized.splatoon.parsley

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.annotations.SerializedName
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.backend.getCurrentTime
import com.mattrubacky.monet2.backend.insertStages
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest
import com.mattrubacky.monet2.data.deserialized_entities.Stage
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod
import com.mattrubacky.monet2.data.parsley.Sprig
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import retrofit2.Response
import kotlin.collections.ArrayList

class Schedules() : Sprig<SplatnetParsley,SplatnetDatabase>,Parcelable{

    var needsUpdate: Boolean = true
        private set
    private var stages: List<Stage> = ArrayList()

    @SerializedName("regular")
    var regular: ArrayList<TimePeriod> = ArrayList()
    @SerializedName("gachi")
    var ranked: ArrayList<TimePeriod> = ArrayList()
    @SerializedName("league")
    var league: ArrayList<TimePeriod> = ArrayList()

    //splatfest is not in the Splatnet API, this is populated client side
    @SerializedName("fes")
    var splatfest: ArrayList<TimePeriod> = ArrayList()

    private fun setSplatfest(splatfest: Splatfest) {
        val start = splatfest.times.start
        val end = splatfest.times.end
        this.splatfest = java.util.ArrayList()
        for (i in regular.indices) {
            while (regular.size > i && regular[i].end > start && regular[i].start <= end) {
                this.splatfest.add(regular[i])
                regular.removeAt(i)
                ranked.removeAt(i)
                league.removeAt(i)
            }
        }
    }


    override suspend fun getFromSource(source: SplatnetParsley) {
        if(needsUpdate) {
            val schedules: Response<Schedules> = source.getSchedules().execute()
            val splatfest: Response<CurrentSplatfest> = source.getActiveSplatfests().execute()
            if (schedules.isSuccessful) {
                val schedule = schedules.body()!!
                regular = schedule.regular
                ranked = schedule.ranked
                league = schedule.league
                if (splatfest.isSuccessful && splatfest.body()!!.splatfests.isNotEmpty()) {
                    setSplatfest(splatfest.body()!!.splatfests[0])
                }
            }
            needsUpdate = false
        }
    }

    override suspend fun checkStore(store: SplatnetDatabase) {
        val old = store.timePeriodDao.selectOld(getCurrentTime())
        for(timePeriod in old){
            store.timePeriodDao.delete(timePeriod)
        }
        needsUpdate = old.isNotEmpty()
    }

    override suspend fun putInStore(store: SplatnetDatabase) {
        for(timePeriod in regular){
            store.timePeriodDao.insertTimePeriod(timePeriod,store.stageDao)
        }
        for(timePeriod in ranked){
            store.timePeriodDao.insertTimePeriod(timePeriod,store.stageDao)
        }
        for(timePeriod in league){
            store.timePeriodDao.insertTimePeriod(timePeriod,store.stageDao)
        }
        for(timePeriod in splatfest){
            store.timePeriodDao.insertTimePeriod(timePeriod,store.stageDao)
        }
    }

    override fun getLiveData(store: SplatnetDatabase): LiveData<Sprig<SplatnetParsley,SplatnetDatabase>> {
        val schedules:MediatorLiveData<Sprig<SplatnetParsley,SplatnetDatabase>> = MediatorLiveData()

        schedules.addSource(store.stageDao.selectAllLive()) { result: List<Stage>? ->
            val updatedValue = schedules.value!! as Schedules
            updatedValue.stages = result ?: ArrayList()
            if (updatedValue.stages.isNotEmpty()){
                if (!updatedValue.regular.isNullOrEmpty()) {
                    updatedValue.regular = insertStages(updatedValue.regular, updatedValue.stages)
                }
                if(!updatedValue.ranked.isNullOrEmpty()){
                    updatedValue.ranked = insertStages(updatedValue.ranked, updatedValue.stages)
                }
                if(!updatedValue.league.isNullOrEmpty()){
                    updatedValue.league = insertStages(updatedValue.league, updatedValue.stages)
                }
                if(!updatedValue.splatfest.isNullOrEmpty()){
                    updatedValue.splatfest = insertStages(updatedValue.splatfest, updatedValue.stages)
                }
            }
            schedules.setValue(updatedValue)
        }

        schedules.addSource(store.timePeriodDao.selectRegularLive(getCurrentTime())){ result: List<TimePeriod>? ->
            val updatedValue = schedules.value!! as Schedules
            updatedValue.regular = ArrayList(result!!)
            if(updatedValue.stages.isNotEmpty()&&updatedValue.regular.isNotEmpty()) {
                updatedValue.regular = insertStages(updatedValue.regular, updatedValue.stages)
            }
            schedules.setValue(updatedValue)
        }
        schedules.addSource(store.timePeriodDao.selectGachiLive(getCurrentTime())){ result: List<TimePeriod>? ->
            val updatedValue =schedules.value!! as Schedules
            updatedValue.ranked = ArrayList(result!!)
            if(updatedValue.stages.isNotEmpty()&&updatedValue.ranked.isNotEmpty()) {
                updatedValue.ranked = insertStages(updatedValue.ranked, updatedValue.stages)
            }
            schedules.setValue(updatedValue)
        }
        schedules.addSource(store.timePeriodDao.selectLeagueLive(getCurrentTime())){ result: List<TimePeriod>? ->
            val updatedValue = schedules.value!! as Schedules
            updatedValue.league = ArrayList(result!!)
            if(updatedValue.stages.isNotEmpty()&&updatedValue.league.isNotEmpty()) {
                updatedValue.league = insertStages(updatedValue.league, updatedValue.stages)
            }
            schedules.setValue(updatedValue)
        }
        schedules.addSource(store.timePeriodDao.selectFestivalLive(getCurrentTime())){ result: List<TimePeriod>? ->
            val updatedValue = schedules.value!! as Schedules
            updatedValue.splatfest = ArrayList(result!!)
            if(updatedValue.stages.isNotEmpty()&&updatedValue.splatfest.isNotEmpty()) {
                updatedValue.splatfest = insertStages(updatedValue.splatfest, updatedValue.stages)
            }
            schedules.setValue(updatedValue)
        }

        return schedules
    }

    constructor(parcel: Parcel) : this() {
        regular = parcel.createTypedArrayList(TimePeriod.CREATOR)!!
        ranked = parcel.createTypedArrayList(TimePeriod.CREATOR)!!
        league = parcel.createTypedArrayList(TimePeriod.CREATOR)!!
        splatfest = parcel.createTypedArrayList(TimePeriod.CREATOR)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(regular)
        parcel.writeTypedList(ranked)
        parcel.writeTypedList(league)
        parcel.writeTypedList(splatfest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Schedules> {
        override fun createFromParcel(parcel: Parcel): Schedules {
            return Schedules(parcel)
        }

        override fun newArray(size: Int): Array<Schedules?> {
            return arrayOfNulls(size)
        }
    }
}