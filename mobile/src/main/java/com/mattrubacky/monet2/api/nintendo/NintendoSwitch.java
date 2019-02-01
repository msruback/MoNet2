package com.mattrubacky.monet2.api.nintendo;

import com.mattrubacky.monet2.deserialized.nintendo.LoginResult;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mattr on 8/14/2018.
 */

public class NintendoSwitch {
    private NintendoSwitchApi switchApi;

    public NintendoSwitch(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api-lp1.znc.srv.nintendo.net/").build();
        switchApi = retrofit.create(NintendoSwitchApi.class);
    }

    public LoginResult getAccessToken(String birthday,String idToken) throws IOException {
        String json = "{ \"parameter\": { \"f\": \"a05eae3d62b6d33b48e69ea3fc9f15778a7b8a9c5b7c3d3d5d14539ce83e61f2\",\"language\": \"en-US\", \"naBirthday\": \""+birthday+"\", \"naCountry\": \"US\", \"naIdToken\": \""+idToken+"\" } }";
        RequestBody jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Call<LoginResult> login = switchApi.logIn(jsonRequest);
        return login.execute().body();
    }
}
