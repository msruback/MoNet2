package com.mattrubacky.monet2;

import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

public interface NintendoSignIn{
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "X-Platform: Android",
            "X-ProductVersion: 1.0.4",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)"
    })
    @FormUrlEncoded
    @POST("connect/1.0.0/api/session_token")
    Call<ResponseBody> getSession(@Field("client_id") String id, @Field("session_token_code") String token_code, @Field("session_token_code_verifier") String verifier);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.0.4",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)"
    })
    @POST("connect/1.0.0/api/token")
    Call<ResponseBody> getServiceToken(@Body RequestBody params);

    @Headers({
            "X-Platform: Android",
            "X-ProductVersion: 1.0.4",
            "User-Agent: com.nintendo.znca/1.0.4 (Android/4.4.2)"
    })
    @GET("2.0.0/users/me")
    Call<ResponseBody> getUserDetails(@Header("Authorization") String authorization);
}
