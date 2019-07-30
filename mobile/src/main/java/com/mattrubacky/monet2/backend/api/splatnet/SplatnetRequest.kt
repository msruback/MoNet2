package com.mattrubacky.monet2.backend.api.splatnet

import com.mattrubacky.monet2.backend.api.Request
import retrofit2.Response

abstract class SplatnetRequest<T>(splatnet:Splatnet,cookie:String,uniqueId:String): Request<T>() {
    override fun manageError(response: Response<T>) {
        error = if(response.code()==200){
            "Splatnet is down for Maintenance."
        }else{
            "User Authentication failed"
        }
    }
}