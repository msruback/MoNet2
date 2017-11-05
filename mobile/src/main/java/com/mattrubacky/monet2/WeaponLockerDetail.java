package com.mattrubacky.monet2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

        RelativeLayout winLossMeter = (RelativeLayout) findViewById(R.id.WinLossMeter);
        RelativeLayout wins = (RelativeLayout) findViewById(R.id.Wins);
        RelativeLayout losses = (RelativeLayout) findViewById(R.id.Losses);

        winLossMeter.setClipToOutline(true);

        RelativeLayout inkMeter = (RelativeLayout) findViewById(R.id.InkMeter);
        RelativeLayout inkLowerWhisker = (RelativeLayout) findViewById(R.id.InkLowerWhisker);
        RelativeLayout inkBox = (RelativeLayout) findViewById(R.id.inkBox);
        RelativeLayout inkLowerBox = (RelativeLayout) findViewById(R.id.InkLowerBox);
        RelativeLayout inkUpperBox = (RelativeLayout) findViewById(R.id.InkUpperBox);
        RelativeLayout inkUpperWhisker = (RelativeLayout) findViewById(R.id.InkUpperWhisker);

        inkBox.setClipToOutline(true);

        RelativeLayout killMeter = (RelativeLayout) findViewById(R.id.KillMeter);
        RelativeLayout killLowerWhisker = (RelativeLayout) findViewById(R.id.KillLowerWhisker);
        RelativeLayout killBox = (RelativeLayout) findViewById(R.id.killBox);
        RelativeLayout killLowerBox = (RelativeLayout) findViewById(R.id.KillLowerBox);
        RelativeLayout killUpperBox = (RelativeLayout) findViewById(R.id.KillUpperBox);
        RelativeLayout killUpperWhisker = (RelativeLayout) findViewById(R.id.KillUpperWhisker);

        killBox.setClipToOutline(true);

        RelativeLayout deathMeter = (RelativeLayout) findViewById(R.id.DeathMeter);
        RelativeLayout deathLowerWhisker = (RelativeLayout) findViewById(R.id.DeathLowerWhisker);
        RelativeLayout deathBox = (RelativeLayout) findViewById(R.id.deathBox);
        RelativeLayout deathLowerBox = (RelativeLayout) findViewById(R.id.DeathLowerBox);
        RelativeLayout deathUpperBox = (RelativeLayout) findViewById(R.id.DeathUpperBox);
        RelativeLayout deathUpperWhisker = (RelativeLayout) findViewById(R.id.DeathUpperWhisker);

        deathBox.setClipToOutline(true);

        RelativeLayout specialMeter = (RelativeLayout) findViewById(R.id.SpecialMeter);
        RelativeLayout specialLowerWhisker = (RelativeLayout) findViewById(R.id.SpecialLowerWhisker);
        RelativeLayout specialBox = (RelativeLayout) findViewById(R.id.specialBox);
        RelativeLayout specialLowerBox = (RelativeLayout) findViewById(R.id.SpecialLowerBox);
        RelativeLayout specialUpperBox = (RelativeLayout) findViewById(R.id.SpecialUpperBox);
        RelativeLayout specialUpperWhisker = (RelativeLayout) findViewById(R.id.SpecialUpperWhisker);

        specialBox.setClipToOutline(true);

        ImageView flag = (ImageView) findViewById(R.id.Flag);
        ImageView weapon = (ImageView) findViewById(R.id.Weapon);
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

        //Handle Win Loss Meter

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();

        int meterWidth = winLossMeter.getLayoutParams().width;
        int total = weaponStats.wins+weaponStats.losses;
        int width = weaponStats.wins/total;

        width *= meterWidth;
        layoutParams.width = width;
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = weaponStats.losses/total;
        width *= meterWidth;
        layoutParams.width = width;
        losses.setLayoutParams(layoutParams);

        //Ink card

        inkTitle.setTypeface(fontTitle);
        inkMinimum.setTypeface(font);
        inkLowerQuartile.setTypeface(font);
        inkMedian.setTypeface(font);
        inkUpperQuartile.setTypeface(font);
        inkMaximum.setTypeface(font);

        inkMinimum.setText(weaponStats.inkStats[0]);
        inkLowerQuartile.setText(weaponStats.inkStats[1]);
        inkMedian.setText(weaponStats.inkStats[2]);
        inkUpperQuartile.setText(weaponStats.inkStats[3]);
        inkMaximum.setText(weaponStats.inkStats[4]);

        int range = weaponStats.inkStats[4] - weaponStats.inkStats[0];
        layoutParams = inkMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        //Need to position the inkMedian TextView so as to line it up with the center line
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) inkMedian.getLayoutParams();
        marginLayoutParams.leftMargin=((weaponStats.inkStats[2]-weaponStats.inkStats[1])/range)*(meterWidth/range);
        inkMedian.setLayoutParams(marginLayoutParams);

        layoutParams = inkLowerWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.inkStats[1]-weaponStats.inkStats[0])/range)*(meterWidth/range);
        inkLowerWhisker.setLayoutParams(layoutParams);

        layoutParams = inkBox.getLayoutParams();
        layoutParams.width = ((weaponStats.inkStats[3]-weaponStats.inkStats[1])/range)*(meterWidth/range);
        inkBox.setLayoutParams(layoutParams);

        layoutParams = inkLowerBox.getLayoutParams();
        layoutParams.width = ((weaponStats.inkStats[2]-weaponStats.inkStats[1])/range)*(meterWidth/range);
        inkLowerBox.setLayoutParams(layoutParams);

        layoutParams = inkUpperBox.getLayoutParams();
        layoutParams.width = ((weaponStats.inkStats[3]-weaponStats.inkStats[2])/range)*(meterWidth/range);
        inkUpperBox.setLayoutParams(layoutParams);

        layoutParams = inkUpperWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.inkStats[4]-weaponStats.inkStats[3])/range)*(meterWidth/range);
        inkUpperWhisker.setLayoutParams(layoutParams);

        //Kill card

        killTitle.setTypeface(fontTitle);
        killMinimum.setTypeface(font);
        killLowerQuartile.setTypeface(font);
        killMedian.setTypeface(font);
        killUpperQuartile.setTypeface(font);
        killMaximum.setTypeface(font);

        killMinimum.setText(weaponStats.killStats[0]);
        killLowerQuartile.setText(weaponStats.killStats[1]);
        killMedian.setText(weaponStats.killStats[2]);
        killUpperQuartile.setText(weaponStats.killStats[3]);
        killMaximum.setText(weaponStats.killStats[4]);

        range = weaponStats.killStats[4] - weaponStats.killStats[0];
        layoutParams = killMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        marginLayoutParams = (ViewGroup.MarginLayoutParams) killMedian.getLayoutParams();
        marginLayoutParams.leftMargin = ((weaponStats.killStats[2]-weaponStats.killStats[3])/range)*(meterWidth/range);
        killMedian.setLayoutParams(marginLayoutParams);

        layoutParams = killLowerWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.killStats[1]-weaponStats.killStats[0])/range)*(meterWidth/range);
        killLowerWhisker.setLayoutParams(layoutParams);

        layoutParams = killBox.getLayoutParams();
        layoutParams.width = ((weaponStats.killStats[3]-weaponStats.killStats[1])/range)*(meterWidth/range);
        killBox.setLayoutParams(layoutParams);

        layoutParams = killLowerBox.getLayoutParams();
        layoutParams.width = ((weaponStats.killStats[2]-weaponStats.killStats[1])/range)*(meterWidth/range);
        killLowerBox.setLayoutParams(layoutParams);

        layoutParams = killUpperBox.getLayoutParams();
        layoutParams.width = ((weaponStats.killStats[3]-weaponStats.killStats[2])/range)*(meterWidth/range);
        killUpperBox.setLayoutParams(layoutParams);

        layoutParams = killUpperWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.killStats[4]-weaponStats.killStats[3])/range)*(meterWidth/range);
        killUpperWhisker.setLayoutParams(layoutParams);

        //Death Card

        deathTitle.setTypeface(fontTitle);
        deathMinimum.setTypeface(font);
        deathLowerQuartile.setTypeface(font);
        deathMedian.setTypeface(font);
        deathUpperQuartile.setTypeface(font);
        deathMaximum.setTypeface(font);

        deathMinimum.setText(weaponStats.deathStats[0]);
        deathLowerQuartile.setText(weaponStats.deathStats[1]);
        deathMedian.setText(weaponStats.deathStats[2]);
        deathUpperQuartile.setText(weaponStats.deathStats[3]);
        deathMaximum.setText(weaponStats.deathStats[4]);

        range = weaponStats.deathStats[4] - weaponStats.deathStats[0];
        layoutParams = deathMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        marginLayoutParams = (ViewGroup.MarginLayoutParams) deathMedian.getLayoutParams();
        marginLayoutParams.leftMargin = ((weaponStats.deathStats[2]-weaponStats.deathStats[3])/range)*(meterWidth/range);
        deathMedian.setLayoutParams(marginLayoutParams);

        layoutParams = deathLowerWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.deathStats[1]-weaponStats.deathStats[0])/range)*(meterWidth/range);
        deathLowerWhisker.setLayoutParams(layoutParams);

        layoutParams = deathBox.getLayoutParams();
        layoutParams.width = ((weaponStats.deathStats[3]-weaponStats.deathStats[1])/range)*(meterWidth/range);
        deathBox.setLayoutParams(layoutParams);

        layoutParams = deathLowerBox.getLayoutParams();
        layoutParams.width = ((weaponStats.deathStats[2]-weaponStats.deathStats[1])/range)*(meterWidth/range);
        deathLowerBox.setLayoutParams(layoutParams);

        layoutParams = deathUpperBox.getLayoutParams();
        layoutParams.width = ((weaponStats.deathStats[3]-weaponStats.deathStats[2])/range)*(meterWidth/range);
        deathUpperBox.setLayoutParams(layoutParams);

        layoutParams = deathUpperWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.deathStats[4]-weaponStats.deathStats[3])/range)*(meterWidth/range);
        deathUpperWhisker.setLayoutParams(layoutParams);

        //Special Card

        specialTitle.setTypeface(fontTitle);
        specialMinimum.setTypeface(font);
        specialLowerQuartile.setTypeface(font);
        specialMedian.setTypeface(font);
        specialUpperQuartile.setTypeface(font);
        specialMaximum.setTypeface(font);

        specialMinimum.setText(weaponStats.specialStats[0]);
        specialLowerQuartile.setText(weaponStats.specialStats[1]);
        specialMedian.setText(weaponStats.specialStats[2]);
        specialUpperQuartile.setText(weaponStats.specialStats[3]);
        specialMaximum.setText(weaponStats.specialStats[4]);

        range = weaponStats.specialStats[4] - weaponStats.specialStats[0];
        layoutParams = specialMeter.getLayoutParams();
        meterWidth = layoutParams.width;

        marginLayoutParams = (ViewGroup.MarginLayoutParams) specialMedian.getLayoutParams();
        marginLayoutParams.leftMargin = ((weaponStats.specialStats[2]-weaponStats.specialStats[3])/range)*(meterWidth/range);
        specialMedian.setLayoutParams(marginLayoutParams);

        layoutParams = specialLowerWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.specialStats[1]-weaponStats.specialStats[0])/range)*(meterWidth/range);
        specialLowerWhisker.setLayoutParams(layoutParams);

        layoutParams = specialBox.getLayoutParams();
        layoutParams.width = ((weaponStats.specialStats[3]-weaponStats.specialStats[1])/range)*(meterWidth/range);
        specialBox.setLayoutParams(layoutParams);

        layoutParams = specialLowerBox.getLayoutParams();
        layoutParams.width = ((weaponStats.specialStats[2]-weaponStats.specialStats[1])/range)*(meterWidth/range);
        specialLowerBox.setLayoutParams(layoutParams);

        layoutParams = specialUpperBox.getLayoutParams();
        layoutParams.width = ((weaponStats.specialStats[3]-weaponStats.specialStats[2])/range)*(meterWidth/range);
        specialUpperBox.setLayoutParams(layoutParams);

        layoutParams = specialUpperWhisker.getLayoutParams();
        layoutParams.width = ((weaponStats.specialStats[4]-weaponStats.specialStats[3])/range)*(meterWidth/range);
        specialUpperWhisker.setLayoutParams(layoutParams);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("fragment",4);
            intent.putExtra("stats",1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
