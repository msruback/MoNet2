package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StageStats;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class StagePostcardsDetail extends AppCompatActivity {

    StageStats stageStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_postcards);

        Bundle bundle = getIntent().getExtras();

        stageStats = bundle.getParcelable("stats");

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView stage = (ImageView) findViewById(R.id.StageImage);

        TextView name = (TextView) findViewById(R.id.Name);
        TextView number = (TextView) findViewById(R.id.Number);
        TextView turfTitle = (TextView) findViewById(R.id.TurfTitle);
        TextView turfWinText = (TextView) findViewById(R.id.TurfWinText);
        TextView turfLossText = (TextView) findViewById(R.id.TurfLossText);
        TextView zoneTitle = (TextView) findViewById(R.id.ZoneTitle);
        TextView zoneWinText = (TextView) findViewById(R.id.ZoneWinText);
        TextView zoneLossText = (TextView) findViewById(R.id.ZoneLossText);
        TextView rainmakerTitle = (TextView) findViewById(R.id.RainmakerTitle);
        TextView rainmakerWinText = (TextView) findViewById(R.id.RainmakerWinText);
        TextView rainmakerLossText = (TextView) findViewById(R.id.RainmakerLossText);
        TextView towerTitle = (TextView) findViewById(R.id.TowerTitle);
        TextView towerWinText = (TextView) findViewById(R.id.TowerWinText);
        TextView towerLossText = (TextView) findViewById(R.id.TowerLossText);
        TextView clamTitle = (TextView) findViewById(R.id.ClamTitle);
        TextView clamWinText = (TextView) findViewById(R.id.ClamWinText);
        TextView clamLossText = (TextView) findViewById(R.id.ClamLossText);
        TextView lastUsedTitle = (TextView) findViewById(R.id.LastTitleText);
        TextView lastUsed = (TextView) findViewById(R.id.LastText);

        name.setTypeface(fontTitle);
        turfTitle.setTypeface(fontTitle);
        zoneTitle.setTypeface(fontTitle);
        rainmakerTitle.setTypeface(fontTitle);
        towerTitle.setTypeface(fontTitle);
        clamTitle.setTypeface(fontTitle);

        number.setTypeface(font);
        turfWinText.setTypeface(font);
        turfLossText.setTypeface(font);
        zoneWinText.setTypeface(font);
        zoneLossText.setTypeface(font);
        rainmakerWinText.setTypeface(font);
        rainmakerLossText.setTypeface(font);
        towerWinText.setTypeface(font);
        towerLossText.setTypeface(font);
        clamWinText.setTypeface(font);
        clamLossText.setTypeface(font);
        lastUsedTitle.setTypeface(font);
        lastUsed.setTypeface(font);

        //Handle normal stat text
        name.setText(stageStats.stage.name);

        String numGames = stageStats.numGames +"/"+(stageStats.splatzonesWin+stageStats.splatzonesLose+stageStats.rainmakerWin+stageStats.rainmakerLose+stageStats.towerWin+stageStats.towerLose);
        number.setText(numGames);

        turfWinText.setText(String.valueOf(stageStats.turfWin));
        turfLossText.setText(String.valueOf(stageStats.turfLose));
        zoneWinText.setText(String.valueOf(stageStats.splatzonesWin));
        zoneLossText.setText(String.valueOf(stageStats.splatzonesLose));
        rainmakerWinText.setText(String.valueOf(stageStats.rainmakerWin));
        rainmakerLossText.setText(String.valueOf(stageStats.rainmakerLose));
        towerWinText.setText(String.valueOf(stageStats.towerWin));
        towerLossText.setText(String.valueOf(stageStats.towerLose));
        clamWinText.setText(String.valueOf(stageStats.clamWin));
        clamLossText.setText(String.valueOf(stageStats.clamLose));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String last = format.format(stageStats.lastPlayed*1000);
        lastUsed.setText(last);

        //Handle images
        ImageHandler imageHandler = new ImageHandler();

        String dirName = stageStats.stage.name.toLowerCase().replaceAll(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+stageStats.stage.url;

        if(imageHandler.imageExists("stage",dirName,getApplicationContext())){
            stage.setImageBitmap(imageHandler.loadImage("stage",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).resize(1280,720).into(stage);
            imageHandler.downloadImage("stage",dirName,url,getApplicationContext());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }



    public void updateUI(){

        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");
        FragmentManager fragmentManager = getSupportFragmentManager();

        RelativeLayout turfMeter = (RelativeLayout) findViewById(R.id.turfMeter);
        RelativeLayout turfWinLossMeter = (RelativeLayout) findViewById(R.id.TurfWinLossMeter);
        RelativeLayout turfWins = (RelativeLayout) findViewById(R.id.TurfWins);
        RelativeLayout turfLosses = (RelativeLayout) findViewById(R.id.TurfLosses);

        turfWinLossMeter.setClipToOutline(true);
        if(stageStats.turfWin==0&&stageStats.turfLose==0){
            turfMeter.setVisibility(View.GONE);
        }

        RelativeLayout zoneMeter = (RelativeLayout) findViewById(R.id.zoneMeter);
        RelativeLayout zoneWinLossMeter = (RelativeLayout) findViewById(R.id.ZoneWinLossMeter);
        RelativeLayout zoneWins = (RelativeLayout) findViewById(R.id.ZoneWins);
        RelativeLayout zoneLosses = (RelativeLayout) findViewById(R.id.ZoneLosses);

        zoneWinLossMeter.setClipToOutline(true);
        if(stageStats.splatzonesLose==0&&stageStats.splatzonesWin==0){
            zoneMeter.setVisibility(View.GONE);
        }

        RelativeLayout rainmakerMeter = (RelativeLayout) findViewById(R.id.rainmakerMeter);
        RelativeLayout rainmakerWinLossMeter = (RelativeLayout) findViewById(R.id.RainmakerWinLossMeter);
        RelativeLayout rainmakerWins = (RelativeLayout) findViewById(R.id.RainmakerWins);
        RelativeLayout rainmakerLosses = (RelativeLayout) findViewById(R.id.RainmakerLosses);

        rainmakerWinLossMeter.setClipToOutline(true);
        if(stageStats.rainmakerWin==0&&stageStats.rainmakerLose==0){
            rainmakerMeter.setVisibility(View.GONE);
        }

        RelativeLayout towerMeter = (RelativeLayout) findViewById(R.id.towerMeter);
        RelativeLayout towerWinLossMeter = (RelativeLayout) findViewById(R.id.TowerWinLossMeter);
        RelativeLayout towerWins = (RelativeLayout) findViewById(R.id.TowerWins);
        RelativeLayout towerLosses = (RelativeLayout) findViewById(R.id.TowerLosses);

        towerWinLossMeter.setClipToOutline(true);
        if(stageStats.towerWin==0&&stageStats.towerLose==0){
            towerMeter.setVisibility(View.GONE);
        }

        RelativeLayout clamMeter = (RelativeLayout) findViewById(R.id.clamMeter);
        RelativeLayout clamWinLossMeter = (RelativeLayout) findViewById(R.id.ClamWinLossMeter);
        RelativeLayout clamWins = (RelativeLayout) findViewById(R.id.ClamWins);
        RelativeLayout clamLosses = (RelativeLayout) findViewById(R.id.ClamLosses);

        clamWinLossMeter.setClipToOutline(true);
        if(stageStats.clamWin==0&&stageStats.clamLose==0){
            clamMeter.setVisibility(View.GONE);
        }

        RelativeLayout inkCard = (RelativeLayout) findViewById(R.id.inkStats);
        RelativeLayout killCard = (RelativeLayout) findViewById(R.id.killStats);
        RelativeLayout deathCard = (RelativeLayout) findViewById(R.id.deathStats);
        RelativeLayout specialCard = (RelativeLayout) findViewById(R.id.specialStats);
        RelativeLayout noStatsCard = (RelativeLayout) findViewById(R.id.noStats);

        TextView inkTitle = (TextView) findViewById(R.id.InkTitle);
        TextView killTitle = (TextView) findViewById(R.id.KillTitle);
        TextView deathTitle = (TextView) findViewById(R.id.DeathTitle);
        TextView specialTitle = (TextView) findViewById(R.id.SpecialTitle);
        TextView noStatsText = (TextView) findViewById(R.id.NoStatsText);

        inkTitle.setTypeface(fontTitle);
        killTitle.setTypeface(fontTitle);
        deathTitle.setTypeface(fontTitle);
        specialTitle.setTypeface(fontTitle);
        noStatsText.setTypeface(fontTitle);


        ViewGroup.LayoutParams layoutParams = turfWins.getLayoutParams();

        float total = stageStats.turfWin+stageStats.turfLose;
        float width = stageStats.turfWin/total;

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        turfWins.setLayoutParams(layoutParams);

        layoutParams = turfLosses.getLayoutParams();
        width = stageStats.turfLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        turfLosses.setLayoutParams(layoutParams);

        layoutParams = zoneWins.getLayoutParams();
        total = stageStats.splatzonesWin+stageStats.splatzonesLose;
        width = stageStats.splatzonesWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        zoneWins.setLayoutParams(layoutParams);

        layoutParams = zoneLosses.getLayoutParams();
        width = stageStats.splatzonesLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        zoneLosses.setLayoutParams(layoutParams);

        layoutParams = rainmakerWins.getLayoutParams();
        total = stageStats.rainmakerWin+stageStats.rainmakerLose;
        width = stageStats.rainmakerWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        rainmakerWins.setLayoutParams(layoutParams);

        layoutParams = rainmakerLosses.getLayoutParams();
        width = stageStats.rainmakerLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        rainmakerLosses.setLayoutParams(layoutParams);

        layoutParams = towerWins.getLayoutParams();
        total = stageStats.towerWin+stageStats.towerLose;
        width = stageStats.towerWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        towerWins.setLayoutParams(layoutParams);

        layoutParams = towerLosses.getLayoutParams();
        width = stageStats.towerLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        towerLosses.setLayoutParams(layoutParams);

        layoutParams = clamWins.getLayoutParams();
        total = stageStats.clamWin+stageStats.clamLose;
        width = stageStats.clamWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        clamWins.setLayoutParams(layoutParams);

        layoutParams = clamLosses.getLayoutParams();
        width = stageStats.clamLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        clamLosses.setLayoutParams(layoutParams);

        boolean hasStats = false;

        //Ink card
        float range = stageStats.inkStats[4] - stageStats.inkStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",stageStats.inkStats);
            Fragment ink = new SoloMeterFragment();
            ink.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.InkMeter,ink)
                    .commit();
            hasStats = true;
        }else{
            inkCard.setVisibility(View.GONE);
        }

        //Kill card
        range = stageStats.killStats[4] - stageStats.killStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",stageStats.killStats);
            Fragment kill = new SoloMeterFragment();
            kill.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.KillMeter,kill)
                    .commit();
            hasStats = true;
        }else{
            killCard.setVisibility(View.GONE);
        }

        //Death Card

        range = stageStats.deathStats[4] - stageStats.deathStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",stageStats.deathStats);
            Fragment death = new SoloMeterFragment();
            death.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.DeathMeter,death)
                    .commit();
            hasStats = true;
        }else{
            deathCard.setVisibility(View.GONE);
        }

        //Special Card

        range = stageStats.specialStats[4] - stageStats.specialStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",stageStats.specialStats);
            Fragment special = new SoloMeterFragment();
            special.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.SpecialMeter,special)
                    .commit();
            hasStats = true;
        }else{
            specialCard.setVisibility(View.GONE);
        }
        if(hasStats){
            noStatsCard.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
