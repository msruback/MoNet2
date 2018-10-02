package com.mattrubacky.monet2.api.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mattrubacky.monet2.api.nintendo.GameLogin;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mattr on 8/14/2018.
 */

public class SplatnetLogin extends GameLogin {
    private Context context;

    public SplatnetLogin(Context context){
        super();
        this.context = context;
    }

    @Override
    public String getGameId() {
        return "5741031244955648";
    }

    @Override
    public String login(String accessToken2) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").build();
        Splatnet splatnet = retrofit.create(Splatnet.class);

        Call<ResponseBody> getCookie = splatnet.getHomepage(getGameToken(accessToken2));
        okhttp3.Headers headers = getCookie.execute().headers();
        String preCookie = headers.get("Set-Cookie");
        System.out.println(preCookie);
        String[] cookieDissect = preCookie.split(";");
        String cookie = "";
        for(int i=0;i<cookieDissect.length;i++){
            if(cookieDissect[i].contains("iksm_session")){
                cookie = cookieDissect[i];
            }
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("cookie",cookie);
        edit.commit();
        return cookie;
    }
}
