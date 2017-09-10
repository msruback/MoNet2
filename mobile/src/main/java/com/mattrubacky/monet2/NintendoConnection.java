package com.mattrubacky.monet2;

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

/**
 * Created by mattr on 9/9/2017.
 */

public class NintendoConnection {

    //session token request params
    private String clientID,sessionTokenCode, state;

    //service access token request param
    private String sessionToken;

    private String accessToken;
    //login request param
    private String idToken,birthday;

    //param to get Splatoon id
    private String accessToken2;

    //param to get Splatoon token
    private String splatID;

    //param to get Splatoon cookie
    private String splatToken;

    //Splatoon Cookie
    private String splatCookie;

    public NintendoConnection(String clientID,String sessionTokenCode,String state){
        this.clientID = clientID;
        this.sessionTokenCode = sessionTokenCode;
        this.state = state;
    }

    public void requestSessionToken(){
        try {
            URL ninUrl = new URL("https://accounts.nintendo.com/connect/1.0.0/api/session_token");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "client_id="+clientID+"&session_token_code="+sessionTokenCode+"&session_token_code_verifier="+state;

            request.setDoOutput(true);
            request.setDoInput(true);

            request.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            request.setRequestProperty("Content-Length",Integer.toString(post.length()));

            request.setRequestMethod("POST");
            request.connect();

            //Session Token Request
            OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream());
            writer.write(post);
            writer.flush();
            writer.close();

            //Session Token Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            sessionToken = jsonParse.getString("session_token");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestServiceToken(){
        try {
            URL ninUrl = new URL("https://accounts.nintendo.com/connect/1.0.0/api/token");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "{ \"client_id\":\""+clientID+"\", \"grant_type\":\"urn:ietf:params:oauth:grant-type:jwt-bearer-session-token\",\"session_token\":\""+sessionToken+"\" }";

            request.setDoOutput(true);
            request.setDoInput(true);

            //Set Header
            request.setRequestProperty("Host"," accounts.nintendo.com");
            request.setRequestProperty("Content-Type","application/json; charset=utf-8");
            request.setRequestProperty("Connection","keep-alive");
            request.setRequestProperty("User-Agent","OnlineLoune/1.0.4 NASDKAPI iOS");
            request.setRequestProperty("Accept","application/json");
            request.setRequestProperty("Accept-Language","en-US");
            request.setRequestProperty("Accept-Encoding","gzip,deflate");
            request.setRequestProperty("Content-Length",Integer.toString(post.length()));

            request.setRequestMethod("POST");

            request.connect();

            //Session Token Request
            OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream());
            writer.write(post);
            writer.flush();
            writer.close();

            //Session Token Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            idToken = jsonParse.getString("id_token");
            accessToken = jsonParse.getString("access_token");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getBirthday(){
        try {
            URL ninUrl = new URL("https://accounts.nintendo.com/2.0.0/users/me");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "";

            request.setDoInput(true);

            //Set Header
            request.setRequestProperty("Host","api.accounts.nintendo.com");request.setRequestProperty("Connection","keep-alive");
            request.setRequestProperty("User-Agent","OnlineLoune/1.0.4 NASDKAPI iOS");
            request.setRequestProperty("Accept","application/json");
            request.setRequestProperty("Accept-Language","en-US");
            request.setRequestProperty("Accept-Encoding","gzip,deflate");
            request.setRequestProperty("Authorization",accessToken);

            request.setRequestMethod("Get");

            request.connect();

            //Session Token Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            birthday = jsonParse.getString("birthday");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void login(){
        try {
            URL ninUrl = new URL("https://api-lp1.znv.srv.nintendo.net/v1/Account/GetToken");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "{ \"parameter\": { \"language\": \"en-US\", \"naBirthday\": \""+birthday+"\", \"naCountry\": \"US\", \"naIdToken\": \""+idToken+"\" } }";

            request.setDoOutput(true);
            request.setDoInput(true);

            //Set Header
            request.setRequestProperty("Content-Type","application/json; charset=utf-8");
            request.setRequestProperty("Connection","keep-alive");
            request.setRequestProperty("User-Agent","OnlineLoune/1.0.4 NASDKAPI iOS");
            request.setRequestProperty("Accept","application/json");
            request.setRequestProperty("Accept-Language","en-US");
            request.setRequestProperty("Accept-Encoding","gzip,deflate");
            request.setRequestProperty("Content-Length",Integer.toString(post.length()));

            request.setRequestMethod("POST");

            request.connect();

            //Session Token Request
            OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream());
            writer.write(post);
            writer.flush();
            writer.close();

            //Session Token Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            accessToken2 = jsonParse.getJSONObject("webApiServerCredential").getString("accessToken");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void requestGameList(){
        try {
            URL ninUrl = new URL("https://api-lp1.znv.srv.nintendo.net/v1/Game/ListWebServices");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());

            request.setDoInput(true);

            //Set Header

            request.setRequestProperty("Host","api-lp1.znc.srv.nintendo.net");
            request.setRequestProperty("Content-Type","application/json; charset=utf-8");
            request.setRequestProperty("Connection","keep-alive");
            request.setRequestProperty("X-ProductVersion","1.0.4");
            request.setRequestProperty("User-Agent","com.nintendo.znca/1.0.4 (iOS/10.3.3)");
            request.setRequestProperty("Accept-Language","en-US");
            request.setRequestProperty("X-Platform","iOS");
            request.setRequestProperty("Authorization",accessToken2);
            request.setRequestProperty("Content-Length","0");

            request.setRequestMethod("POST");

            request.connect();

            //Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            splatID = jsonParse.getJSONArray("result").getJSONObject(0).getString("id");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void requestSplatoonToken(){
        try {
            URL ninUrl = new URL("https://api-lp1.znv.srv.nintendo.net/v1/Game/ListWebServices");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "{ \"parameter\": { \"id\": "+splatID+" } }";

            request.setDoOutput(true);
            request.setDoInput(true);

            //Set Header

            request.setRequestProperty("Host","api-lp1.znc.srv.nintendo.net");
            request.setRequestProperty("Content-Type","application/json; charset=utf-8");
            request.setRequestProperty("Connection","keep-alive");
            request.setRequestProperty("X-ProductVersion","1.0.4");
            request.setRequestProperty("User-Agent","com.nintendo.znca/1.0.4 (iOS/10.3.3)");
            request.setRequestProperty("Accept-Language","en-US");
            request.setRequestProperty("X-Platform","iOS");
            request.setRequestProperty("Accept-Encoding","gzip,deflate");
            request.setRequestProperty("Accept","application/json; charset=utf-8");
            request.setRequestProperty("Authorization",accessToken2);
            request.setRequestProperty("Content-Length",Integer.toString(post.length()));

            request.setRequestMethod("POST");

            request.connect();

            //Session Token Request
            OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream());
            writer.write(post);
            writer.flush();
            writer.close();

            //Response
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(request.getInputStream())));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();

            JSONObject jsonParse = new JSONObject(builder.toString());

            splatToken = jsonParse.getJSONObject("result").getString("accessToken");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void requestSplatoonCookie(){
        try {
            URL ninUrl = new URL("https://app.splatoon2.nintendo.net/");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());
            String post = "{ \"parameter\": { \"id\": "+splatID+" } }";

            request.setDoInput(true);

            //Set Header

            request.setRequestProperty("Host","https://app.splatoon2.nintendo.net");
            request.setRequestProperty("X-GameWebToken",splatToken);
            request.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_3 like Mac OS X) AppleWebKit/603.3.8 (KHTML, like Gecko) Mobile/14G60");

            request.setRequestMethod("GET");

            request.connect();

            splatCookie = request.getHeaderField("Set-Cookie");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getSplatoonCookie(){
        return splatCookie;
    }
}
