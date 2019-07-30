package com.mattrubacky.monet2.data.deserialized.splatoon.parsley

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.annotations.SerializedName
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.backend.getCurrentTime
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest
import com.mattrubacky.monet2.data.parsley.Sprig
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import retrofit2.Response
import java.util.*

class CurrentSplatfest() : Sprig<SplatnetParsley,SplatnetDatabase>,Parcelable{

    private var needsUpdate: Boolean = true
    //The list of upcoming and active splatfests. As far as I know this can only be size()==0||size()==1
    @SerializedName("festivals")
    var splatfests: List<Splatfest> = ArrayList()

    override suspend fun getFromSource(source: SplatnetParsley) {
        if(needsUpdate) {
            val splatfest: Response<CurrentSplatfest> = source.getActiveSplatfests().execute()
            if (splatfest.isSuccessful) {
                splatfests = splatfest.body()!!.splatfests
            }
            needsUpdate = false
        }
    }

    override suspend fun checkStore(store: SplatnetDatabase) {
        val splatfest = store.splatfestDao.selectUpcoming(getCurrentTime())
        needsUpdate = splatfest.splatestRoom==null
    }

    override suspend fun putInStore(store: SplatnetDatabase) {
        if(!splatfests.isNullOrEmpty()) {
            store.splatfestDao.insertSplatfest(splatfests[0], store.stageDao)
        }
    }

    override fun getLiveData(store: SplatnetDatabase): LiveData<Sprig<SplatnetParsley,SplatnetDatabase>> {
        val currentSplatfest = MediatorLiveData<Sprig<SplatnetParsley,SplatnetDatabase>>()
        currentSplatfest.addSource(store.splatfestDao.selectUpcomingLive(getCurrentTime())){
            val updatedValue = currentSplatfest.value as CurrentSplatfest
            updatedValue.splatfests = arrayListOf(it.toDeserialized())
            currentSplatfest.setValue(updatedValue)
        }
        return currentSplatfest
    }

    constructor(parcel: Parcel) : this() {
        splatfests = parcel.createTypedArrayList(Splatfest.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(splatfests)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrentSplatfest> {
        override fun createFromParcel(parcel: Parcel): CurrentSplatfest {
            return CurrentSplatfest(parcel)
        }

        override fun newArray(size: Int): Array<CurrentSplatfest?> {
            return arrayOfNulls(size)
        }
    }
}