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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.adapter.*;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.dialog.AlertDialog;
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
    int nextUpdate;
    long lastUpdate;

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
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);


        wearLink = new WearLink(getContext());

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
        edit.putInt("nextUpdate",nextUpdate);
        edit.putLong("lastUpdate",lastUpdate);
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
        super.onCreate(new Bundle());
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        wearLink.openConnection();

        customHandler = new android.os.Handler();
        updateUi();
        int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        updateRotationData = new UpdateRotationData();
        updateRotationData.execute();
        if(curHour>settings.getInt("nextUpdate",-1)){
            customHandler.post(update2Hours);
        }else{

            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdateCal = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR);
            if(schedules.regular!=null&&schedules.splatfest!=null&&schedules.regular.size()>0&&schedules.splatfest.size()>0){
                if(schedules.regular.get(0).end<schedules.splatfest.get(0).end){
                    nextUpdateCal.setTimeInMillis(schedules.regular.get(0).end*1000);
                    lastUpdate = schedules.regular.get(0).start;
                }else{
                    nextUpdateCal.setTimeInMillis(schedules.splatfest.get(0).end*1000);
                    lastUpdate = schedules.splatfest.get(0).start;
                }
            }else if(schedules.regular!=null&&schedules.regular.size()>0){
                nextUpdateCal.setTimeInMillis(schedules.regular.get(0).end*1000);
                lastUpdate = schedules.regular.get(0).start;
            }else if (schedules.splatfest!=null&&schedules.splatfest.size()>0){
                nextUpdateCal.setTimeInMillis(schedules.splatfest.get(0).end*1000);
                lastUpdate = schedules.splatfest.get(0).start;
            }
            Long nextUpdateTime = nextUpdateCal.getTimeInMillis()-now.getTimeInMillis();

            nextUpdate = nextUpdateCal.get(Calendar.HOUR_OF_DAY);
            customHandler.postDelayed(update2Hours,nextUpdateTime);
        }
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

    private class UpdateRotationData extends AsyncTask<Void,Void,Void> {

        ImageView loading;
        @Override
        protected void onPreExecute() {
            loading =(ImageView) getActivity().findViewById(R.id.loading_indicator);

            RotateAnimation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1000);
            loading.startAnimation(animation);
            loading.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String cookie;

                //Create Splatnet manager
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                cookie = settings.getString("cookie","");
                String uniqueId = settings.getString("unique_id","");

                Call<Schedules> rotationGet = splatnet.getSchedules(cookie,uniqueId);


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
                    Call<CurrentSplatfest> getSplatfest = splatnet.getActiveSplatfests(cookie,uniqueId);
                    response = getSplatfest.execute();
                    if(response.isSuccessful()){
                        currentSplatfest = (CurrentSplatfest) response.body();
                        if(currentSplatfest.splatfests.size()>0){
                            schedules.setSplatfest(currentSplatfest.splatfests.get(0));
                            database.insertSplatfests(currentSplatfest.splatfests);
                        }
                    }else if(response.code()==403){
                        AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                        alertDialog.show();
                    }
                }else if(response.code()==403){
                    AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                    alertDialog.show();
                }
                Call<SalmonSchedule> salmonGet = splatnet.getSalmonSchedule(cookie,uniqueId);
                response = salmonGet.execute();
                if(response.isSuccessful()){
                    salmonSchedule = (SalmonSchedule) response.body();
                }else if(response.code()==403){
                    AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Cookie is invalid, please obtain a new cookie");
                    alertDialog.show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                AlertDialog alertDialog = new AlertDialog(getActivity(),"Error: Could not reach Splatnet");
                alertDialog.show();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
            loading.setAnimation(null);
            loading.setVisibility(View.GONE);
        }

    }

    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            updateRotationData = new UpdateRotationData();
            updateRotationData.execute();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdateCal = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR);
            if(schedules.regular!=null&&schedules.splatfest!=null&&schedules.regular.size()>0&&schedules.splatfest.size()>0){
                if(schedules.regular.get(0).end<schedules.splatfest.get(0).end){
                    nextUpdateCal.setTimeInMillis(schedules.regular.get(0).end*1000);
                }else{
                    nextUpdateCal.setTimeInMillis(schedules.splatfest.get(0).end*1000);
                }
            }else if(schedules.regular!=null&&schedules.regular.size()>0){
                nextUpdateCal.setTimeInMillis(schedules.regular.get(0).end*1000);
            }else if (schedules.splatfest!=null&&schedules.splatfest.size()>0){
                nextUpdateCal.setTimeInMillis(schedules.splatfest.get(0).end*1000);
            }
            Long nextUpdateTime = nextUpdateCal.getTimeInMillis()-now.getTimeInMillis();

            nextUpdate = nextUpdateCal.get(Calendar.HOUR_OF_DAY);
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };
}