package com.mattrubacky.monet2;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.adapter.SplatfestPerformanceAdapter;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestColor;
import com.mattrubacky.monet2.deserialized.SplatfestDatabase;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.deserialized.SplatfestVotes;
import com.mattrubacky.monet2.dialog.AlertDialog;
import com.mattrubacky.monet2.dialog.SplatfestBattleDialog;
import com.mattrubacky.monet2.dialog.VoteDialog;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.fragment.SplatfestDetail.TeamMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplatfestDetail extends AppCompatActivity {

    Splatfest splatfest;
    SplatfestResult result;
    SplatfestStats stats;
    SplatfestVotes votes;

    RelativeLayout inkMeter,killMeter,deathMeter,specialMeter,votesButton;
    Fragment inkSolo,inkTeam,killSolo,killTeam,deathSolo,deathTeam,specialSolo,specialTeam;
    boolean inkToggle,killToggle,deathToggle,specialToggle;

    ArrayList<Battle> battles;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splatfest_detail);

        Bundle bundle = getIntent().getExtras();
        splatfest = bundle.getParcelable("splatfest");
        result = bundle.getParcelable("result");
        StatCalc statCalc = new StatCalc(getApplicationContext(),splatfest);
        stats = statCalc.getSplatfestStats();
        battles = statCalc.getBattles();

        stats.grade = bundle.getString("grade");
        stats.power = bundle.getInt("power");


        votes = null;
        new UpdateVotes().execute();

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        title.setText(splatfest.names.alpha + " VS. "+splatfest.names.bravo);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView panel = (ImageView) findViewById(R.id.PanelImage);
        RelativeLayout moreInfo = (RelativeLayout) findViewById(R.id.moreInfo);
        RelativeLayout generalStats = (RelativeLayout) findViewById(R.id.generalStats);
        RelativeLayout inkStats = (RelativeLayout) findViewById(R.id.inkStats);
        inkMeter = (RelativeLayout) findViewById(R.id.InkMeter);
        RelativeLayout killStats = (RelativeLayout) findViewById(R.id.killStats);
        killMeter = (RelativeLayout) findViewById(R.id.KillMeter);
        RelativeLayout deathStats = (RelativeLayout) findViewById(R.id.deathStats);
        deathMeter = (RelativeLayout) findViewById(R.id.DeathMeter);
        RelativeLayout specialStats = (RelativeLayout) findViewById(R.id.specialStats);
        specialMeter = (RelativeLayout) findViewById(R.id.SpecialMeter);
        RelativeLayout extraStats = (RelativeLayout) findViewById(R.id.extraStats);
        votesButton = (RelativeLayout) findViewById(R.id.VotesButton);
        RelativeLayout battlesButton = (RelativeLayout) findViewById(R.id.BattlesButton);


        final TextView splatfestTime = (TextView) findViewById(R.id.SplatfestTime);
        TextView inkTitle = (TextView) findViewById(R.id.InkTitle);
        final TextView inkButton = (TextView) findViewById(R.id.InkButton);
        TextView killTitle = (TextView) findViewById(R.id.KillTitle);
        final TextView killButton = (TextView) findViewById(R.id.KillButton);
        TextView deathTitle = (TextView) findViewById(R.id.DeathTitle);
        final TextView deathButton = (TextView) findViewById(R.id.DeathButton);
        TextView specialTitle = (TextView) findViewById(R.id.SpecialTitle);
        final TextView specialButton = (TextView) findViewById(R.id.SpecialButton);
        TextView votesButtonText = (TextView) findViewById(R.id.VotesButtonText);
        TextView battlesButtonText = (TextView) findViewById(R.id.BattlesButtonText);

        //Colors

        moreInfo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        generalStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        inkStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        killStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        deathStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        specialStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        extraStats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));

        splatfestTime.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        inkTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        inkButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        killTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        killButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        deathTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        deathButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        specialTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        specialButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        votesButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        battlesButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));


        ViewPager generalPager = (ViewPager) findViewById(R.id.GeneralStatsPager);
        TabLayout generalDots = (TabLayout) findViewById(R.id.GeneralDots);

        splatfestTime.setTypeface(fontTitle);
        inkTitle.setTypeface(fontTitle);
        inkButton.setTypeface(fontTitle);
        killTitle.setTypeface(fontTitle);
        killButton.setTypeface(fontTitle);
        deathTitle.setTypeface(fontTitle);
        deathButton.setTypeface(fontTitle);
        specialTitle.setTypeface(fontTitle);
        specialButton.setTypeface(fontTitle);
        votesButtonText.setTypeface(fontTitle);
        battlesButtonText.setTypeface(fontTitle);

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = String.valueOf(splatfest.id);
        String url = "https://app.splatoon2.nintendo.net"+splatfest.images.panel;
        if (imageHandler.imageExists("splatfest", imageDirName, getApplicationContext())) {
            panel.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
        } else {
            Picasso.with(getApplicationContext()).load(url).into(panel);
            imageHandler.downloadImage("splatfest", imageDirName, url, getApplicationContext());
        }


        SimpleDateFormat format = new SimpleDateFormat("MM/DD/YY h a");


        StringBuilder time = new StringBuilder();
        time.append(format.format(splatfest.times.start*1000));
        time.append(" to ");
        time.append(format.format(splatfest.times.end*1000));

        splatfestTime.setText(time.toString());

        fragmentManager = getSupportFragmentManager();

        if(stats.timePlayed>0||result!=null) {
            SplatfestPerformanceAdapter performanceAdapter = new SplatfestPerformanceAdapter(fragmentManager, splatfest, result, stats);

            generalDots.setupWithViewPager(generalPager, true);
            generalPager.setAdapter(performanceAdapter);
        }else{
            generalStats.setVisibility(View.GONE);
        }

        //Stat Cards
        Bundle meterBundle;

        if(stats.playerInkStats[0]!=stats.playerInkStats[4]) {
            inkSolo = new SoloMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats",stats.playerInkStats);
            meterBundle.putParcelable("colors",splatfest.colors);
            inkSolo.setArguments(meterBundle);

            inkTeam = new TeamMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats",stats.teamInkStats);
            meterBundle.putFloat("average",stats.playerInkAverage);
            meterBundle.putParcelable("colors",splatfest.colors);
            inkTeam.setArguments(meterBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.InkMeter,inkSolo)
                    .commit();
            inkToggle = false;

            inkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inkToggle) {
                        //On Team
                        fragmentManager.beginTransaction()
                                .replace(R.id.InkMeter, inkSolo)
                                .commit();
                        inkToggle = false;
                        inkButton.setText("Solo");
                    } else {
                        //On Solo
                        fragmentManager.beginTransaction()
                                .replace(R.id.InkMeter, inkTeam)
                                .commit();
                        inkToggle = true;
                        inkButton.setText("Team");
                    }
                }
            });
        }else{
            inkStats.setVisibility(View.GONE);
        }

        if(stats.playerKillStats[0]!=stats.playerKillStats[4]) {
            killSolo = new SoloMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.playerKillStats);
            meterBundle.putParcelable("colors",splatfest.colors);
            killSolo.setArguments(meterBundle);

            killTeam = new TeamMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.teamKillStats);
            meterBundle.putFloat("average", stats.playerKillAverage);
            meterBundle.putParcelable("colors",splatfest.colors);
            killTeam.setArguments(meterBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.KillMeter, killSolo)
                    .commit();
            killToggle = false;

            killButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (killToggle) {
                        //On Team
                        fragmentManager.beginTransaction()
                                .replace(R.id.KillMeter, killSolo)
                                .commit();
                        killToggle = false;
                        killButton.setText("Solo");
                    } else {
                        //On Solo
                        fragmentManager.beginTransaction()
                                .replace(R.id.KillMeter, killTeam)
                                .commit();
                        killToggle = true;
                        killButton.setText("Team");
                    }
                }
            });
        }else{
            killStats.setVisibility(View.GONE);
        }

        if(stats.playerDeathStats[0]!=stats.playerDeathStats[4]) {
            deathSolo = new SoloMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.playerDeathStats);
            meterBundle.putParcelable("colors",splatfest.colors);
            deathSolo.setArguments(meterBundle);

            deathTeam = new TeamMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.teamDeathStats);
            meterBundle.putFloat("average", stats.playerDeathAverage);
            meterBundle.putParcelable("colors",splatfest.colors);
            deathTeam.setArguments(meterBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.DeathMeter, deathSolo)
                    .commit();
            deathToggle = false;

            deathButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deathToggle) {
                        //On Team
                        fragmentManager.beginTransaction()
                                .replace(R.id.DeathMeter, deathSolo)
                                .commit();
                        deathToggle = false;
                        deathButton.setText("Solo");
                    } else {
                        //On Solo
                        fragmentManager.beginTransaction()
                                .replace(R.id.DeathMeter, deathTeam)
                                .commit();
                        deathToggle = true;
                        deathButton.setText("Team");
                    }
                }
            });
        }else{
            deathStats.setVisibility(View.GONE);
        }

        if(stats.playerSpecialStats[0]!=stats.playerSpecialStats[4]) {
            specialSolo = new SoloMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.playerSpecialStats);
            meterBundle.putParcelable("colors",splatfest.colors);
            specialSolo.setArguments(meterBundle);

            specialTeam = new TeamMeterFragment();
            meterBundle = new Bundle();
            meterBundle.putIntArray("stats", stats.teamSpecialStats);
            meterBundle.putFloat("average", stats.playerSpecialAverage);
            meterBundle.putParcelable("colors",splatfest.colors);
            specialTeam.setArguments(meterBundle);


            fragmentManager.beginTransaction()
                    .replace(R.id.SpecialMeter, specialSolo)
                    .commit();
            specialToggle = false;

            specialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (specialToggle) {
                        //On Team
                        fragmentManager.beginTransaction()
                                .replace(R.id.SpecialMeter, specialSolo)
                                .commit();
                        specialToggle = false;
                        specialButton.setText("Solo");
                    } else {
                        //On Solo
                        fragmentManager.beginTransaction()
                                .replace(R.id.SpecialMeter, specialTeam)
                                .commit();
                        specialToggle = true;
                        specialButton.setText("Team");
                    }
                }
            });
        }else{
            specialStats.setVisibility(View.GONE);
        }

        votesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new VoteDialog(SplatfestDetail.this, votes, splatfest);
                dialog.show();
            }
        });
        if(votes==null){
            votesButton.setVisibility(View.GONE);
        }
        if(battles!=null||battles.size()>0) {
            battlesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SplatfestColor color,otherColor;
                    if (stats.grade.contains(splatfest.names.alpha)) {
                        color = splatfest.colors.alpha;
                        otherColor = splatfest.colors.bravo;
                    } else {
                        color = splatfest.colors.bravo;
                        otherColor = splatfest.colors.alpha;
                    }
                    Dialog dialog = new SplatfestBattleDialog(SplatfestDetail.this, battles, splatfest, color,otherColor);
                    dialog.show();
                }
            });
        }else{
            battlesButton.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private class UpdateVotes extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cookie = settings.getString("cookie","");
            String uniqueId = settings.getString("unique_id","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Call<SplatfestVotes> getVotes = splatnet.getSplatfestVotes(String.valueOf(splatfest.id),cookie,uniqueId);
                Response response = getVotes.execute();
                if(response.isSuccessful()){
                    votes = (SplatfestVotes) response.body();
                }else if(response.code()==403){
                    AlertDialog alertDialog = new AlertDialog(SplatfestDetail.this,"Error: Cookie is invalid, please obtain a new cookie");
                    alertDialog.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                AlertDialog alertDialog = new AlertDialog(SplatfestDetail.this,"Error: Could not reach Splatnet");
                alertDialog.show();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(votes!=null){
                votesButton.setVisibility(View.VISIBLE);
            }
        }

    }
}
