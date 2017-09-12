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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class Rotation extends AppCompatActivity {
    int TurfPage = 5;
    int LeaguePage = 5;
    int RankPage = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        ArrayList<String> salmonRunTimes=new ArrayList<String>();
        salmonRunTimes.add("9/10 8:00 AM - 9/11 2:00 PM");
        salmonRunTimes.add("9/12 2:00 PM - 9/13 8:00 AM");
        salmonRunTimes.add("9/14 2:00 AM - 9/15 2:00 AM");
        salmonRunTimes.add("9/15 8:00 PM - 9/17 2:00 AM");
        salmonRunTimes.add("9/17 8:00 PM - 9/18 8:00 PM");
        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(),"Paintball.otf");

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.title);

        TextView turfWarTitle = (TextView) findViewById(R.id.turfWarName);
        TextView rankedTitle = (TextView) findViewById(R.id.rankedName);
        TextView leagueTitle = (TextView) findViewById(R.id.leagueName);
        TextView salmonTitle = (TextView) findViewById(R.id.salmonName);

        ListView salmonTimes = (ListView) findViewById(R.id.SalmonTimes);

        ArrayAdapter<String> itemsAdapter = new SalmonAdapter(this,salmonRunTimes);

        salmonTimes.setAdapter(itemsAdapter);

        new RotationData().execute();

        turfWarTitle.setTypeface(fontTitle);
        rankedTitle.setTypeface(fontTitle);
        leagueTitle.setTypeface(fontTitle);
        salmonTitle.setTypeface(fontTitle);
    }

    class RotationData extends AsyncTask<Void, Void, Void> {
        Schedules schedules;

        @Override
        protected Void doInBackground(Void... params) {
            //Do Stuff that takes ages (background thread)
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
                System.out.println(cookie);
                Call<Schedules> rotationGet = splatnet.getSchedules(cookie);
                schedules = rotationGet.execute().body();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            //Call your next task (ui thread)=
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
    }



        //Adapters
    private class TurfAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public TurfAdapter(FragmentManager fm, ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
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
        }

        @Override
        public Fragment getItem(int position) {
            return new RankedRotation();
        }

        @Override
        public int getCount() {
            return RankPage;
        }

    }
    private class LeagueAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public LeagueAdapter(FragmentManager fm,ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
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
            return LeaguePage;
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

