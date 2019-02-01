package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.WeaponStats;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView flag = findViewById(R.id.Flag);
        ImageView weapon = findViewById(R.id.WeaponImage);
        ImageView sub = findViewById(R.id.Sub);
        ImageView special = findViewById(R.id.Special);

        TextView name = findViewById(R.id.Name);
        TextView number = findViewById(R.id.Number);
        TextView winText = findViewById(R.id.WinText);
        TextView lossText = findViewById(R.id.LossText);
        TextView inkedTitle = findViewById(R.id.InkedTitleText);
        TextView inked = findViewById(R.id.InkedText);
        TextView lastUsedTitle = findViewById(R.id.LastTitleText);
        TextView lastUsed = findViewById(R.id.LastText);

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

        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");
        FragmentManager fragmentManager = getSupportFragmentManager();

        RelativeLayout winLossMeter = findViewById(R.id.WinLossMeter);
        RelativeLayout wins = findViewById(R.id.Wins);
        RelativeLayout losses = findViewById(R.id.Losses);

        winLossMeter.setClipToOutline(true);

        RelativeLayout inkCard = findViewById(R.id.inkStats);
        RelativeLayout killCard = findViewById(R.id.killStats);
        RelativeLayout deathCard = findViewById(R.id.deathStats);
        RelativeLayout specialCard = findViewById(R.id.specialStats);
        RelativeLayout noStatsCard = findViewById(R.id.noStats);

        TextView inkTitle = findViewById(R.id.InkTitle);
        TextView killTitle = findViewById(R.id.KillTitle);
        TextView deathTitle = findViewById(R.id.DeathTitle);
        TextView specialTitle = findViewById(R.id.SpecialTitle);
        TextView noStatsText = findViewById(R.id.NoStatsText);

        inkTitle.setTypeface(fontTitle);
        killTitle.setTypeface(fontTitle);
        deathTitle.setTypeface(fontTitle);
        specialTitle.setTypeface(fontTitle);
        noStatsText.setTypeface(fontTitle);

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();

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

        boolean hasStats = false;

        //Ink card
        float range = weaponStats.inkStats[4] - weaponStats.inkStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",weaponStats.inkStats);
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
        range = weaponStats.killStats[4] - weaponStats.killStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",weaponStats.killStats);
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

        range = weaponStats.deathStats[4] - weaponStats.deathStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",weaponStats.deathStats);
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

        range = weaponStats.specialStats[4] - weaponStats.specialStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",weaponStats.specialStats);
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
