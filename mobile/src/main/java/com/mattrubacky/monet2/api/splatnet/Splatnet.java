package com.mattrubacky.monet2.api.splatnet;

/**
 * Created by mattr on 9/11/2017.
 */

import com.mattrubacky.monet2.deserialized.splatoon.Annie;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.CampaignRecords;
import com.mattrubacky.monet2.deserialized.splatoon.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.splatoon.PastSplatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.deserialized.splatoon.ResultList;
import com.mattrubacky.monet2.deserialized.splatoon.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.splatoon.Schedules;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestVotes;
import com.mattrubacky.monet2.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.Timeline;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Splatnet {

    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate",
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "origin: https://app.splatoon2.nintendo.net",
            "Connection: keep-alive"
    })
    @GET("/")
    Call<ResponseBody> getHomepage(@Header("X-GameWebToken") String token);

//    @Headers({
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate",
//            "Accept-Language: en-US",
//            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
//            "X-Requested-With: XMLHttpRequest",
//            "x-timezone-offset: 240",
//            "Connection: keep-alive",
//            "Referer: https://app.splatoon2.nintendo.net/home"
//    })
    @GET("api/timeline")
    Call<Timeline> getTimeline(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

//    @Headers({
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate",
//            "Accept-Language: en-US",
//            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
//            "X-Requested-With: XMLHttpRequest",
//            "x-timezone-offset: 240",
//            "Connection: keep-alive",
//            "Referer: https://app.splatoon2.nintendo.net/results"
//    })
    @GET("api/results")
    Call<ResultList> get50Results(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    //https://app.splatoon2.nintendo.net/results/{id}
//    @Headers({
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate",
//            "Accept-Language: en-US",
//            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
//            "X-Requested-With: XMLHttpRequest",
//            "x-timezone-offset: 240",
//            "Connection: keep-alive"
//    })
    @GET("api/results/{battle}")
    Call<Battle> getBattle(@Path("battle") String battle, @Header("Referer") String referer, @Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

//    @Headers({
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate",
//            "Accept-Language: en-US",
//            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
//            "X-Requested-With: XMLHttpRequest",
//            "x-timezone-offset: 240",
//            "Connection: keep-alive",
//            "Referer: https://app.splatoon2.nintendo.net/home"
//    })
    @GET("api/records")
    Call<Record> getRecords(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

//    @Headers({
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate",
//            "Accept-Language: en-US",
//            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
//            "X-Requested-With: XMLHttpRequest",
//            "x-timezone-offset: 240",
//            "Connection: keep-alive",
//            "Referer: https://app.splatoon2.nintendo.net/records"
//    })
    @GET("api/records/hero")
    Call<CampaignRecords> getHeroData(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/home/schedules"
    })
    @GET("api/schedules")
    Call<Schedules> getSchedules(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/home/coop"
    })
    @GET("api/coop_schedules")
    Call<SalmonSchedule> getSalmonSchedule(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/home"
    })
    @GET("api/data/stages")
    Call<ArrayList<Stage>> getStages(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/home"
    })
    @GET("api/festivals/active")
    Call<CurrentSplatfest> getActiveSplatfests(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/records/festival"
    })
    @GET("api/festivals/pasts")
    Call<PastSplatfest> getPastSplatfests(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive"
    })
    @GET("api/festivals/{id}/votes")
    Call<SplatfestVotes> getSplatfestVotes(@Path("id") String id, @Header("Referer") String referer, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    //https://app.splatoon2.nintendo.net/records/festival/{id}
    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive"
    })
    @GET("api/festivals/{id}/rankings")
    Call<ResponseBody> getSplatfestRanks(@Path("id") String id,@Header("Referer") String referer, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    //https://app.splatoon2.nintendo.net/records/festival/{id}
    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "origin: https://app.splatoon2.nintendo.net",
            "Connection: keep-alive"
    })
    @GET("api/festivals/{id}/results")
    Call<ResponseBody> getSplatfestResult(@Path("id") String id, @Header("Referer") String referer, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: : https://app.splatoon2.nintendo.net/home"
    })
    @GET("nickname_and_icon")
    Call<ResponseBody> getNicknameIcon(@Query("id") String id, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "Connection: keep-alive",
            "Referer: https://app.splatoon2.nintendo.net/shop"
    })
    @GET("api/onlineshop/merchandises")
    Call<Annie> getShop(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate",
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "origin: https://app.splatoon2.nintendo.net",
            "Connection: keep-alive"
    })
    @Multipart
    @POST("api/onlineshop/order/{id}")
    Call<ResponseBody> orderMerch(@Path("id") String id, @Part("override") RequestBody override,@Header("Referer") String referer, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    //https://app.splatoon2.nintendo.net/records/x_ranking/{time}
    @Headers({
            "Accept-Language: en-US",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "x-timezone-offset: 240",
            "origin: https://app.splatoon2.nintendo.net",
            "Connection: keep-alive"
    })
    @POST("api/x_power_ranking/{time}/summary")
    Call<ResponseBody> getXPowerSummary(@Path("time") String time,@Header("Referer") String referer, @Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);


    @GET("api/share/profile")
    Call<ResponseBody> shareProfile(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/results/summary")
    Call<ResponseBody> shareSummary(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/results/{battle}")
    Call<ResponseBody> shareBattle(@Path("battle") String battle,@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/challenges/{challenge}")
    Call<ResponseBody> shareChallenge(@Path("challenge") String challenge,@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);



}
