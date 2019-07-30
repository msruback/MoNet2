package com.mattrubacky.monet2.backend.api.splatnet

import com.mattrubacky.monet2.data.deserialized.splatoon.CoopResult
import retrofit2.Call

class CoopResultRequest(id:Int,splatnet:Splatnet,cookie:String,uniqueId:String): SplatnetRequest<CoopResult>(splatnet,cookie,uniqueId) {
    private val referer = "https://app.splatoon2.nintendo.net/coop_results/$id"
    override var call: Call<CoopResult> = splatnet.getSalmonResult(id.toString(),referer,cookie,uniqueId)
}