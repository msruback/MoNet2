package com.mattrubacky.monet2.ui.activities;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.Pager.SplatfestPerformanceAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestVotes;
import com.mattrubacky.monet2.ui.dialog.SplatfestBattleDialog;
import com.mattrubacky.monet2.ui.dialog.VoteDialog;
import com.mattrubacky.monet2.ui.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.ui.fragment.SplatfestDetail.TeamMeterFragment;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.data.stats.SplatfestStats;
import com.mattrubacky.monet2.backend.api.splatnet.SplatfestVoteRequest;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnector;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class SplatfestDetail extends AppCompatActivity implements SplatnetConnected{

    private Splatfest splatfest;
    private SplatfestResult result;
    private SplatfestStats stats;
    private SplatfestVotes votes;

    private RelativeLayout inkMeter,killMeter,deathMeter,specialMeter,votesButton;
    private Fragment inkSolo,inkTeam,killSolo,killTeam,deathSolo,deathTeam,specialSolo,specialTeam;
    private boolean inkToggle,killToggle,deathToggle,specialToggle;


    private FragmentManager fragmentManager;

    private SplatnetConnector splatnetConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splatfest_detail);

        Bundle bundle = getIntent().getExtras();
        splatfest = bundle.getParcelable("splatfest");
        result = bundle.getParcelable("result");
        stats = new SplatfestStats();
        stats.splatfest = splatfest;
        stats.grade = bundle.getString("grade");
        stats.power = bundle.getInt("power");
        stats.calcStats(SplatfestDetail.this);
        

        votes = null;

        splatnetConnector = new SplatnetConnector((SplatnetConnected)this,this);
        splatnetConnector.addRequest(new SplatfestVoteRequest(splatfest.id));
        splatnetConnector.execute();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Typeface fontTitle;
        if(settings.getBoolean("useInkling",false)){
            fontTitle = Typeface.createFromAsset(getAssets(),"Inkling.otf");
        }else{
            fontTitle = Typeface.createFromAsset(getAssets(),"Paintball.otf");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        title.setText(splatfest.names.alpha + " VS. "+splatfest.names.bravo);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView panel = findViewById(R.id.PanelImage);
        RelativeLayout moreInfo = findViewById(R.id.moreInfo);
        RelativeLayout generalStats = findViewById(R.id.generalStats);
        RelativeLayout inkStats = findViewById(R.id.inkStats);
        inkMeter = findViewById(R.id.InkMeter);
        RelativeLayout killStats = findViewById(R.id.killStats);
        killMeter = findViewById(R.id.KillMeter);
        RelativeLayout deathStats = findViewById(R.id.deathStats);
        deathMeter = findViewById(R.id.DeathMeter);
        RelativeLayout specialStats = findViewById(R.id.specialStats);
        specialMeter = findViewById(R.id.SpecialMeter);
        RelativeLayout extraStats = findViewById(R.id.extraStats);
        votesButton = findViewById(R.id.VotesButton);
        RelativeLayout battlesButton = findViewById(R.id.BattlesButton);


        final TextView splatfestTime = findViewById(R.id.SplatfestTime);
        TextView inkTitle = findViewById(R.id.InkTitle);
        final TextView inkButton = findViewById(R.id.InkButton);
        TextView killTitle = findViewById(R.id.KillTitle);
        final TextView killButton = findViewById(R.id.KillButton);
        TextView deathTitle = findViewById(R.id.DeathTitle);
        final TextView deathButton = findViewById(R.id.DeathButton);
        TextView specialTitle = findViewById(R.id.SpecialTitle);
        final TextView specialButton = findViewById(R.id.SpecialButton);
        TextView votesButtonText = findViewById(R.id.VotesButtonText);
        TextView battlesButtonText = findViewById(R.id.BattlesButtonText);

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


        ViewPager generalPager = findViewById(R.id.GeneralStatsPager);

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


        SimpleDateFormat format = new SimpleDateFormat("MM/d/yy h a");


        StringBuilder time = new StringBuilder();
        time.append(format.format(splatfest.times.start*1000));
        time.append(" to ");
        time.append(format.format(splatfest.times.end*1000));

        splatfestTime.setText(time.toString());

        fragmentManager = getSupportFragmentManager();

        if(stats.timePlayed==0&&result.rates.vote.alpha==0) {
            generalStats.setVisibility(View.GONE);
        }else{
            SplatfestPerformanceAdapter performanceAdapter = new SplatfestPerformanceAdapter(fragmentManager, splatfest, result, stats);

            generalPager.setAdapter(performanceAdapter);
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
        if(stats.battles!=null&&stats.battles.size()>0) {
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
                    Dialog dialog = new SplatfestBattleDialog(SplatfestDetail.this, stats.battles, splatfest, color,otherColor);
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

    @Override
    public void update(Bundle bundle) {
        votes = bundle.getParcelable("votes");
        if(votes!=null){
            votesButton.setVisibility(View.VISIBLE);
        }
    }
}
