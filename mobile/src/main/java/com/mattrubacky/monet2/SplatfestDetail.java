package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.adapter.FesAdapter;
import com.mattrubacky.monet2.adapter.SplatfestPerformanceAdapter;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestResult;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.fragment.SplatfestDetail.TeamMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class SplatfestDetail extends AppCompatActivity {

    Splatfest splatfest;
    SplatfestResult result;
    SplatfestStats stats;

    RelativeLayout inkMeter,killMeter,deathMeter,specialMeter;
    Fragment inkSolo,inkTeam,killSolo,killTeam,deathSolo,deathTeam,specialSolo,specialTeam;
    boolean inkToggle,killToggle,deathToggle,specialToggle;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splatfest_detail);

        Bundle bundle = getIntent().getExtras();
        splatfest = bundle.getParcelable("splatfest");
        result = bundle.getParcelable("result");
        stats = bundle.getParcelable("stats");

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
        RelativeLayout moreStripes = (RelativeLayout) findViewById(R.id.moreStripes);
        RelativeLayout generalStats = (RelativeLayout) findViewById(R.id.generalStats);
        RelativeLayout inkStats = (RelativeLayout) findViewById(R.id.inkStats);
        inkMeter = (RelativeLayout) findViewById(R.id.InkMeter);
        RelativeLayout killStats = (RelativeLayout) findViewById(R.id.killStats);
        killMeter = (RelativeLayout) findViewById(R.id.KillMeter);
        RelativeLayout deathStats = (RelativeLayout) findViewById(R.id.deathStats);
        deathMeter = (RelativeLayout) findViewById(R.id.DeathMeter);
        RelativeLayout specialStats = (RelativeLayout) findViewById(R.id.specialStats);
        specialMeter = (RelativeLayout) findViewById(R.id.SpecialMeter);
        RelativeLayout votesButton = (RelativeLayout) findViewById(R.id.VotesButton);
        RelativeLayout battlesButton = (RelativeLayout) findViewById(R.id.BattlesButton);

        TextView splatfestTime = (TextView) findViewById(R.id.SplatfestTime);
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
        SplatfestPerformanceAdapter performanceAdapter = new SplatfestPerformanceAdapter(fragmentManager,splatfest,result,stats);

        generalDots.setupWithViewPager(generalPager, true);
        generalPager.setAdapter(performanceAdapter);

        //Stat Cards

        inkSolo = new SoloMeterFragment();
        Bundle meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.playerInkStats);
        inkSolo.setArguments(meterBundle);

        inkTeam = new TeamMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.teamInkStats);
        meterBundle.putFloat("average",stats.playerInkAverage);
        inkTeam.setArguments(meterBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.InkMeter,inkSolo)
                .commit();
        inkToggle = false;

        inkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inkToggle){
                    //On Team
                    fragmentManager.beginTransaction()
                            .replace(R.id.InkMeter, inkSolo)
                            .commit();
                    inkToggle = false;
                    inkButton.setText("Solo");
                }else{
                    //On Solo
                    fragmentManager.beginTransaction()
                            .replace(R.id.InkMeter, inkTeam)
                            .commit();
                    inkToggle = true;
                    inkButton.setText("Team");
                }
            }
        });

        killSolo = new SoloMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.playerKillStats);
        killSolo.setArguments(meterBundle);

        killTeam = new TeamMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.teamKillStats);
        meterBundle.putFloat("average",stats.playerKillAverage);
        killTeam.setArguments(meterBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.KillMeter,killSolo)
                .commit();
        killToggle = false;

        killButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(killToggle){
                    //On Team
                    fragmentManager.beginTransaction()
                            .replace(R.id.KillMeter,killSolo)
                            .commit();
                    killToggle = false;
                    killButton.setText("Solo");
                }else{
                    //On Solo
                    fragmentManager.beginTransaction()
                            .replace(R.id.KillMeter,killTeam)
                            .commit();
                    killToggle = true;
                    killButton.setText("Team");
                }
            }
        });

        deathSolo = new SoloMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.playerDeathStats);
        deathSolo.setArguments(meterBundle);

        deathTeam = new TeamMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.teamDeathStats);
        meterBundle.putFloat("average",stats.playerDeathAverage);
        deathTeam.setArguments(meterBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.DeathMeter,deathSolo)
                .commit();
        deathToggle = false;

        deathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deathToggle){
                    //On Team
                    fragmentManager.beginTransaction()
                            .replace(R.id.DeathMeter,deathSolo)
                            .commit();
                    deathToggle = false;
                    deathButton.setText("Solo");
                }else{
                    //On Solo
                    fragmentManager.beginTransaction()
                            .replace(R.id.DeathMeter,deathTeam)
                            .commit();
                    deathToggle = true;
                    deathButton.setText("Team");
                }
            }
        });

        specialSolo = new SoloMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.playerSpecialStats);
        specialSolo.setArguments(meterBundle);

        specialTeam = new TeamMeterFragment();
        meterBundle = new Bundle();
        meterBundle.putIntArray("stats",stats.teamSpecialStats);
        meterBundle.putFloat("average",stats.playerSpecialAverage);
        specialTeam.setArguments(meterBundle);


        fragmentManager.beginTransaction()
                .replace(R.id.SpecialMeter,specialSolo)
                .commit();
        specialToggle = false;

        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(specialToggle){
                    //On Team
                    fragmentManager.beginTransaction()
                            .replace(R.id.SpecialMeter,specialSolo)
                            .commit();
                    specialToggle = false;
                    specialButton.setText("Solo");
                }else{
                    //On Solo
                    fragmentManager.beginTransaction()
                            .replace(R.id.SpecialMeter,specialTeam)
                            .commit();
                    specialToggle = true;
                    specialButton.setText("Team");
                }
            }
        });

    }
}
