package com.mattrubacky.monet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by mattr on 9/12/2017.
 */

public class CookieManager {
    public CookieManager(){
    }
    public String getCookie(String sessionToken,Context cxt){
        String cookie="";
        try{
            String accessToken;
            //login request param
            String idToken, birthday;

            //param to get Splatoon id
            String accessToken2;


            //param to get Splatoon cookie
            String splatToken;


            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://accounts.nintendo.com").build();
            //NintendoSignIn signIn = retrofit.create(NintendoSignIn.class);
            ResponseBody response;
            JSONObject jsonParse;

            //Get ID token and Access Token 1
            /*String json = "{ \"client_id\":\"71b963c1b7b6d119\", \"grant_type\":\"urn:ietf:params:oauth:grant-type:jwt-bearer-session-token\", \"session_token\":\""+sessionToken+"\" }";
            RequestBody jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
            Call<ResponseBody> serviceTokenGet = signIn.getServiceToken(jsonRequest);
            response = serviceTokenGet.execute().body();
            jsonParse = new JSONObject(response.string());
            idToken = jsonParse.getString("id_token");
            accessToken = jsonParse.getString("access_token");
            System.out.println("ID Token: "+idToken);
            System.out.println("Access Token 1: "+accessToken);

            retrofit = new Retrofit.Builder().baseUrl("https://api.accounts.nintendo.com").build();
            signIn = retrofit.create(NintendoSignIn.class);
            //Get Birthday
            String auth = "Bearer "+accessToken;
            Call<ResponseBody> userDataGet = signIn.getUserDetails(auth);
            response = userDataGet.execute().body();
            jsonParse = new JSONObject(response.string());
            birthday = jsonParse.getString("birthday");
            System.out.println("Birthday: "+birthday);

            //Switch Api to Nintendo Accounts API
            */retrofit = new Retrofit.Builder().baseUrl("https://api-lp1.znc.srv.nintendo.net/").build();
            NintendoAccounts accounts = retrofit.create(NintendoAccounts.class);
            /*
            //Login to Nintendo Accounts API
            (json = "{ \"parameter\": { \"f\": \"a05eae3d62b6d33b48e69ea3fc9f15778a7b8a9c5b7c3d3d5d14539ce83e61f2\",\"language\": \"en-US\", \"naBirthday\": \""+birthday+"\", \"naCountry\": \"US\", \"naIdToken\": \""+idToken+"\" } }";
            jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
            Call<ResponseBody> login = accounts.logIn(jsonRequest);
            response = login.execute().body();
            jsonParse = new JSONObject(response.string());*/
            accessToken2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGktbHAxLnpuYy5zcnYubmludGVuZG8ubmV0IiwiYXVkIjoiZjQxN2UxdGlianFkOTFjaDk5dTQ5aXd6NXNuOWNoeTMiLCJpYXQiOjE1MDY0NDAwMjcsInR5cCI6ImlkX3Rva2VuIiwic3ViIjo1NzUxMjcxNTU0Mjg1NTY4LCJleHAiOjE1MDY0NDcyMjd9.zKYaiw4ow2CLKg0ZqkBDmMO8Y7lZP2l2jCVEF4IdZQY";
            System.out.println("Access Token 2: "+accessToken2);

            //Get Splatoon Token
            String auth = "Bearer "+accessToken2;
            String json = "{ \"parameter\": { \"id\": 5741031244955648 } }";
            RequestBody jsonRequest = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
            Call<ResponseBody> splatTokenGet = accounts.getGameToken(auth,jsonRequest);
            response = splatTokenGet.execute().body();
            jsonParse = new JSONObject(response.string());
            System.out.println(jsonParse.toString());
            splatToken = jsonParse.getJSONObject("result").getString("accessToken");


            retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").build();
            Splatnet splatnet = retrofit.create(Splatnet.class);

            Call<ResponseBody> getCookie = splatnet.getHomepage(splatToken);
            okhttp3.Headers headers = getCookie.execute().headers();
            String preCookie = headers.get("Set-Cookie");
            System.out.println(preCookie);
            String[] cookieDissect = preCookie.split(";");
            String midcookie="";
            for(int i=0;i<cookieDissect.length;i++){
                if(cookieDissect[i].contains("iksm_session")){
                    midcookie = cookieDissect[i].split("=")[1];
                }
            }
            cookie = "iksm_session="+midcookie;
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DATE,1);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(cxt);
            SharedPreferences.Editor edit = settings.edit();
            edit.putLong("cookie_expire",time.getTimeInMillis());
            edit.putString("cookie",cookie);
            edit.commit();
            return cookie;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cookie;
    }
}
