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

import com.mattrubacky.monet2.deserialized.StageStats;
import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
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
        TextView zoneWinText = (TextView) findViewById(R.id.ZoneWinText);
        TextView zoneLossText = (TextView) findViewById(R.id.ZoneLossText);
        TextView rainmakerWinText = (TextView) findViewById(R.id.RainmakerWinText);
        TextView rainmakerLossText = (TextView) findViewById(R.id.RainmakerLossText);
        TextView towerWinText = (TextView) findViewById(R.id.TowerWinText);
        TextView towerLossText = (TextView) findViewById(R.id.TowerLossText);
        TextView lastUsedTitle = (TextView) findViewById(R.id.LastTitleText);
        TextView lastUsed = (TextView) findViewById(R.id.LastText);

        name.setTypeface(fontTitle);
        number.setTypeface(font);
        zoneWinText.setTypeface(font);
        zoneLossText.setTypeface(font);
        rainmakerWinText.setTypeface(font);
        rainmakerLossText.setTypeface(font);
        towerWinText.setTypeface(font);
        towerLossText.setTypeface(font);
        lastUsedTitle.setTypeface(font);
        lastUsed.setTypeface(font);

        //Handle normal stat text
        name.setText(stageStats.stage.name);

        String numGames = stageStats.numGames +"/"+(stageStats.splatzonesWin+stageStats.splatzonesLose+stageStats.rainmakerWin+stageStats.rainmakerLose+stageStats.towerWin+stageStats.towerLose);
        number.setText(numGames);

        zoneWinText.setText(String.valueOf(stageStats.splatzonesWin));
        zoneLossText.setText(String.valueOf(stageStats.splatzonesLose));
        rainmakerWinText.setText(String.valueOf(stageStats.rainmakerWin));
        rainmakerLossText.setText(String.valueOf(stageStats.rainmakerLose));
        towerWinText.setText(String.valueOf(stageStats.towerWin));
        towerLossText.setText(String.valueOf(stageStats.towerLose));

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

        RelativeLayout inkCard = (RelativeLayout) findViewById(R.id.inkStats);
        RelativeLayout killCard = (RelativeLayout) findViewById(R.id.killStats);
        RelativeLayout deathCard = (RelativeLayout) findViewById(R.id.deathStats);
        RelativeLayout specialCard = (RelativeLayout) findViewById(R.id.specialStats);

        TextView inkTitle = (TextView) findViewById(R.id.InkTitle);
        TextView killTitle = (TextView) findViewById(R.id.KillTitle);
        TextView deathTitle = (TextView) findViewById(R.id.DeathTitle);
        TextView specialTitle = (TextView) findViewById(R.id.SpecialTitle);

        inkTitle.setTypeface(fontTitle);
        killTitle.setTypeface(fontTitle);
        deathTitle.setTypeface(fontTitle);
        specialTitle.setTypeface(fontTitle);


        ViewGroup.LayoutParams layoutParams = zoneWins.getLayoutParams();

        float total = stageStats.splatzonesWin+stageStats.splatzonesLose;
        float width = stageStats.splatzonesWin/total;

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
        }else{
            specialCard.setVisibility(View.GONE);
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
