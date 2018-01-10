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
import com.mattrubacky.monet2.connection.WatchConnected;
import com.mattrubacky.monet2.connection.WatchConnector;
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

public class MapRotation extends Activity implements WatchConnected{

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    WatchConnector watchConnector;
    WatchViewStub stub;
    CurrentSplatfest currentSplatfest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        watchConnector = new WatchConnector(getApplicationContext(),this);

        watchConnector.execute();


        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

                TextView title = (TextView) stub.findViewById(R.id.Title);

                title.setTypeface(fontTitle);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void update(Bundle bundle){
        schedules = bundle.getParcelable("rotation");
        salmonSchedule = bundle.getParcelable("salmon");
        currentSplatfest = bundle.getParcelable("splatfest");
        updateUI();
    }

    private void updateUI(){
        ArrayList<String> rotation = new ArrayList<>();
        if(schedules==null){
            schedules = new Schedules();
        }
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
