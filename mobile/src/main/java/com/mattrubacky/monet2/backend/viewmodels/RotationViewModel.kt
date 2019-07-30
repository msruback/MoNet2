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
import com.mattrubacky.monet2.data.parsley.Bunch
import com.mattrubacky.monet2.data.parsley.Bunny
import com.mattrubacky.monet2.data.parsley.splatnet.bunch.RotationBunch
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import java.util.*
import kotlin.collections.ArrayList

class RotationViewModel(application: Application) : AndroidViewModel(application){
    private val db = SplatnetDatabase.getInstance(application)
    private val api = SplatnetParsley(application)
    private val mochi = Bunny(SplatnetParsley(application), SplatnetDatabase.getInstance(application))

    infix fun feedMo(bunch: Bunch<SplatnetParsley,SplatnetDatabase>): MediatorLiveData<Bunch<SplatnetParsley,SplatnetDatabase>>{
        return mochi monch bunch
    }

    override fun onCleared() {
        super.onCleared()
        db.close()
    }


}