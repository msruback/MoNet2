package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Calendar;

import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Login extends AppCompatActivity {
    private String sessionTokenCode,codeVerifier,codeChallenge,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.title);

        int flags = Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP;
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36];
        random.nextBytes(bytes);
        state = Base64.encodeToString(bytes,flags);
        bytes = new byte[32];
        random.nextBytes(bytes);
        /*codeVerifier = Base64.encodeToString(bytes,flags);
        codeChallenge = "";
        try {
            codeChallenge = Base64.encodeToString(computeHash(codeVerifier).getBytes(),flags);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        state="bEVJiwcJvRXSgswXXJfBWAXuVGPYUdIURawRoiAfpPuyvruFln";
        codeChallenge = "yJBOlX8APS9gACKA7iMs17HfkE4z8qzZn1sW00WAt0k";
        String url = "https://accounts.nintendo.com/connect/1.0.0/authorize?state="+state+"&redirect_uri=npf71b963c1b7b6d119://auth&client_id=71b963c1b7b6d119&scope=openid%20user%20user.birthday%20user.mii%20user.screenName&response_type=session_token_code&session_token_code_challenge="+codeChallenge+"&session_token_code_challenge_method=S256&theme=login_form";
        final WebView wv = (WebView) findViewById(R.id.Web);

        wv.getSettings().setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, WebResourceRequest webRequest){
                String url2 = webRequest.getUrl().toString();
                if(url2.contains("npf71b963c1b7b6d119://auth#session_state")) {
                    //session token request params
                    System.out.println(url2);
                    sessionTokenCode = "";
                    String[] url2DissectPre = url2.split("#");
                    String[] url2Dissect = url2DissectPre[1].split("&");
                    for (int i = 0; i < url2Dissect.length - 1; i++) {
                        if (url2Dissect[i].contains("session_token_code")) {
                            sessionTokenCode = url2Dissect[i].split("=")[1];
                        }
                    }
                    getData();
                    return true;
                }
                return false;
            }
        };
        wv.setWebViewClient(client);
        wv.loadUrl(url);

    }
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString( int len ) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    public String computeHash(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();

        byte[] byteData = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();

    }
    public void getData(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String sessionToken;

                String accessToken;
                //login request param
                String idToken, birthday;

                //param to get Splatoon id
                String accessToken2;

                //param to get Splatoon token
                String splatID = "5741031244955648";

                //param to get Splatoon cookie
                String splatToken;

                //Splatoon Cookie
                String splatCookie = "";

               try {
                   Retrofit retrofit = new Retrofit.Builder().baseUrl("https://accounts.nintendo.com").build();
                   NintendoSignIn signIn = retrofit.create(NintendoSignIn.class);

                   //Get Session Token
                   Call<ResponseBody> sessionTokenGet = signIn.getSession("71b963c1b7b6d119",sessionTokenCode,state);
                   ResponseBody response = sessionTokenGet.execute().body();
                   JSONObject jsonParse = new JSONObject(response.string());
                   sessionToken = jsonParse.getString("session_token");
                   System.out.println("Session Token: "+sessionToken);
                   Calendar expire = Calendar.getInstance();
                   expire.add(Calendar.YEAR,2);

                   SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                   SharedPreferences.Editor edit = settings.edit();
                   edit.putString("token",sessionToken);
                   edit.putLong("token_expire",expire.getTimeInMillis());

                   edit.commit();
                   CookieManager cookieManager = new CookieManager();
                   cookieManager.getCookie(sessionToken,getApplicationContext());

                   Intent intent = new Intent(getBaseContext(), Rotation.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread t = new Thread(r);
        t.start();
    }





}
