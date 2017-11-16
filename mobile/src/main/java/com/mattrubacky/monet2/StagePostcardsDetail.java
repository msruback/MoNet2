package com.mattrubacky.monet2;

import android.graphics.Typeface;
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

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

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
        RelativeLayout inkMeter = (RelativeLayout) findViewById(R.id.InkMeter);
        RelativeLayout inkLowerWhisker = (RelativeLayout) findViewById(R.id.InkLowerWhisker);
        RelativeLayout inkBox = (RelativeLayout) findViewById(R.id.InkBox);
        RelativeLayout inkLowerBox = (RelativeLayout) findViewById(R.id.InkLowerBox);
        RelativeLayout inkUpperBox = (RelativeLayout) findViewById(R.id.InkUpperBox);
        RelativeLayout inkUpperWhisker = (RelativeLayout) findViewById(R.id.InkUpperWhisker);

        inkBox.setClipToOutline(true);

        RelativeLayout killCard = (RelativeLayout) findViewById(R.id.killStats);
        RelativeLayout killMeter = (RelativeLayout) findViewById(R.id.KillMeter);
        RelativeLayout killLowerWhisker = (RelativeLayout) findViewById(R.id.KillLowerWhisker);
        RelativeLayout killBox = (RelativeLayout) findViewById(R.id.KillBox);
        RelativeLayout killLowerBox = (RelativeLayout) findViewById(R.id.KillLowerBox);
        RelativeLayout killUpperBox = (RelativeLayout) findViewById(R.id.KillUpperBox);
        RelativeLayout killUpperWhisker = (RelativeLayout) findViewById(R.id.KillUpperWhisker);

        killBox.setClipToOutline(true);

        RelativeLayout deathCard = (RelativeLayout) findViewById(R.id.deathStats);
        RelativeLayout deathMeter = (RelativeLayout) findViewById(R.id.DeathMeter);
        RelativeLayout deathLowerWhisker = (RelativeLayout) findViewById(R.id.DeathLowerWhisker);
        RelativeLayout deathBox = (RelativeLayout) findViewById(R.id.DeathBox);
        RelativeLayout deathLowerBox = (RelativeLayout) findViewById(R.id.DeathLowerBox);
        RelativeLayout deathUpperBox = (RelativeLayout) findViewById(R.id.DeathUpperBox);
        RelativeLayout deathUpperWhisker = (RelativeLayout) findViewById(R.id.DeathUpperWhisker);

        deathBox.setClipToOutline(true);

        RelativeLayout specialCard = (RelativeLayout) findViewById(R.id.specialStats);
        RelativeLayout specialMeter = (RelativeLayout) findViewById(R.id.SpecialMeter);
        RelativeLayout specialLowerWhisker = (RelativeLayout) findViewById(R.id.SpecialLowerWhisker);
        RelativeLayout specialBox = (RelativeLayout) findViewById(R.id.SpecialBox);
        RelativeLayout specialLowerBox = (RelativeLayout) findViewById(R.id.SpecialLowerBox);
        RelativeLayout specialUpperBox = (RelativeLayout) findViewById(R.id.SpecialUpperBox);
        RelativeLayout specialUpperWhisker = (RelativeLayout) findViewById(R.id.SpecialUpperWhisker);

        specialBox.setClipToOutline(true);

        TextView inkTitle = (TextView) findViewById(R.id.InkTitle);
        TextView inkMinimum = (TextView) findViewById(R.id.InkMinimum);
        TextView inkLowerQuartile = (TextView) findViewById(R.id.InkLowerQuartile);
        TextView inkMedian = (TextView) findViewById(R.id.InkMedian);
        TextView inkUpperQuartile = (TextView) findViewById(R.id.InkUpperQuartile);
        TextView inkMaximum = (TextView) findViewById(R.id.InkMaximum);

        TextView killTitle = (TextView) findViewById(R.id.KillTitle);
        TextView killMinimum = (TextView) findViewById(R.id.KillMinimum);
        TextView killLowerQuartile = (TextView) findViewById(R.id.KillLowerQuartile);
        TextView killMedian = (TextView) findViewById(R.id.KillMedian);
        TextView killUpperQuartile = (TextView) findViewById(R.id.KillUpperQuartile);
        TextView killMaximum = (TextView) findViewById(R.id.KillMaximum);

        TextView deathTitle = (TextView) findViewById(R.id.DeathTitle);
        TextView deathMinimum = (TextView) findViewById(R.id.DeathMinimum);
        TextView deathLowerQuartile = (TextView) findViewById(R.id.DeathLowerQuartile);
        TextView deathMedian = (TextView) findViewById(R.id.DeathMedian);
        TextView deathUpperQuartile = (TextView) findViewById(R.id.DeathUpperQuartile);
        TextView deathMaximum = (TextView) findViewById(R.id.DeathMaximum);

        TextView specialTitle = (TextView) findViewById(R.id.SpecialTitle);
        TextView specialMinimum = (TextView) findViewById(R.id.SpecialMinimum);
        TextView specialLowerQuartile = (TextView) findViewById(R.id.SpecialLowerQuartile);
        TextView specialMedian = (TextView) findViewById(R.id.SpecialMedian);
        TextView specialUpperQuartile = (TextView) findViewById(R.id.SpecialUpperQuartile);
        TextView specialMaximum = (TextView) findViewById(R.id.SpecialMaximum);


        ViewGroup.LayoutParams layoutParams = zoneWins.getLayoutParams();

        int meterWidth = zoneWinLossMeter.getWidth();
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
        meterWidth = rainmakerWinLossMeter.getWidth();
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
        meterWidth = towerWinLossMeter.getWidth();
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

        inkTitle.setTypeface(fontTitle);
        inkMinimum.setTypeface(font);
        inkLowerQuartile.setTypeface(font);
        inkMedian.setTypeface(font);
        inkUpperQuartile.setTypeface(font);
        inkMaximum.setTypeface(font);

        inkMinimum.setText(String.valueOf(stageStats.inkStats[0]));
        inkLowerQuartile.setText(String.valueOf(stageStats.inkStats[1]));
        inkMedian.setText(String.valueOf(stageStats.inkStats[2]));
        inkUpperQuartile.setText(String.valueOf(stageStats.inkStats[3]));
        inkMaximum.setText(String.valueOf(stageStats.inkStats[4]));

        if(stageStats.inkStats[1]==stageStats.inkStats[0]){
            inkLowerQuartile.setVisibility(View.GONE);
        }
        if(stageStats.inkStats[2]==stageStats.inkStats[1]){
            inkMedian.setVisibility(View.GONE);
        }
        if(stageStats.inkStats[3]==stageStats.inkStats[2]){
            inkUpperQuartile.setVisibility(View.GONE);
        }
        if(stageStats.inkStats[4]==stageStats.inkStats[3]){
            inkMaximum.setVisibility(View.GONE);
        }

        float range = stageStats.inkStats[4] - stageStats.inkStats[0];
        meterWidth = inkMeter.getMeasuredWidth();

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) inkMedian.getLayoutParams();

        if(range!=0) {
            //Need to position the inkMedian TextView so as to line it up with the center line
            width = (((stageStats.inkStats[2] - stageStats.inkStats[1])/range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkMedian.setLayoutParams(marginLayoutParams);

            layoutParams = inkLowerWhisker.getLayoutParams();
            width = ((stageStats.inkStats[1] - stageStats.inkStats[0])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = inkBox.getLayoutParams();
            width = ((stageStats.inkStats[3] - stageStats.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkBox.setLayoutParams(layoutParams);

            layoutParams = inkLowerBox.getLayoutParams();
            width = ((stageStats.inkStats[2] - stageStats.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperBox.getLayoutParams();
            width = ((stageStats.inkStats[3] - stageStats.inkStats[2])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkUpperBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperWhisker.getLayoutParams();
            width = ((stageStats.inkStats[4] - stageStats.inkStats[3])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkUpperWhisker.setLayoutParams(layoutParams);
        }else{
            inkCard.setVisibility(View.GONE);
        }

        //Kill card


        killTitle.setTypeface(fontTitle);
        killMinimum.setTypeface(font);
        killLowerQuartile.setTypeface(font);
        killMedian.setTypeface(font);
        killUpperQuartile.setTypeface(font);
        killMaximum.setTypeface(font);

        killMinimum.setText(String.valueOf(stageStats.killStats[0]));
        killLowerQuartile.setText(String.valueOf(stageStats.killStats[1]));
        killMedian.setText(String.valueOf(stageStats.killStats[2]));
        killUpperQuartile.setText(String.valueOf(stageStats.killStats[3]));
        killMaximum.setText(String.valueOf(stageStats.killStats[4]));

        if(stageStats.killStats[1]==stageStats.killStats[0]){
            killLowerQuartile.setVisibility(View.GONE);
        }
        if(stageStats.killStats[2]==stageStats.killStats[1]){
            killMedian.setVisibility(View.GONE);
        }
        if(stageStats.killStats[3]==stageStats.killStats[2]){
            killUpperQuartile.setVisibility(View.GONE);
        }
        if(stageStats.killStats[4]==stageStats.killStats[3]){
            killMaximum.setVisibility(View.GONE);
        }

        range = stageStats.killStats[4] - stageStats.killStats[0];
        layoutParams = killMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) killMedian.getLayoutParams();
            width = (((stageStats.killStats[2] - stageStats.killStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killMedian.setLayoutParams(marginLayoutParams);

            layoutParams = killLowerWhisker.getLayoutParams();
            width = ((stageStats.killStats[1] - stageStats.killStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = killBox.getLayoutParams();
            width = ((stageStats.killStats[3] - stageStats.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killBox.setLayoutParams(layoutParams);

            layoutParams = killLowerBox.getLayoutParams();
            width = ((stageStats.killStats[2] - stageStats.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerBox.setLayoutParams(layoutParams);

            layoutParams = killUpperBox.getLayoutParams();
            width = ((stageStats.killStats[3] - stageStats.killStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killUpperBox.setLayoutParams(layoutParams);

            layoutParams = killUpperWhisker.getLayoutParams();
            width = ((stageStats.killStats[4] - stageStats.killStats[3]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killUpperWhisker.setLayoutParams(layoutParams);
        }else{
            killCard.setVisibility(View.GONE);
        }

        //Death Card

        deathTitle.setTypeface(fontTitle);
        deathMinimum.setTypeface(font);
        deathLowerQuartile.setTypeface(font);
        deathMedian.setTypeface(font);
        deathUpperQuartile.setTypeface(font);
        deathMaximum.setTypeface(font);

        deathMinimum.setText(String.valueOf(stageStats.deathStats[0]));
        deathLowerQuartile.setText(String.valueOf(stageStats.deathStats[1]));
        deathMedian.setText(String.valueOf(stageStats.deathStats[2]));
        deathUpperQuartile.setText(String.valueOf(stageStats.deathStats[3]));
        deathMaximum.setText(String.valueOf(stageStats.deathStats[4]));

        if(stageStats.deathStats[1]==stageStats.deathStats[0]){
            deathLowerQuartile.setVisibility(View.GONE);
        }
        if(stageStats.deathStats[2]==stageStats.deathStats[1]){
            deathMedian.setVisibility(View.GONE);
        }
        if(stageStats.deathStats[3]==stageStats.deathStats[2]){
            deathUpperQuartile.setVisibility(View.GONE);
        }
        if(stageStats.deathStats[4]==stageStats.deathStats[3]){
            deathMaximum.setVisibility(View.GONE);
        }

        range = stageStats.deathStats[4] - stageStats.deathStats[0];
        layoutParams = deathMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) deathMedian.getLayoutParams();
            width = (((stageStats.deathStats[2] - stageStats.deathStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathMedian.setLayoutParams(marginLayoutParams);

            layoutParams = deathLowerWhisker.getLayoutParams();
            width = ((stageStats.deathStats[1] - stageStats.deathStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = deathBox.getLayoutParams();
            width = ((stageStats.deathStats[3] - stageStats.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathBox.setLayoutParams(layoutParams);

            layoutParams = deathLowerBox.getLayoutParams();
            width = ((stageStats.deathStats[2] - stageStats.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperBox.getLayoutParams();
            width = ((stageStats.deathStats[3] - stageStats.deathStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathUpperBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperWhisker.getLayoutParams();
            width = ((stageStats.deathStats[4] - stageStats.deathStats[3]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathUpperWhisker.setLayoutParams(layoutParams);
        }else{
            deathCard.setVisibility(View.GONE);
        }

        //Special Card

        specialTitle.setTypeface(fontTitle);
        specialMinimum.setTypeface(font);
        specialLowerQuartile.setTypeface(font);
        specialMedian.setTypeface(font);
        specialUpperQuartile.setTypeface(font);
        specialMaximum.setTypeface(font);

        specialMinimum.setText(String.valueOf(stageStats.specialStats[0]));
        specialLowerQuartile.setText(String.valueOf(stageStats.specialStats[1]));
        specialMedian.setText(String.valueOf(stageStats.specialStats[2]));
        specialUpperQuartile.setText(String.valueOf(stageStats.specialStats[3]));
        specialMaximum.setText(String.valueOf(stageStats.specialStats[4]));

        if(stageStats.specialStats[1]==stageStats.specialStats[0]){
            specialLowerQuartile.setVisibility(View.GONE);
        }
        if(stageStats.specialStats[2]==stageStats.specialStats[1]){
            specialMedian.setVisibility(View.GONE);
        }
        if(stageStats.specialStats[3]==stageStats.specialStats[2]){
            specialUpperQuartile.setVisibility(View.GONE);
        }
        if(stageStats.specialStats[4]==stageStats.specialStats[3]){
            specialMaximum.setVisibility(View.GONE);
        }

        range = stageStats.specialStats[4] - stageStats.specialStats[0];
        layoutParams = specialMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) specialMedian.getLayoutParams();
            width = (((stageStats.specialStats[2] - stageStats.specialStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialMedian.setLayoutParams(marginLayoutParams);

            layoutParams = specialLowerWhisker.getLayoutParams();
            width = ((stageStats.specialStats[1] - stageStats.specialStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = specialBox.getLayoutParams();
            width = ((stageStats.specialStats[3] - stageStats.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialBox.setLayoutParams(layoutParams);

            layoutParams = specialLowerBox.getLayoutParams();
            width = ((stageStats.specialStats[2] - stageStats.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperBox.getLayoutParams();
            width = ((stageStats.specialStats[3] - stageStats.specialStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialUpperBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperWhisker.getLayoutParams();
            width = ((stageStats.specialStats[4] - stageStats.specialStats[3]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialUpperWhisker.setLayoutParams(layoutParams);
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
