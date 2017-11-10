package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.*;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.fragment.schedule.*;
import com.mattrubacky.monet2.helper.*;
import com.mattrubacky.monet2.reciever.*;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
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

public class RotationFragment extends Fragment {
    Schedules schedules;
    android.os.Handler customHandler;
    ViewGroup rootView;
    WearLink wearLink;
    UpdateRotationData updateRotationData;
    SalmonSchedule salmonSchedule;
    Gear monthlyGear;
    CurrentSplatfest currentSplatfest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);

        updateRotationData = new UpdateRotationData();
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
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedule\":[]}"),SalmonSchedule.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);


        wearLink = new WearLink(getContext());

        customHandler = new android.os.Handler();
        updateUi();
        new UpdateRotationData().execute();
        /*
        if(salmonSchedule.schedule!=null&&salmonSchedule.schedule.size()!=0){
            if(salmonSchedule.schedule.get(0).endTime< new Date().getTime()){
                salmonSchedule.schedule.remove(0);
                SharedPreferences.Editor edit = settings.edit();
                String json = gson.toJson(salmonSchedule);
                edit.putString("salmonRunSchedule",json);
                edit.commit();
                SalmonAlarm salmonAlarm = new SalmonAlarm();
                salmonAlarm.cancelAlarm(getContext());
                salmonAlarm.setAlarm(getContext());
            }
        }

        if(schedules.regular.size()==0){
            customHandler.post(update2Hours);
        }else {
            if ((schedules.regular.get(0).end * 1000) < new Date().getTime()) {
                do{
                    schedules.dequeue();
                }while(schedules.regular.size()>0&&(schedules.regular.get(0).end * 1000)< new Date().getTime());

                updateUi();
                customHandler.post(update2Hours);
            }else{
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                Calendar nextUpdate = Calendar.getInstance();
                nextUpdate.setTimeInMillis(now.getTimeInMillis());
                int hour = now.get(Calendar.HOUR);
                if(now.get(Calendar.HOUR)%2==0){
                    hour+=2;
                }else{
                    hour+=1;
                }
                nextUpdate.set(Calendar.HOUR,hour);
                nextUpdate.set(Calendar.MINUTE,0);
                nextUpdate.set(Calendar.SECOND,0);
                nextUpdate.set(Calendar.MILLISECOND,0);
                Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
                customHandler.postDelayed(update2Hours, nextUpdateTime);
            }
        }*/

        return rootView;
    }



    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
        wearLink.closeConnection();
        updateRotationData.cancel(true);
        customHandler.removeCallbacks(update2Hours);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        wearLink.openConnection();
    }


    //Get Rotation Data

    private void updateUi(){
        ListView scheduleList = (ListView) rootView.findViewById(R.id.ScheduleList);

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
        if(schedules.splatfest!=null&&currentSplatfest.splatfests.size()>0){
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

    private class UpdateRotationData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String cookie;

                //Create Splatnet manager
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                cookie = settings.getString("cookie","");
                Call<Schedules> rotationGet = splatnet.getSchedules(cookie);
                Response response = rotationGet.execute();
                if(response.isSuccessful()){
                    schedules = (Schedules) response.body();
                    SplatnetSQLManager database = new SplatnetSQLManager(getContext());
                    ArrayList<Stage> stages = new ArrayList<>();
                    for(int i=0;i<schedules.regular.size();i++){
                        stages.add(schedules.regular.get(i).a);
                        stages.add(schedules.regular.get(i).b);

                        stages.add(schedules.ranked.get(i).a);
                        stages.add(schedules.ranked.get(i).b);

                        stages.add(schedules.league.get(i).a);
                        stages.add(schedules.league.get(i).b);
                    }
                    database.insertStages(stages);
                    Call<CurrentSplatfest> getSplatfest = splatnet.getActiveSplatfests(cookie);
                    response = getSplatfest.execute();
                    if(response.isSuccessful()){
                        currentSplatfest = (CurrentSplatfest) response.body();
                        if(currentSplatfest.splatfests.size()>0){
                            schedules.setSplatfest(currentSplatfest.splatfests.get(0));
                            database.insertSplatfests(currentSplatfest.splatfests);
                        }
                    }else{

                    }
                }else{

                }
                Call<SalmonSchedule> salmonGet = splatnet.getSalmonSchedule(cookie);
                response = salmonGet.execute();
                if(response.isSuccessful()){
                    salmonSchedule = (SalmonSchedule) response.body();
                }else{

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
        }

    }

    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            updateRotationData = new UpdateRotationData();
            updateRotationData.execute();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdate = Calendar.getInstance();
            nextUpdate.setTimeInMillis(now.getTimeInMillis());
            int hour = now.get(Calendar.HOUR);
            if(now.get(Calendar.HOUR)%2==0){
                hour+=2;
            }else{
                hour+=1;
            }
            int zero = 0;
            nextUpdate.set(Calendar.HOUR,hour);
            nextUpdate.set(Calendar.MINUTE,0);
            nextUpdate.set(Calendar.SECOND,0);
            nextUpdate.set(Calendar.MILLISECOND,0);
            Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };
}