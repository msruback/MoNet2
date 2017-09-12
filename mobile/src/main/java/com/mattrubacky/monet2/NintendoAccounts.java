package com.mattrubacky.monet2;

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

/**
 * Created by mattr on 9/11/2017.
 */

public interface NintendoAccounts {
    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.0.4",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)",
            "Authorization: Bearer"
    })
    @POST("v1/Account/Login")
    Call<ResponseBody> logIn(@Body RequestBody params);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.0.4",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)"
    })
    @POST("v1/Game/GetWebServiceToken")
    Call<ResponseBody> getGameToken(@Header("Authorization") String auth,@Body RequestBody params);
}
