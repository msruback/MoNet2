package com.mattrubacky.monet2.data.deserialized.splatoon.parsley

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.data.deserialized.splatoon.Ordered
import com.mattrubacky.monet2.data.deserialized.splatoon.Product
import com.mattrubacky.monet2.data.parsley.Sprig
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import retrofit2.Response
import java.util.ArrayList

class Annie() : Sprig<SplatnetParsley,SplatnetDatabase>, Parcelable {

    //The Gear that is ordered and currently awaiting pickup at Merch
    @SerializedName("ordered_info")
    var ordered: Ordered? = null
    //Gear that is for sale currently in the shop
    @SerializedName("merchandises")
    var merch: List<Product> = ArrayList()

    override suspend fun getFromSource(source: SplatnetParsley) {
        val annie: Response<Annie> = source.getShop().execute()
        if(annie.isSuccessful){
            ordered = annie.body()!!.ordered
            merch = annie.body()!!.merch
        }
    }

    override suspend fun checkStore(store: SplatnetDatabase) {
        TODO("Shop Dao needs to be implemented")
    }

    override suspend fun putInStore(store: SplatnetDatabase) {
        TODO("Shop Dao needs to be implemented")
    }

    override fun getLiveData(store: SplatnetDatabase): LiveData<Sprig<SplatnetParsley,SplatnetDatabase>> {
        TODO("Shop Dao needs to be implemented")
    }

    //Parcelable Implementation

    constructor(parcel: Parcel) : this() {
        ordered = parcel.readParcelable(Ordered::class.java.classLoader)
        merch = parcel.createTypedArrayList(Product.CREATOR)!!
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(ordered, flags)
        dest.writeTypedList(merch)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Annie> {
        override fun createFromParcel(parcel: Parcel): Annie {
            return Annie(parcel)
        }

        override fun newArray(size: Int): Array<Annie?> {
            return arrayOfNulls(size)
        }
    }
}