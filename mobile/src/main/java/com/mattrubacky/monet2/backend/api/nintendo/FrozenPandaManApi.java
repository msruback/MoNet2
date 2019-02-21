package com.mattrubacky.monet2.backend.api.nintendo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by mattr on 8/14/2018.
 */

public interface FrozenPandaManApi {
    @Headers({
            "Content-Type: application/x-www-formurlencoded",
            "User-Agent: monet/0.9.2"
    })
    @POST("https://elifessler.com/s2s/api/gen")
    Call<ResponseBody> getF();
}
