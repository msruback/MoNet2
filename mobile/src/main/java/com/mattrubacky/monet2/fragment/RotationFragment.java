package com.mattrubacky.monet2.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.*;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.helper.*;
import com.mattrubacky.monet2.splatnet.CoopSchedulesRequest;
import com.mattrubacky.monet2.splatnet.SchedulesRequest;
import com.mattrubacky.monet2.splatnet.Splatnet;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/14/2017.
 */

public class RotationFragment extends Fragment implements SplatnetConnected{
    Schedules schedules;
    android.os.Handler customHandler;
    ViewGroup rootView;
    WearLink wearLink;
    SalmonSchedule salmonSchedule;
    Gear monthlyGear;
    CurrentSplatfest currentSplatfest;
    SplatnetConnector connector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        if(settings.contains("rotationState")) {
            schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
            if(schedules == null){
                schedules = new Schedules();
                schedules.regular = new ArrayList<TimePeriod>();
                schedules.ranked = new ArrayList<TimePeriod>();
                schedules.league = new ArrayList<TimePeriod>();
            }
            if(schedules.regular==null){
                schedules.regular = new ArrayList<>();
            }
            if(schedules.ranked==null){
                schedules.ranked = new ArrayList<>();
            }
            if(schedules.league==null){
                schedules.league = new ArrayList<>();
            }
            if(schedules.splatfest==null){
                schedules.splatfest = new ArrayList<>();
            }
        }else{
            schedules = new Schedules();
            schedules.regular = new ArrayList<TimePeriod>();
            schedules.ranked = new ArrayList<TimePeriod>();
            schedules.league = new ArrayList<TimePeriod>();
        }
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);


        wearLink = new WearLink(getContext());

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        wearLink.closeConnection();
        connector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        super.onCreate(new Bundle());
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        wearLink.openConnection();

        customHandler = new android.os.Handler();
        update();

        connector = new SplatnetConnector(this, getActivity(),getContext());
        connector.addRequest(new SchedulesRequest(getContext()));
        connector.addRequest(new CoopSchedulesRequest(getContext()));
        connector.execute();
    }




    //Get Rotation Data

    @Override
    public void update(){
        ListView scheduleList = (ListView) rootView.findViewById(R.id.ScheduleList);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();

        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        wearLink.openConnection();

        if(schedules==null){
            schedules = new Schedules();
        }
        if(salmonSchedule==null){
            salmonSchedule= new SalmonSchedule();
        }
        if(salmonSchedule.details==null){
            salmonSchedule.details = new ArrayList<>();
        }
        if(salmonSchedule.times==null){
            salmonSchedule.times = new ArrayList<>();
        }

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
        if(schedules.splatfest!=null&&currentSplatfest.splatfests.size()>0&&schedules.splatfest.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }

        if(salmonSchedule.details!=null&&salmonSchedule.details.size()>0){
            rotation.add("salmon");
        }


        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),rotation,getChildFragmentManager(),schedules,currentSplatfest,salmonSchedule,monthlyGear);
        scheduleList.setAdapter(scheduleAdapter);

        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }
}