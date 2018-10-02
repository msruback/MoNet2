package com.mattrubacky.monet2.api.nintendo;

import com.mattrubacky.monet2.deserialized.nintendo.ServiceTokenResponse;
import com.mattrubacky.monet2.deserialized.nintendo.SessionToken;
import com.mattrubacky.monet2.deserialized.nintendo.UserDetailsResponse;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mattr on 8/14/2018.
 */

public class NintendoAccounts {
    NintendoAccountsApi accounts;

    public NintendoAccounts(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://accounts.nintendo.com").build();
        accounts = retrofit.create(NintendoAccountsApi.class);
    }

    public ServiceTokenResponse getIdToken(String sessionToken) throws IOException {
        String json = "{ \"client_id\":\"71b963c1b7b6d119\", \"grant_type\":\"urn:ietf:params:oauth:grant-type:jwt-bearer-session-token\", \"session_token\":\""+sessionToken+"\" }";
        RequestBody jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Call<ServiceTokenResponse> serviceTokenGet = accounts.getServiceToken(jsonRequest);
        return serviceTokenGet.execute().body();
    }

    public UserDetailsResponse getUserDetails(String accessToken) throws IOException {
        String auth = "Bearer "+accessToken;
        Call<UserDetailsResponse> userDataGet = accounts.getUserDetails(auth);
        return userDataGet.execute().body();
    }

}
