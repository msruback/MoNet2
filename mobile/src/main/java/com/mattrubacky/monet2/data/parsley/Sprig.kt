package com.mattrubacky.monet2.data.parsley

import androidx.lifecycle.LiveData

interface Sprig<Source,Store>{
    suspend fun getFromSource(source: Source)
    suspend fun checkStore(store:Store)
    suspend fun putInStore(store:Store)
    fun getLiveData(store: Store):LiveData<Sprig<Source,Store>>
}