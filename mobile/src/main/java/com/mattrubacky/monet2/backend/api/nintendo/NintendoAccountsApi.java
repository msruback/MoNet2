package com.mattrubacky.monet2.backend.api.nintendo;

import com.mattrubacky.monet2.data.deserialized.nintendo.ServiceTokenResponse;
import com.mattrubacky.monet2.data.deserialized.nintendo.UserDetailsResponse;

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
 * Created by mattr on 9/9/2017.
 */

public interface NintendoAccountsApi {
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "X-Platform: Android",
            "X-ProductVersion: 1.1.0",
            "User-Agent: com.nintendo.znca/1.1.0 (Android/5.1.1)"
    })
    @FormUrlEncoded
    @POST("connect/1.0.0/api/session_token")
    Call<ResponseBody> getSession(@Field("client_id") String id, @Field("session_token_code") String token_code, @Field("session_token_code_verifier") String verifier);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.1.0",
            "User-Agent: com.nintendo.znca/1.1.0 (Android/5.1.1)"
    })
    @POST("connect/1.0.0/api/token")
    Call<ServiceTokenResponse> getServiceToken(@Body RequestBody params);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.1.0",
            "User-Agent: com.nintendo.znca/1.1.0 (Android/5.1.1)"
    })
    @GET("2.0.0/users/me")
    Call<UserDetailsResponse> getUserDetails(@Header("Authorization") String authorization);
}