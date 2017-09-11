package com.mattrubacky.monet2;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.ArrayList;

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

        getRotationData();

        turfWarTitle.setTypeface(fontTitle);
        rankedTitle.setTypeface(fontTitle);
        leagueTitle.setTypeface(fontTitle);
        salmonTitle.setTypeface(fontTitle);
    }
    private void getRotationData(){
        ArrayList<JSONObject> toReturn = new ArrayList<>();
        try {
            URL ninUrl = null;
            ninUrl = new URL("https://app.splatoon2.nintendo.net/api/schedules");
            HttpURLConnection request = (HttpURLConnection) (ninUrl.openConnection());

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            String cookie = settings.getString("cookie","error");
            request.setRequestProperty("Accept","*/*");
            request.setRequestProperty("Accept-Encoding","gzip, deflate");
            request.setRequestProperty("User-Agent","com.nintendo.znca/1.0.4 (Android/4.4.2)");
            request.setRequestProperty("Cookie",cookie);

            request.setRequestMethod("GET");

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

            ViewPager TurfPager = (ViewPager) findViewById(R.id.TurfPager);
            ViewPager RankPager = (ViewPager) findViewById(R.id.RankedPager);
            ViewPager LeaguePager = (ViewPager) findViewById(R.id.LeaguePager);

            PagerAdapter turfAdapter = new TurfAdapter(getSupportFragmentManager(),jsonParse.getJSONArray("regular"));
            PagerAdapter rankAdapter = new RankAdapter(getSupportFragmentManager(),jsonParse.getJSONArray("gachi"));
            PagerAdapter leagueAdapter = new LeagueAdapter(getSupportFragmentManager(),jsonParse.getJSONArray("league"));

            TurfPager.setAdapter(turfAdapter);
            RankPager.setAdapter(rankAdapter);
            LeaguePager.setAdapter(leagueAdapter);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Adapters
    private class TurfAdapter extends FragmentStatePagerAdapter {
        JSONArray input;
        public TurfAdapter(FragmentManager fm, JSONArray input) {
            super(fm);
            this.input = input;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment turf = new TurfRotation();
            try {
                Bundle bundle = new Bundle();
                bundle.putString("json",input.getJSONObject(position).toString());
                turf.setArguments(bundle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return turf;
        }

        @Override
        public int getCount() {
            return input.length();
        }

    }
    private class RankAdapter extends FragmentStatePagerAdapter {
        public RankAdapter(FragmentManager fm,JSONArray input) {
            super(fm);
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
        public LeagueAdapter(FragmentManager fm,JSONArray input) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new LeagueRotation();
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

