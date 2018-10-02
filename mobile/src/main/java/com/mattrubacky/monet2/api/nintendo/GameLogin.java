package com.mattrubacky.monet2.api.nintendo;

import com.mattrubacky.monet2.deserialized.nintendo.GameTokenResponse;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mattr on 8/14/2018.
 */

public abstract class GameLogin {
    private NintendoSwitchApi accounts;

    public GameLogin(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api-lp1.znc.srv.nintendo.net/").build();
        accounts = retrofit.create(NintendoSwitchApi.class);
    }

    protected String getGameToken(String accessToken2) throws IOException {
        String auth = "Bearer "+accessToken2;
        String json = "{ \"parameter\": { \"id\": "+getGameId()+" } }";
        RequestBody jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        Call<GameTokenResponse> gameTokenGet = accounts.getGameToken(auth,jsonRequest);
        GameTokenResponse response = gameTokenGet.execute().body();
        return response.result.gameToken;
    }

    public abstract String getGameId();
    public abstract String login(String accessToken2) throws IOException;
}
