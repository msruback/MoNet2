package com.mattrubacky.monet2;

/**
 * Created by mattr on 9/11/2017.
 */
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Splatnet {
    @GET("/")
    Call<ResponseBody> getHomepage(@Header("X-GameWebToken") String token);

    @GET("api/results")
    Call<ResponseBody> get50Results(@Header("Cookie") String cookie);

    @GET("api/results/{battle}")
    Call<ResponseBody> getBattle(@Path("battle") String battle,@Header("Cookie") String Cookie);

    @GET("api/records")
    Call<ResponseBody> getRecords(@Header("Cookie") String cookie);

    @GET("api/records/hero")
    Call<ResponseBody> getHeroData(@Header("Cookie") String cookie);

    @GET("/api/schedules")
    Call<ResponseBody> getSchedules(@Header("Cookie") String cookie);

    @GET("api/data/stages")
    Call<ResponseBody> getStages(@Header("Cookie") String cookie);

    @GET("api/festivals/active")
    Call<ResponseBody> getActiveSplatfests(@Header("Cookie") String cookie);

    @GET("api/festivals/pasts")
    Call<ResponseBody> getPastSplatfests(@Header("Cookie") String cookie);

    @GET("api/festivals/{id}/votes")
    Call<ResponseBody> getSplatfestVotes(@Header("Cookie") String cookie);

    @GET("api/festivals/{id}/rankings")
    Call<ResponseBody> getSplatfestRanks(@Header("Cookie") String cookie);

    @GET("nickname_and_icon{id}")
    Call<ResponseBody> getNicknameIcon(@Path("id") String id,@Header("Cookie") String cookie);

    @GET("api/onlineshop/merchandises")
    Call<ResponseBody> getShop(@Header("Cookie") String cookie);

    @Headers({
            "Accept: */*",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)",
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("api/onlineshop/order/{id}")
    Call<ResponseBody> orderMerch(@Path("id") String id,@Header("X-Unique-Id") String uniqueId);

    @GET("api/share/profile")
    Call<ResponseBody> shareProfile(@Header("Cookie") String Cookie);

    @GET("api/share/results/summary")
    Call<ResponseBody> shareSummary(@Header("Cookie") String Cookie);

    @GET("api/share/results/{battle}")
    Call<ResponseBody> shareBattle(@Path("battle") String battle,@Header("Cookie") String Cookie);

    @GET("api/share/challenges/{challenge}")
    Call<ResponseBody> shareChallenge(@Path("challenge") String challenge,@Header("Cookie") String Cookie);



}
