package com.mattrubacky.monet2.backend.api.splatnet

import com.mattrubacky.monet2.data.deserialized.splatoon.CampaignRecords
import retrofit2.Call

class CampaignRecordsRequest(splatnet:Splatnet,cookie:String,uniqueId:String):SplatnetRequest<CampaignRecords>(splatnet,cookie,uniqueId) {
    override var call: Call<CampaignRecords> = splatnet.getHeroData(cookie,uniqueId)
}