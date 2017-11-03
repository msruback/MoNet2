package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

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
        winText.setText(String.valueOf(weaponStats.wins));
        lossText.setText(String.valueOf(weaponStats.losses));
        inked.setText(String.valueOf(weaponStats.totalPaintPoint));

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

        int winLossWidth = winLossMeter.getLayoutParams().width;
        int total = weaponStats.wins+weaponStats.losses;
        int width = weaponStats.wins/total;

        width *= winLossWidth;
        layoutParams.width = width;
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = weaponStats.losses/total;
        width *= winLossWidth;
        layoutParams.width = width;
        losses.setLayoutParams(layoutParams);
        
    }
}
