package com.mattrubacky.monet2.splatnet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.dialog.AlertDialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 12/6/2017.
 */

public class SplatnetConnector extends AsyncTask<Void,Void,Void> {
    private SplatnetConnected caller;
    private Activity activity;
    private ArrayList<SplatnetRequest> requests;
    private ImageView loading;
    private boolean isUnconn,isUnauth,isMain,hasUI;
    private Context context;
    private Bundle result;

    public SplatnetConnector(SplatnetConnected caller, Activity activity,Context context){
        this.caller = caller;
        this.activity = activity;
        this.context = context;

        result = new Bundle();
        requests = new ArrayList<>();
        hasUI = true;
    }

    //Constructor to not use UI changes
    public SplatnetConnector(SplatnetConnected caller, Context context){
        this.caller = caller;
        this.context = context;

        result = new Bundle();
        requests = new ArrayList<>();
        hasUI = false;
    }

    //Method to add a request to the list
    public void addRequest(SplatnetRequest request){
        requests.add(request);
    }

    //Method to get data requested that is currently stored
    public Bundle getCurrentData(){
        Bundle bundle = new Bundle();
        for(int i=0;i<requests.size();i++){
            SplatnetRequest request = requests.get(i);
            request.result(bundle);
            bundle = request.result(bundle);
        }
        return bundle;
    }

    @Override
    protected void onPreExecute() {
        if(hasUI) {
            loading = (ImageView) activity.findViewById(R.id.loading_indicator);

            RotateAnimation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1000);

            loading.startAnimation(animation);
            loading.setVisibility(View.VISIBLE);
        }
        isUnconn = false;
        isUnauth = false;
        isMain = false;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Splatnet splatnet = retrofit.create(Splatnet.class);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String cookie = settings.getString("cookie","");
            String uniqueId = settings.getString("unique_id","");
            for(int i=0;i<requests.size();i++){
                SplatnetRequest request = requests.get(i);
                request.setup(splatnet,cookie,uniqueId);
                request.run();
                result = request.result(result);
            }
        }catch(SplatnetUnauthorizedException e){
            e.printStackTrace();
            isUnauth = true;
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            isUnconn = true;
            return null;
        } catch (SplatnetMaintenanceException e) {
            e.printStackTrace();
            isMain = true;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(hasUI) {
            if (isUnconn) {
                AlertDialog alertDialog = new AlertDialog(activity, context.getResources().getString(R.string.dataError));
                alertDialog.show();
            } else if (isUnauth) {
                AlertDialog alertDialog = new AlertDialog(activity, context.getResources().getString(R.string.badCookie));
                alertDialog.show();
            } else if (isMain) {
                AlertDialog alertDialog = new AlertDialog(activity, context.getResources().getString(R.string.splatnetMaintenance));
                alertDialog.show();
            } else {
                caller.update(this.result);
            }
            loading.setAnimation(null);
            loading.setVisibility(View.GONE);
        }else{
            if(!isUnconn&&!isUnauth){
                caller.update(this.result);
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(hasUI) {
            ImageView loading = (ImageView) activity.findViewById(R.id.loading_indicator);
            loading.setVisibility(View.GONE);
            loading.setAnimation(null);
        }
    }
}