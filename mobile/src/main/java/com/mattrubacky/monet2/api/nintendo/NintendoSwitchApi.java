package com.mattrubacky.monet2.api.nintendo;

import com.mattrubacky.monet2.deserialized.nintendo.GameTokenResponse;
import com.mattrubacky.monet2.deserialized.nintendo.LoginResult;

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

public interface NintendoSwitchApi {
    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.1.0",
            "User-Agent: com.nintendo.znca/1.1.0 (Android/5.1.1)",
            "Authorization: Bearer"
    })
    @POST("v1/Account/Login")
    Call<LoginResult> logIn(@Body RequestBody params);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.1.0",
            "User-Agent: com.nintendo.znca/1.1.0 (Android/5.1.1)"
    })
    @POST("v1/Game/GetWebServiceToken")
    Call<GameTokenResponse> getGameToken(@Header("Authorization") String auth, @Body RequestBody params);
}