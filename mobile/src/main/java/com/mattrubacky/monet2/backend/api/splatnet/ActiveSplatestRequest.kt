package com.mattrubacky.monet2.backend.api.splatnet

import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.CurrentSplatfest
import retrofit2.Call

class ActiveSplatestRequest(splatnet: Splatnet,cookie:String,uniqueId:String):SplatnetRequest<CurrentSplatfest>(splatnet,cookie,uniqueId)  {
    override var call: Call<CurrentSplatfest> = splatnet.getActiveSplatfests(cookie,uniqueId)
}