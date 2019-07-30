package com.mattrubacky.monet2.backend.api.splatnet

import com.mattrubacky.monet2.data.deserialized.splatoon.CoopResults
import retrofit2.Call
import retrofit2.Response

class CoopResultsRequest(splatnet:Splatnet,cookie:String,uniqueId:String):SplatnetRequest<CoopResults>(splatnet,cookie,uniqueId){
    override var call: Call<CoopResults> = splatnet.getSalmonResults(cookie,uniqueId)
    override fun manageReponse(response: Response<CoopResults>) {
        super.manageReponse(response)

    }
}