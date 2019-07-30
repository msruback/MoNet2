package com.mattrubacky.monet2.backend.api.splatnet

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.mattrubacky.monet2.data.deserialized.splatoon.*
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.Annie
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.CurrentSplatfest
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.SalmonSchedule
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.Schedules
import com.mattrubacky.monet2.data.deserialized_entities.Stage
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SplatnetParsley(context:Context) {
    val splatnet:Splatnet
    val cookie:String
    val uniqueId:String
    init {
        val gson = GsonBuilder().create()

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://app.splatoon2.nintendo.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        splatnet = retrofit.create(Splatnet::class.java)
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        cookie = settings.getString("cookie", "")?:""
        uniqueId = settings.getString("unique_id", "")?:""
    }

    fun getTimeline(): Call<Timeline> = splatnet.getTimeline(cookie,uniqueId)

    fun get50Results(): Call<ResultList> = splatnet.get50Results(cookie, uniqueId)

    fun getBattle(id:Int): Call<Battle> = getBattle(id.toString())

    fun getBattle(id:String):Call<Battle> = splatnet.getBattle(id,"https://app.splatoon2.nintendo.net/results/$id",cookie,uniqueId)

    fun getSalmonResults():Call<CoopResults> = splatnet.getSalmonResults(cookie, uniqueId)

    fun getSalmonResult(id:Int):Call<CoopResult> = getSalmonResult(id.toString())

    fun getSalmonResult(id:String):Call<CoopResult> = splatnet.getSalmonResult(id,"https://app.splatoon2.nintendo.net/coop_results/$id",cookie,uniqueId)

    fun getRecords():Call<Record> = splatnet.getRecords(cookie,uniqueId)

    fun getHeroData():Call<CampaignRecords> = splatnet.getHeroData(cookie,uniqueId)

    fun getSchedules():Call<Schedules> = splatnet.getSchedules(cookie,uniqueId)

    fun getSalmonSchedule():Call<SalmonSchedule> = splatnet.getSalmonSchedule(cookie,uniqueId)

    fun getStages():Call<ArrayList<Stage>> = splatnet.getStages(cookie,uniqueId)

    fun getActiveSplatfests():Call<CurrentSplatfest> = splatnet.getActiveSplatfests(cookie,uniqueId)

    fun getPastSplatfests():Call<PastSplatfest> = splatnet.getPastSplatfests(cookie,uniqueId)

    fun getSplatfestVotes(id: Int):Call<SplatfestVotes> = getSplatfestVotes(id.toString())

    fun getSplatfestVotes(id: String):Call<SplatfestVotes> = splatnet.getSplatfestVotes(id,"https://app.splatoon2.nintendo.net/records/festival/$id",cookie,uniqueId)

    fun getSplatfestRanks(id: Int):Call<ResponseBody> = getSplatfestRanks(id.toString())

    fun getSplatfestRanks(id: String):Call<ResponseBody> = splatnet.getSplatfestRanks(id,"https://app.splatoon2.nintendo.net/records/festival/$id",cookie,uniqueId)

    fun getSplatfestResult(id: Int):Call<ResponseBody> = getSplatfestResult(id.toString())

    fun getSplatfestResult(id: String):Call<ResponseBody> = splatnet.getSplatfestResult(id,"https://app.splatoon2.nintendo.net/records/festival/$id",cookie, uniqueId)

    fun getNicknameIcon(id: String):Call<ResponseBody> = splatnet.getNicknameIcon(id,cookie,uniqueId)

    fun getShop():Call<Annie> = splatnet.getShop(cookie, uniqueId)

    fun orderMerch(id: String):Call<ResponseBody> = splatnet.orderMerch(id, RequestBody.create(MediaType.parse("text/plain"), "1"),"https://app.splatoon2.nintendo.net/shop/$id", cookie, uniqueId)
}