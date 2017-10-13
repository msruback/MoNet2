package com.mattrubacky.monet2;

/**
 * Created by mattr on 9/11/2017.
 */
import android.preference.MultiSelectListPreference;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Splatnet {
    @GET("/")
    Call<ResponseBody> getHomepage(@Header("X-GameWebToken") String token);

    @GET("/api/timeline")
    Call<Timeline> getTimeline(@Header("Cookie") String Cookie);

    @GET("api/results")
    Call<ResultList> get50Results(@Header("Cookie") String cookie);

    @GET("api/results/{battle}")
    Call<Battle> getBattle(@Path("battle") String battle,@Header("Cookie") String Cookie);

    @GET("api/records")
    Call<Record> getRecords(@Header("Cookie") String cookie);

    @GET("api/records/hero")
    Call<ResponseBody> getHeroData(@Header("Cookie") String cookie);

    @GET("/api/schedules")
    Call<Schedules> getSchedules(@Header("Cookie") String cookie);

    @GET("api/data/stages")
    Call<ResponseBody> getStages(@Header("Cookie") String cookie);

    @GET("api/festivals/active")
    Call<CurrentSplatfest> getActiveSplatfests(@Header("Cookie") String cookie);

    @GET("api/festivals/pasts")
    Call<PastSplatfest> getPastSplatfests(@Header("Cookie") String cookie);

    @GET("api/festivals/{id}/votes")
    Call<ResponseBody> getSplatfestVotes(@Header("Cookie") String cookie);

    @GET("api/festivals/{id}/rankings")
    Call<ResponseBody> getSplatfestRanks(@Header("Cookie") String cookie);

    @GET("nickname_and_icon{id}")
    Call<ResponseBody> getNicknameIcon(@Path("id") String id,@Header("Cookie") String cookie);

    @GET("api/onlineshop/merchandises")
    Call<Annie> getShop(@Header("Cookie") String cookie);

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
    Call<ResponseBody> shareProfile(@Header("Cookie") String Cookie);

    @GET("api/share/results/summary")
    Call<ResponseBody> shareSummary(@Header("Cookie") String Cookie);

    @GET("api/share/results/{battle}")
    Call<ResponseBody> shareBattle(@Path("battle") String battle,@Header("Cookie") String Cookie);

    @GET("api/share/challenges/{challenge}")
    Call<ResponseBody> shareChallenge(@Path("challenge") String challenge,@Header("Cookie") String Cookie);



}
