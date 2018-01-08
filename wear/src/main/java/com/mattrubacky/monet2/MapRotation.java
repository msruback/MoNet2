package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.mattrubacky.monet2.adapter.ScheduleAdapter;
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapRotation extends Activity implements DataApi.DataListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    private GoogleApiClient googleApiClient;
    WatchViewStub stub;
    CurrentSplatfest currentSplatfest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();


        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Gson gson = new Gson();
                if(settings.contains("rotationState")) {
                    schedules = gson.fromJson(settings.getString("rotationState","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);
                    if(schedules == null){
                        schedules = new Schedules();
                        schedules.regular = new ArrayList<TimePeriod>();
                        schedules.ranked = new ArrayList<TimePeriod>();
                        schedules.league = new ArrayList<TimePeriod>();
                    }
                }else{
                    schedules = new Schedules();
                    schedules.regular = new ArrayList<TimePeriod>();
                    schedules.ranked = new ArrayList<TimePeriod>();
                    schedules.league = new ArrayList<TimePeriod>();
                }
                salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedules\":[],\"details\":[]}"),SalmonSchedule.class);

                Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");


                TextView title = (TextView) stub.findViewById(R.id.Title);

                title.setTypeface(fontTitle);

                updateUI();
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
        googleApiClient.disconnect();
        Wearable.DataApi.removeListener(googleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedules\":[],\"details\":[]}"),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/schedules") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    saveSchedules(dataMap.getString("schedule"),dataMap.getString("salmonRunSchedule"),dataMap.getString("currentSplatfest"));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }

    }
    private void saveSchedules(String schedules,String salmon,String splatfest){
        Gson gson = new Gson();
        this.schedules = gson.fromJson(schedules,Schedules.class);
        this.salmonSchedule = gson.fromJson(salmon,SalmonSchedule.class);
        this.currentSplatfest = gson.fromJson(splatfest,CurrentSplatfest.class);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = settings.edit();

        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();

        if(schedules!=null){
            updateUI();
        }
    }


    private void updateUI(){
        ArrayList<String> rotation = new ArrayList<>();
        if(schedules.regular!=null&&schedules.regular.size()>0){
            rotation.add("regular");
        }
        if(schedules.ranked!=null&&schedules.ranked.size()>0){
            rotation.add("ranked");
        }
        if(schedules.league!=null&&schedules.league.size()>0){
            rotation.add("league");
        }
        if(currentSplatfest!=null&&currentSplatfest.splatfests!=null&&currentSplatfest.splatfests.size()>0&&schedules.splatfest!=null&&schedules.splatfest.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }
        if(salmonSchedule!=null&&salmonSchedule.details!=null&&salmonSchedule.details.size()>0){
            rotation.add("salmon");
        }
        ListView rotationList = (ListView) stub.findViewById(R.id.ScheduleList);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getApplicationContext(),rotation,schedules,salmonSchedule,currentSplatfest);
        rotationList.setAdapter(scheduleAdapter);
    }
}
