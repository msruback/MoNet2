package com.mattrubacky.monet2.data.parsley

import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

class Bunny<Source,Store>(private val source:Source, private val store:Store){

    infix fun <T:Bunch<Source,Store>> monch(parsley:T):MediatorLiveData<T>{
        val kiss = MediatorLiveData<T>()
        var i = 0
        while(i<parsley.sprigs.size){
            val currentSprig = parsley.sprigs[i]
            kiss.addSource(currentSprig.getLiveData(store)){
                val updatedValue = kiss.value!!
                updatedValue.sprigs[i] = it as Sprig<Source, Store>
                kiss.setValue(updatedValue)
            }
            GlobalScope.launch(Dispatchers.IO){dream(parsley.sprigs)}
            i++
        }

        return kiss
    }

    private suspend fun dream(sprigs:ArrayList<Sprig<Source,Store>>) = withContext(Dispatchers.IO){
        for(sprig in sprigs){
            sprig.checkStore(store)
            sprig.getFromSource(source)
            sprig.putInStore(store)
        }
    }
}