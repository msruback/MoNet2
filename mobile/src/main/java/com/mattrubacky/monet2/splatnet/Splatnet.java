package com.mattrubacky.monet2.splatnet;

/**
 * Created by mattr on 9/11/2017.
 */

import com.mattrubacky.monet2.deserialized.*;

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

public interface Splatnet {
    @GET("/")
    Call<ResponseBody> getHomepage(@Header("X-GameWebToken") String token, @Header("X-Unique-Id") String uniqueId);

    @GET("/api/timeline")
    Call<Timeline> getTimeline(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/results")
    Call<ResultList> get50Results(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/results/{battle}")
    Call<Battle> getBattle(@Path("battle") String battle, @Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/records")
    Call<Record> getRecords(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/records/hero")
    Call<CampaignRecords> getHeroData(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("/api/schedules")
    Call<Schedules> getSchedules(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("/api/coop_schedules")
    Call<SalmonSchedule> getSalmonSchedule(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/data/stages")
    Call<ArrayList<Stage>> getStages(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/festivals/active")
    Call<CurrentSplatfest> getActiveSplatfests(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/festivals/pasts")
    Call<PastSplatfest> getPastSplatfests(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/festivals/{id}/votes")
    Call<SplatfestVotes> getSplatfestVotes(@Path("id") String id,@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/festivals/{id}/rankings")
    Call<ResponseBody> getSplatfestRanks(@Path("id") String id,@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("nickname_and_icon?id={id}")
    Call<NicknameRequest> getNicknameIcon(@Path("id") String id,@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/onlineshop/merchandises")
    Call<Annie> getShop(@Header("Cookie") String cookie, @Header("X-Unique-Id") String uniqueId);

    @Headers({
            "Accept: */*",
            "User-Agent: Mozilla/5.0 (Linux; Android 5.1.1; KFDOWI Build/LVY48F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "origin: https://app.splatoon2.nintendo.net",
            "Connection: keep-alive"
    })
    @Multipart
    @POST("api/onlineshop/order/{id}")
    Call<ResponseBody> orderMerch(@Path("id") String id, @Header("X-Unique-Id") String uniqueId, @Part("override") RequestBody override, @Header("Cookie") String cookie);

    @GET("api/share/profile")
    Call<ResponseBody> shareProfile(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/results/summary")
    Call<ResponseBody> shareSummary(@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/results/{battle}")
    Call<ResponseBody> shareBattle(@Path("battle") String battle,@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);

    @GET("api/share/challenges/{challenge}")
    Call<ResponseBody> shareChallenge(@Path("challenge") String challenge,@Header("Cookie") String Cookie, @Header("X-Unique-Id") String uniqueId);



}
