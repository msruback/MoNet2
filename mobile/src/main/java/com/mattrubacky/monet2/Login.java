package com.mattrubacky.monet2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    private String clientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.title);

        //String url = getIntent().getStringExtra("URL");
        String url = "https://accounts.nintendo.com/connect/1.0.0/authorize?state=bnvdUgZQoNnPNIFnOVGdKFaShscBeLPsQMCQrrqoAlpfjUHrNW&redirect_uri=npf71b963c1b7b6d119://auth&client_id=71b963c1b7b6d119&scope=openid%20user%20user.birthday%20user.mii%20user.screenName&response_type=session_token_code&session_token_code_challenge=l9ps_8WbrubM4Dc8UZD801Qpv8MKiNbgO3BFxOBCa-0&session_token_code_challenge_method=S256&theme=login_form";
        String[] url1Disect= url.split("&");

        //get client id for session token request
        for(int i=0;i<url1Disect.length-1;i++){
            if(url1Disect[i].contains("client_id")){
                clientID = url1Disect[i].split("client_id=")[1];
            }
        }
        final WebView wv = (WebView) findViewById(R.id.Web);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.squid);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(url);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String url2 = wv.getUrl();

                //session token request params
                String sessionTokenCode, state;

                //service access token request param
                String sessionToken;

                //login request param
                String idToken;

                //param to get Splatoon id
                String accessToken;

                //param to get Splatoon token
                String splatID;

                //param to get Splatoon cookie
                String splatToken;

                //Splatoon Cookie
                String splatCookie;

                //Get sessionTokenCode and state out of url2
                String[] url2Dissect=url2.split("&");
                for(int i=0;i<url2Dissect.length-1;i++){
                    if(url2Dissect[i].contains("session_token_code")){
                        sessionTokenCode = url2Dissect[i].split("session_token_code=")[1];
                    }else if(url2Dissect[i].contains("state")){
                        state = url2Dissect[i].split("state=")[1];
                    }
                }



                Intent intent = new Intent(getBaseContext(), Rotation.class);
                startActivity(intent);
            }
        });

    }
}
