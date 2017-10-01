package com.mattrubacky.monet2;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

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

public class RotationFragment extends Fragment {
    Schedules schedules;
    android.os.Handler customHandler;
    ViewGroup rootView;
    WearLink wearLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(settings.contains("rotationState")) {
            Gson gson = new Gson();
            schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
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

        wearLink = new WearLink(getContext());

        ViewPager TurfPager = (ViewPager) rootView.findViewById(R.id.TurfPager);
        ViewPager RankPager = (ViewPager) rootView.findViewById(R.id.RankedPager);
        ViewPager LeaguePager = (ViewPager) rootView.findViewById(R.id.LeaguePager);

        PagerAdapter turfAdapter = new TurfAdapter(getFragmentManager(), schedules.regular);
        PagerAdapter rankAdapter = new RankAdapter(getFragmentManager(), schedules.ranked);
        PagerAdapter leagueAdapter = new LeagueAdapter(getFragmentManager(), schedules.league);

        TabLayout turfDots = (TabLayout) rootView.findViewById(R.id.TurfDots);
        TabLayout rankDots = (TabLayout) rootView.findViewById(R.id.RankDots);
        TabLayout leagueDots = (TabLayout) rootView.findViewById(R.id.LeagueDots);

        turfDots.setupWithViewPager(TurfPager, true);
        rankDots.setupWithViewPager(RankPager, true);
        leagueDots.setupWithViewPager(LeaguePager, true);

        TurfPager.setAdapter(turfAdapter);
        RankPager.setAdapter(rankAdapter);
        LeaguePager.setAdapter(leagueAdapter);

        TextView turfWarTitle = (TextView) rootView.findViewById(R.id.turfWarName);
        TextView rankedTitle = (TextView) rootView.findViewById(R.id.rankedName);
        TextView leagueTitle = (TextView) rootView.findViewById(R.id.leagueName);

        TextView turfError = (TextView) rootView.findViewById(R.id.TurfErrorMessage);
        TextView rankError = (TextView) rootView.findViewById(R.id.RankErrorMessage);


        customHandler = new android.os.Handler();

        Thread t = new Thread(updateRotationData);
        if(schedules.regular.size()==0){
            customHandler.post(update2Hours);
        }else {
            if ((schedules.regular.get(0).end * 1000) < new Date().getTime()) {
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
        }

        turfWarTitle.setTypeface(fontTitle);
        rankedTitle.setTypeface(fontTitle);
        leagueTitle.setTypeface(fontTitle);

        turfError.setTypeface(font);
        rankError.setTypeface(font);
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
        edit.commit();
        customHandler.removeCallbacks(updateUI);
        wearLink.closeConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        if(schedules!=null){
            customHandler.post(updateUI);
        }
        wearLink.openConnection();
    }

    //Get Rotation Data

    private Runnable updateRotationData = new Runnable()
    {
        public void run()
        {
            try {
                //Long now = Calendar.getInstance().getTimeInMillis();
                //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                String cookie;

                //Create Splatnet manager
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                cookie = settings.getString("cookie","");
                Call<Schedules> rotationGet = splatnet.getSchedules(cookie);
                Response response = rotationGet.execute();
                if(response.isSuccessful()){
                    schedules = (Schedules) response.body();
                }else{
                    if(schedules.getLength()>0){
                        schedules.dequeue();
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            Thread t = new Thread(updateRotationData);
            customHandler.postDelayed(updateUI,10000);
            t.start();
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
            customHandler.postDelayed(updateUI,nextUpdateTime+60000);
        }
    };
    private Runnable updateUI = new Runnable(){
        @Override
        public void run() {
            ViewPager TurfPager = (ViewPager) rootView.findViewById(R.id.TurfPager);
            ViewPager RankPager = (ViewPager) rootView.findViewById(R.id.RankedPager);
            ViewPager LeaguePager = (ViewPager) rootView.findViewById(R.id.LeaguePager);

            TabLayout turfDots = (TabLayout) rootView.findViewById(R.id.TurfDots);
            TabLayout rankDots = (TabLayout) rootView.findViewById(R.id.RankDots);
            TabLayout leagueDots = (TabLayout) rootView.findViewById(R.id.LeagueDots);

            turfDots.setupWithViewPager(TurfPager, true);
            rankDots.setupWithViewPager(RankPager, true);
            leagueDots.setupWithViewPager(LeaguePager, true);

            PagerAdapter turfAdapter = new TurfAdapter(getFragmentManager(), schedules.regular);
            PagerAdapter rankAdapter = new RankAdapter(getFragmentManager(), schedules.ranked);
            PagerAdapter leagueAdapter = new LeagueAdapter(getFragmentManager(), schedules.league);

            TurfPager.setAdapter(turfAdapter);
            RankPager.setAdapter(rankAdapter);
            LeaguePager.setAdapter(leagueAdapter);
            wearLink.sendRotation(schedules);
        }
    };





    //Adapters
    private class TurfAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public TurfAdapter(FragmentManager fm, ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
            if(getCount()>0){
                TextView errorMessage = (TextView) rootView.findViewById(R.id.TurfErrorMessage);
                errorMessage.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment turf = new TurfRotation();
            turf.setArguments(bundle);
            return turf;
        }

        @Override
        public int getCount() {
            return input.size();
        }

    }
    private class RankAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public RankAdapter(FragmentManager fm,ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
            if(getCount()>0){
                TextView errorMessage = (TextView) rootView.findViewById(R.id.RankErrorMessage);
                errorMessage.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment rank = new RankedRotation();
            rank.setArguments(bundle);
            return rank;
        }

        @Override
        public int getCount() {
            return input.size();
        }

    }
    private class LeagueAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public LeagueAdapter(FragmentManager fm,ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
            if(getCount()>0){
                TextView errorMessage = (TextView) rootView.findViewById(R.id.LeagueErrorMessage);
                errorMessage.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment league = new LeagueRotation();
            league.setArguments(bundle);
            return league;
        }

        @Override
        public int getCount() {
            return input.size();
        }

    }
}
