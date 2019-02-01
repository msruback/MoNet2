package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.adapter.RecyclerView.PlayerInfoAdapter;

import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.Player;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BattleInfo extends AppCompatActivity {
    Battle battle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        battle = bundle.getParcelable("battle");

        RelativeLayout meter = findViewById(R.id.meter);
        meter.setClipToOutline(true);

        RelativeLayout fesMode = findViewById(R.id.FesMode);
        RelativeLayout alpha = findViewById(R.id.Alpha);
        RelativeLayout bravo = findViewById(R.id.Bravo);

        RelativeLayout allyCard = findViewById(R.id.allies);
        RelativeLayout foeCard = findViewById(R.id.foes);

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RelativeLayout allyMeter = findViewById(R.id.AllyMeter);
        RelativeLayout foeMeter = findViewById(R.id.FoeMeter);

        ImageView stageImage = findViewById(R.id.StageImage);
        ImageView modeImage = findViewById(R.id.Mode);
        ImageView allyImage = findViewById(R.id.AllyFesIcon);
        ImageView foeImage = findViewById(R.id.FoeFesIcon);

        TextView stageName = findViewById(R.id.StageName);
        TextView rule = findViewById(R.id.Rule);
        TextView allyTitle = findViewById(R.id.allyTitle);
        TextView foeTitle = findViewById(R.id.foeTitle);
        TextView allyCount = findViewById(R.id.AllyPercent);
        TextView foeCount = findViewById(R.id.FoePercent);
        TextView result = findViewById(R.id.Result);
        TextView power = findViewById(R.id.Power);
        TextView startTime = findViewById(R.id.Time);
        TextView elapsedTime = findViewById(R.id.Length);

        title.setTypeface(fontTitle);
        stageName.setTypeface(fontTitle);
        rule.setTypeface(fontTitle);
        allyTitle.setTypeface(font);
        foeTitle.setTypeface(font);
        allyCount.setTypeface(font);
        foeCount.setTypeface(font);
        result.setTypeface(fontTitle);
        power.setTypeface(font);
        startTime.setTypeface(font);
        elapsedTime.setTypeface(font);


        SimpleDateFormat startFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");

        String start = startFormat.format(battle.start*1000);

        title.setText("Battle #"+battle.id);
        if(battle.result.key.equals("victory")){
            result.setText(getResources().getString(R.string.victory));
        }else{
            result.setText(getResources().getString(R.string.defeat));
        }
        startTime.setText(start);
        rule.setText(battle.rule.name);
        stageName.setText(battle.stage.name);

        String url = "https://app.splatoon2.nintendo.net"+battle.stage.url;

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = battle.stage.name.toLowerCase().replace(" ", "_");
        if (imageHandler.imageExists("stage", imageDirName, getApplicationContext())) {
            stageImage.setImageBitmap(imageHandler.loadImage("stage", imageDirName));
        } else {
            Picasso.with(getApplicationContext()).load(url).into(stageImage);
            imageHandler.downloadImage("stage", imageDirName, url, getApplicationContext());
        }

        int total;
        float allyWidth,foeWidth;
        ViewGroup.LayoutParams allyParams,foeParams;
        String percentCount,elapsed;
        SimpleDateFormat elapsedFormat = new SimpleDateFormat("mm:ss");
        switch(battle.type){
            case "regular":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_regular));

                allyImage.setVisibility(View.GONE);
                foeImage.setVisibility(View.GONE);
                fesMode.setVisibility(View.GONE);
                power.setVisibility(View.GONE);
                elapsedTime.setVisibility(View.GONE);

                allyWidth = (float) 2.5*battle.myTeamPercent;
                foeWidth = (float) 2.5*battle.otherTeamPercent;

                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = battle.myTeamPercent + "%";
                allyCount.setText(percentCount);
                percentCount = battle.otherTeamPercent + "%";
                foeCount.setText(percentCount);
                break;
            case "gachi":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_ranked));

                allyImage.setVisibility(View.GONE);
                foeImage.setVisibility(View.GONE);
                fesMode.setVisibility(View.GONE);

                elapsed = elapsedFormat.format(battle.time*1000);
                elapsedTime.setText(elapsed);
                String powerString = String.valueOf(battle.gachiPower);
                power.setText(powerString);

                total = battle.myTeamCount+battle.otherTeamCount;
                allyWidth = (float) battle.myTeamCount/total;
                allyWidth *= 250;
                foeWidth = (float) battle.otherTeamCount/total;
                foeWidth *= 250;

                allyParams = allyMeter.getLayoutParams();
                allyParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = String.valueOf(battle.myTeamCount);
                allyCount.setText(percentCount);
                percentCount = String.valueOf(battle.otherTeamCount);
                foeCount.setText(percentCount);
                break;
            case "league":
                modeImage.setImageDrawable(getResources().getDrawable(R.drawable.battle_league));

                allyImage.setVisibility(View.GONE);
                foeImage.setVisibility(View.GONE);
                fesMode.setVisibility(View.GONE);
                power.setVisibility(View.GONE);

                elapsed = elapsedFormat.format(battle.time*1000);
                elapsedTime.setText(elapsed);

                total = battle.myTeamCount+battle.otherTeamCount;
                allyWidth = (float) 250*(battle.myTeamCount/total);
                foeWidth = (float) 250*(battle.otherTeamCount/total);

                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = String.valueOf(battle.myTeamCount);
                allyCount.setText(percentCount);
                percentCount = String.valueOf(battle.otherTeamCount);
                foeCount.setText(percentCount);
                break;
            case "fes":
                Splatfest splatfest = bundle.getParcelable("splatfest");

                allyCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                foeCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                allyMeter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                foeMeter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));

                alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
                bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
                if(battle.myTheme.key.equals("alpha")){

                    url = "https://app.splatoon2.nintendo.net"+splatfest.images.alpha;
                    imageDirName = splatfest.names.alpha.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("splatfest", imageDirName, getApplicationContext())) {
                        allyImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
                    } else {
                        Picasso.with(getApplicationContext()).load(url).into(allyImage);
                        imageHandler.downloadImage("splatfest", imageDirName, url, getApplicationContext());
                    }
                }else{

                    url = "https://app.splatoon2.nintendo.net"+splatfest.images.bravo;
                    imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("splatfest", imageDirName, getApplicationContext())) {
                        allyImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
                    } else {
                        Picasso.with(getApplicationContext()).load(url).into(allyImage);
                        imageHandler.downloadImage("splatfest", imageDirName, url, getApplicationContext());
                    }
                }
                if(battle.otherTheme.key.equals("alpha")){
                    url = "https://app.splatoon2.nintendo.net"+splatfest.images.alpha;
                    imageDirName = splatfest.names.alpha.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("splatfest", imageDirName, getApplicationContext())) {
                        foeImage.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getApplicationContext()).load(url).into(foeImage);
                        imageHandler.downloadImage("weapon", imageDirName, url, getApplicationContext());
                    }
                }else{
                    url = "https://app.splatoon2.nintendo.net"+splatfest.images.bravo;
                    imageDirName = splatfest.names.bravo.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("splatfest", imageDirName, getApplicationContext())) {
                        foeImage.setImageBitmap(imageHandler.loadImage("splatfest", imageDirName));
                    } else {
                        Picasso.with(getApplicationContext()).load(url).into(foeImage);
                        imageHandler.downloadImage("splatfest", imageDirName, url, getApplicationContext());
                    }
                }

                modeImage.setVisibility(View.GONE);
                elapsedTime.setVisibility(View.GONE);

                powerString = String.valueOf(battle.myFesPower);
                power.setText(powerString);

                allyWidth = (float) 2.5*battle.myTeamPercent;
                foeWidth = (float) 2.5*battle.otherTeamPercent;
                allyParams = allyMeter.getLayoutParams();
                allyParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, allyWidth, getResources().getDisplayMetrics());
                allyMeter.setLayoutParams(allyParams);

                foeParams = foeMeter.getLayoutParams();
                foeParams.width =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, foeWidth, getResources().getDisplayMetrics());
                foeMeter.setLayoutParams(foeParams);

                percentCount = battle.myTeamPercent + "%";
                allyCount.setText(percentCount);
                percentCount = battle.otherTeamPercent + "%";
                foeCount.setText(percentCount);

                rule.setText("Splatfest Battle");

        }
        ArrayList<Player> allies = new ArrayList<>();
        allies.add(battle.user);
        allies.addAll(battle.myTeam);

        final RecyclerView allyList = findViewById(R.id.AllyList);
        final RecyclerView foeList = findViewById(R.id.FoeList);


        PlayerInfoAdapter allyAdapter = new PlayerInfoAdapter(BattleInfo.this,allies,allyList,battle,true);
        PlayerInfoAdapter foeAdapter = new PlayerInfoAdapter(BattleInfo.this,battle.otherTeam,foeList,battle,false);

        allyList.setAdapter(allyAdapter);
        foeList.setAdapter(foeAdapter);
        allyList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        foeList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}