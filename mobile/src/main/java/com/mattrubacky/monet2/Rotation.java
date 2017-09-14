package com.mattrubacky.monet2;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Cookie;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class Rotation extends AppCompatActivity {
    Schedules schedules;
    android.os.Handler customHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(),"Paintball.otf");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

        ViewPager TurfPager = (ViewPager) findViewById(R.id.TurfPager);
        ViewPager RankPager = (ViewPager) findViewById(R.id.RankedPager);
        ViewPager LeaguePager = (ViewPager) findViewById(R.id.LeaguePager);

        PagerAdapter turfAdapter = new TurfAdapter(getSupportFragmentManager(), schedules.regular);
        PagerAdapter rankAdapter = new RankAdapter(getSupportFragmentManager(), schedules.ranked);
        PagerAdapter leagueAdapter = new LeagueAdapter(getSupportFragmentManager(), schedules.league);

        TurfPager.setAdapter(turfAdapter);
        RankPager.setAdapter(rankAdapter);
        LeaguePager.setAdapter(leagueAdapter);


        ArrayList<String> salmonRunTimes=new ArrayList<String>();
        salmonRunTimes.add("9/10 8:00 AM - 9/11 2:00 PM");
        salmonRunTimes.add("9/12 2:00 PM - 9/13 8:00 AM");
        salmonRunTimes.add("9/14 2:00 AM - 9/15 2:00 AM");
        salmonRunTimes.add("9/15 8:00 PM - 9/17 2:00 AM");
        salmonRunTimes.add("9/17 8:00 PM - 9/18 8:00 PM");

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.title);

        TextView turfWarTitle = (TextView) findViewById(R.id.turfWarName);
        TextView rankedTitle = (TextView) findViewById(R.id.rankedName);
        TextView leagueTitle = (TextView) findViewById(R.id.leagueName);
        TextView salmonTitle = (TextView) findViewById(R.id.salmonName);

        TextView turfError = (TextView) findViewById(R.id.TurfErrorMessage);
        TextView rankError = (TextView) findViewById(R.id.RankErrorMessage);

        ListView salmonTimes = (ListView) findViewById(R.id.SalmonTimes);

        ArrayAdapter<String> itemsAdapter = new SalmonAdapter(this,salmonRunTimes);

        customHandler = new android.os.Handler();

        salmonTimes.setAdapter(itemsAdapter);
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
        salmonTitle.setTypeface(fontTitle);

        turfError.setTypeface(font);
        rankError.setTypeface(font);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelable("schedules",schedules);
        // etc.
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        edit.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        schedules = savedInstanceState.getParcelable("schedules");

        ViewPager TurfPager = (ViewPager) findViewById(R.id.TurfPager);
        ViewPager RankPager = (ViewPager) findViewById(R.id.RankedPager);
        ViewPager LeaguePager = (ViewPager) findViewById(R.id.LeaguePager);

        PagerAdapter turfAdapter = new TurfAdapter(getSupportFragmentManager(), schedules.regular);
        PagerAdapter rankAdapter = new RankAdapter(getSupportFragmentManager(), schedules.ranked);
        PagerAdapter leagueAdapter = new LeagueAdapter(getSupportFragmentManager(), schedules.league);

        TurfPager.setAdapter(turfAdapter);
        RankPager.setAdapter(rankAdapter);
        LeaguePager.setAdapter(leagueAdapter);
    }

    //Get Rotation Data

    private Runnable updateRotationData = new Runnable()
    {
        public void run()
        {
            try {
                Long now = Calendar.getInstance().getTimeInMillis();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cookie;

                //Create Splatnet manager
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);

                //Check if cookie is valid
                if ((settings.getLong("cookie_expire", 0)*1000) < now) {
                    //Replace cookie
                    CookieManager cookieManager = new CookieManager();
                    cookie = cookieManager.getCookie(settings.getString("token",""),getApplicationContext());

                } else {
                    //Retrieve cookie
                    cookie = settings.getString("cookie", "");
                }
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
            Rotation.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ViewPager TurfPager = (ViewPager) findViewById(R.id.TurfPager);
                    ViewPager RankPager = (ViewPager) findViewById(R.id.RankedPager);
                    ViewPager LeaguePager = (ViewPager) findViewById(R.id.LeaguePager);

                    PagerAdapter turfAdapter = new TurfAdapter(getSupportFragmentManager(), schedules.regular);
                    PagerAdapter rankAdapter = new RankAdapter(getSupportFragmentManager(), schedules.ranked);
                    PagerAdapter leagueAdapter = new LeagueAdapter(getSupportFragmentManager(), schedules.league);

                    TurfPager.setAdapter(turfAdapter);
                    RankPager.setAdapter(rankAdapter);
                    LeaguePager.setAdapter(leagueAdapter);
                }
            });
        }
    };
    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            Thread t = new Thread(updateRotationData);
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
        }
    };





        //Adapters
    private class TurfAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public TurfAdapter(FragmentManager fm, ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
            if(getCount()>0){
                TextView errorMessage = (TextView) findViewById(R.id.TurfErrorMessage);
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
                TextView errorMessage = (TextView) findViewById(R.id.RankErrorMessage);
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
                TextView errorMessage = (TextView) findViewById(R.id.LeagueErrorMessage);
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
    private class SalmonAdapter extends ArrayAdapter<String>{
        public SalmonAdapter(Context context, ArrayList<String> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_salmon_run, parent, false);
            }
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            // Lookup view for data population
            // Populate the data into the template view using the data object
            TextView time = (TextView) convertView.findViewById(R.id.Time);
            RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.item);
            time.setText(getItem(position));
            time.setTypeface(font);
            if(position%2==0){
                layout.setBackgroundResource(R.color.transparent);
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

}

