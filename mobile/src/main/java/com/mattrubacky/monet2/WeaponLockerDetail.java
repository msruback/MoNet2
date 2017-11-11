package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class WeaponLockerDetail extends AppCompatActivity {

    WeaponStats weaponStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_stats);

        Bundle bundle = getIntent().getExtras();

        weaponStats = bundle.getParcelable("stats");

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView flag = (ImageView) findViewById(R.id.Flag);
        ImageView weapon = (ImageView) findViewById(R.id.WeaponImage);
        ImageView sub = (ImageView) findViewById(R.id.Sub);
        ImageView special = (ImageView) findViewById(R.id.Special);

        TextView name = (TextView) findViewById(R.id.Name);
        TextView number = (TextView) findViewById(R.id.Number);
        TextView winText = (TextView) findViewById(R.id.WinText);
        TextView lossText = (TextView) findViewById(R.id.LossText);
        TextView inkedTitle = (TextView) findViewById(R.id.InkedTitleText);
        TextView inked = (TextView) findViewById(R.id.InkedText);
        TextView lastUsedTitle = (TextView) findViewById(R.id.LastTitleText);
        TextView lastUsed = (TextView) findViewById(R.id.LastText);

        name.setTypeface(fontTitle);
        number.setTypeface(font);
        winText.setTypeface(font);
        lossText.setTypeface(font);
        inkedTitle.setTypeface(font);
        inked.setTypeface(font);
        lastUsedTitle.setTypeface(font);
        lastUsed.setTypeface(font);

        //Handle Win meter
        if(weaponStats.maxWinMeter<10){
            //raw
            flag.setImageDrawable(getDrawable(R.drawable.icon_flag));
        }else if(weaponStats.maxWinMeter<15){
            //fresh
            flag.setImageDrawable(getDrawable(R.drawable.icon_flag_bronze));
        }else if(weaponStats.maxWinMeter<50){
            //superfresh
            flag.setImageDrawable(getDrawable(R.drawable.icon_flag_silver));
        }else{
            //gold flag
            flag.setImageDrawable(getDrawable(R.drawable.icon_flag_gold));
        }

        //Handle normal stat text
        name.setText(weaponStats.weapon.name);

        String numGames = weaponStats.numGames +"/"+(weaponStats.wins+weaponStats.losses);
        number.setText(numGames);

        winText.setText(String.valueOf(weaponStats.wins));
        lossText.setText(String.valueOf(weaponStats.losses));
        inked.setText(String.valueOf(weaponStats.totalPaintPoint));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String last = format.format(weaponStats.lastUsed*1000);
        lastUsed.setText(last);

        //Handle images
        ImageHandler imageHandler = new ImageHandler();

        String dirName = weaponStats.weapon.name.toLowerCase().replaceAll(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.url;

        if(imageHandler.imageExists("weapon",dirName,getApplicationContext())){
            weapon.setImageBitmap(imageHandler.loadImage("weapon",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(weapon);
            imageHandler.downloadImage("weapon",dirName,url,getApplicationContext());
        }

        dirName = weaponStats.weapon.sub.name.toLowerCase().replaceAll(" ","_");
        url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.sub.url;

        if(imageHandler.imageExists("sub",dirName,getApplicationContext())){
            sub.setImageBitmap(imageHandler.loadImage("sub",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(sub);
            imageHandler.downloadImage("sub",dirName,url,getApplicationContext());
        }

        dirName = weaponStats.weapon.special.name.toLowerCase().replaceAll(" ","_");
        url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.special.url;

        if(imageHandler.imageExists("special",dirName,getApplicationContext())){
            special.setImageBitmap(imageHandler.loadImage("special",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(special);
            imageHandler.downloadImage("special",dirName,url,getApplicationContext());
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

        RelativeLayout winLossMeter = (RelativeLayout) findViewById(R.id.WinLossMeter);
        RelativeLayout wins = (RelativeLayout) findViewById(R.id.Wins);
        RelativeLayout losses = (RelativeLayout) findViewById(R.id.Losses);

        winLossMeter.setClipToOutline(true);

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


        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();

        int meterWidth = winLossMeter.getWidth();
        float total = weaponStats.wins+weaponStats.losses;
        float width = weaponStats.wins/total;

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = weaponStats.losses/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);

        //Ink card

        inkTitle.setTypeface(fontTitle);
        inkMinimum.setTypeface(font);
        inkLowerQuartile.setTypeface(font);
        inkMedian.setTypeface(font);
        inkUpperQuartile.setTypeface(font);
        inkMaximum.setTypeface(font);

        inkMinimum.setText(String.valueOf(weaponStats.inkStats[0]));
        inkLowerQuartile.setText(String.valueOf(weaponStats.inkStats[1]));
        inkMedian.setText(String.valueOf(weaponStats.inkStats[2]));
        inkUpperQuartile.setText(String.valueOf(weaponStats.inkStats[3]));
        inkMaximum.setText(String.valueOf(weaponStats.inkStats[4]));

        if(weaponStats.inkStats[1]==weaponStats.inkStats[0]){
            inkLowerQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.inkStats[2]==weaponStats.inkStats[1]){
            inkMedian.setVisibility(View.GONE);
        }
        if(weaponStats.inkStats[3]==weaponStats.inkStats[2]){
            inkUpperQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.inkStats[4]==weaponStats.inkStats[3]){
            inkMaximum.setVisibility(View.GONE);
        }

        float range = weaponStats.inkStats[4] - weaponStats.inkStats[0];
        meterWidth = inkMeter.getMeasuredWidth();

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) inkMedian.getLayoutParams();

        if(range!=0) {
            //Need to position the inkMedian TextView so as to line it up with the center line
            width = (((weaponStats.inkStats[2] - weaponStats.inkStats[1])/range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkMedian.setLayoutParams(marginLayoutParams);

            layoutParams = inkLowerWhisker.getLayoutParams();
            width = ((weaponStats.inkStats[1] - weaponStats.inkStats[0])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = inkBox.getLayoutParams();
            width = ((weaponStats.inkStats[3] - weaponStats.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkBox.setLayoutParams(layoutParams);

            layoutParams = inkLowerBox.getLayoutParams();
            width = ((weaponStats.inkStats[2] - weaponStats.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperBox.getLayoutParams();
            width = ((weaponStats.inkStats[3] - weaponStats.inkStats[2])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkUpperBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperWhisker.getLayoutParams();
            width = ((weaponStats.inkStats[4] - weaponStats.inkStats[3])/range) * (270);
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

        killMinimum.setText(String.valueOf(weaponStats.killStats[0]));
        killLowerQuartile.setText(String.valueOf(weaponStats.killStats[1]));
        killMedian.setText(String.valueOf(weaponStats.killStats[2]));
        killUpperQuartile.setText(String.valueOf(weaponStats.killStats[3]));
        killMaximum.setText(String.valueOf(weaponStats.killStats[4]));

        if(weaponStats.killStats[1]==weaponStats.killStats[0]){
            killLowerQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.killStats[2]==weaponStats.killStats[1]){
            killMedian.setVisibility(View.GONE);
        }
        if(weaponStats.killStats[3]==weaponStats.killStats[2]){
            killUpperQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.killStats[4]==weaponStats.killStats[3]){
            killMaximum.setVisibility(View.GONE);
        }

        range = weaponStats.killStats[4] - weaponStats.killStats[0];
        layoutParams = killMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) killMedian.getLayoutParams();
            width = (((weaponStats.killStats[2] - weaponStats.killStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killMedian.setLayoutParams(marginLayoutParams);

            layoutParams = killLowerWhisker.getLayoutParams();
            width = ((weaponStats.killStats[1] - weaponStats.killStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = killBox.getLayoutParams();
            width = ((weaponStats.killStats[3] - weaponStats.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killBox.setLayoutParams(layoutParams);

            layoutParams = killLowerBox.getLayoutParams();
            width = ((weaponStats.killStats[2] - weaponStats.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerBox.setLayoutParams(layoutParams);

            layoutParams = killUpperBox.getLayoutParams();
            width = ((weaponStats.killStats[3] - weaponStats.killStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killUpperBox.setLayoutParams(layoutParams);

            layoutParams = killUpperWhisker.getLayoutParams();
            width = ((weaponStats.killStats[4] - weaponStats.killStats[3]) / range) * (270);
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

        deathMinimum.setText(String.valueOf(weaponStats.deathStats[0]));
        deathLowerQuartile.setText(String.valueOf(weaponStats.deathStats[1]));
        deathMedian.setText(String.valueOf(weaponStats.deathStats[2]));
        deathUpperQuartile.setText(String.valueOf(weaponStats.deathStats[3]));
        deathMaximum.setText(String.valueOf(weaponStats.deathStats[4]));

        if(weaponStats.deathStats[1]==weaponStats.deathStats[0]){
            deathLowerQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.deathStats[2]==weaponStats.deathStats[1]){
            deathMedian.setVisibility(View.GONE);
        }
        if(weaponStats.deathStats[3]==weaponStats.deathStats[2]){
            deathUpperQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.deathStats[4]==weaponStats.deathStats[3]){
            deathMaximum.setVisibility(View.GONE);
        }

        range = weaponStats.deathStats[4] - weaponStats.deathStats[0];
        layoutParams = deathMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) deathMedian.getLayoutParams();
            width = (((weaponStats.deathStats[2] - weaponStats.deathStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathMedian.setLayoutParams(marginLayoutParams);

            layoutParams = deathLowerWhisker.getLayoutParams();
            width = ((weaponStats.deathStats[1] - weaponStats.deathStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = deathBox.getLayoutParams();
            width = ((weaponStats.deathStats[3] - weaponStats.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathBox.setLayoutParams(layoutParams);

            layoutParams = deathLowerBox.getLayoutParams();
            width = ((weaponStats.deathStats[2] - weaponStats.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperBox.getLayoutParams();
            width = ((weaponStats.deathStats[3] - weaponStats.deathStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathUpperBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperWhisker.getLayoutParams();
            width = ((weaponStats.deathStats[4] - weaponStats.deathStats[3]) / range) * (270);
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

        specialMinimum.setText(String.valueOf(weaponStats.specialStats[0]));
        specialLowerQuartile.setText(String.valueOf(weaponStats.specialStats[1]));
        specialMedian.setText(String.valueOf(weaponStats.specialStats[2]));
        specialUpperQuartile.setText(String.valueOf(weaponStats.specialStats[3]));
        specialMaximum.setText(String.valueOf(weaponStats.specialStats[4]));

        if(weaponStats.specialStats[1]==weaponStats.specialStats[0]){
            specialLowerQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.specialStats[2]==weaponStats.specialStats[1]){
            specialMedian.setVisibility(View.GONE);
        }
        if(weaponStats.specialStats[3]==weaponStats.specialStats[2]){
            specialUpperQuartile.setVisibility(View.GONE);
        }
        if(weaponStats.specialStats[4]==weaponStats.specialStats[3]){
            specialMaximum.setVisibility(View.GONE);
        }

        range = weaponStats.specialStats[4] - weaponStats.specialStats[0];
        layoutParams = specialMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) specialMedian.getLayoutParams();
            width = (((weaponStats.specialStats[2] - weaponStats.specialStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialMedian.setLayoutParams(marginLayoutParams);

            layoutParams = specialLowerWhisker.getLayoutParams();
            width = ((weaponStats.specialStats[1] - weaponStats.specialStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = specialBox.getLayoutParams();
            width = ((weaponStats.specialStats[3] - weaponStats.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialBox.setLayoutParams(layoutParams);

            layoutParams = specialLowerBox.getLayoutParams();
            width = ((weaponStats.specialStats[2] - weaponStats.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperBox.getLayoutParams();
            width = ((weaponStats.specialStats[3] - weaponStats.specialStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialUpperBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperWhisker.getLayoutParams();
            width = ((weaponStats.specialStats[4] - weaponStats.specialStats[3]) / range) * (270);
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
