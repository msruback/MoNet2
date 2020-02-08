package com.mattrubacky.monet2.backend.api

import retrofit2.Call
import retrofit2.Response

abstract class Request<T>{
    abstract var call: Call<T>
    var value:T? = null
    var error:String? = null
    private var isSuccess:Boolean = false

    abstract fun manageError(response: Response<T>)

    private fun shouldUpdate():Boolean = true

    open fun manageReponse(response: Response<T>){
        value = response.body()
    }

    fun run(){
        if(shouldUpdate()){
            val response = call.execute()
            if(response.isSuccessful){
                isSuccess = true
                manageReponse(response)
            }else{
                manageError(response)
            }
        }
    }
}