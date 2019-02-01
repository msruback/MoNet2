package com.mattrubacky.monet2;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.fragment.SplatfestDetail.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ClosetDetail extends AppCompatActivity {

    ClosetHanger hanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_detail);

        Bundle bundle = getIntent().getExtras();

        hanger = bundle.getParcelable("stats");

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RelativeLayout nameLayout = findViewById(R.id.nameLayout);
        RelativeLayout moreInfoLayout = findViewById(R.id.moreInfo);

        ImageView editGear = findViewById(R.id.EditGear);
        ImageView brand = findViewById(R.id.Brand);
        ImageView gear = findViewById(R.id.GearImage);
        ImageView main = findViewById(R.id.Main);
        ImageView sub1 = findViewById(R.id.Sub1);
        ImageView sub2 = findViewById(R.id.Sub2);
        ImageView sub3 = findViewById(R.id.Sub3);

        TextView name = findViewById(R.id.Name);
        TextView number = findViewById(R.id.Number);
        TextView winText = findViewById(R.id.WinText);
        TextView lossText = findViewById(R.id.LossText);
        TextView inkedTitle = findViewById(R.id.InkedTitleText);
        TextView inkedText = findViewById(R.id.InkedText);
        TextView lastUsedTitle = findViewById(R.id.LastTitleText);
        TextView lastText = findViewById(R.id.LastText);

        name.setTypeface(fontTitle);
        number.setTypeface(font);
        winText.setTypeface(font);
        lossText.setTypeface(font);
        inkedTitle.setTypeface(fontTitle);
        inkedText.setTypeface(font);
        lastUsedTitle.setTypeface(fontTitle);
        lastText.setTypeface(font);

        name.setText(hanger.gear.name);
        number.setText(String.valueOf(hanger.numGames));
        winText.setText(String.valueOf(hanger.wins));
        lossText.setText(String.valueOf(hanger.losses));
        inkedText.setText(String.valueOf(hanger.inked));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String last = format.format((hanger.time*1000));
        lastText.setText(last);

        //Handle Colors
        switch(hanger.gear.kind){
            case "head":
                nameLayout.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                moreInfoLayout.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                nameLayout.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                moreInfoLayout.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                nameLayout.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                moreInfoLayout.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                break;
        }

        //Handle Brand
        ImageHandler imageHandler = new ImageHandler();

        String dirName = hanger.gear.brand.name.toLowerCase().replaceAll(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+hanger.gear.brand.url;
        if(imageHandler.imageExists("brand",dirName,getApplicationContext())){
            brand.setImageBitmap(imageHandler.loadImage("brand",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(brand);
            imageHandler.downloadImage("brand",dirName,url,getApplicationContext());
        }

        //Handle Subs

        dirName = hanger.skills.main.name.toLowerCase().replaceAll(" ","_");
        url = "https://app.splatoon2.nintendo.net"+hanger.skills.main.url;

        if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
            main.setImageBitmap(imageHandler.loadImage("ability",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(main);
            imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
        }

        if(hanger.skills.subs.size()>0&&hanger.skills.subs.get(0)!=null){
            dirName = hanger.skills.subs.get(0).name.toLowerCase().replaceAll(" ","_");
            url = "https://app.splatoon2.nintendo.net"+hanger.skills.subs.get(0).url;

            if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
                sub1.setImageBitmap(imageHandler.loadImage("ability",dirName));
            }else{
                Picasso.with(getApplicationContext()).load(url).into(sub1);
                imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
            }
            if(hanger.skills.subs.size()>1&&hanger.skills.subs.get(1)!=null){
                dirName = hanger.skills.subs.get(1).name.toLowerCase().replaceAll(" ","_");
                url = "https://app.splatoon2.nintendo.net"+hanger.skills.subs.get(1).url;

                if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
                    sub2.setImageBitmap(imageHandler.loadImage("ability",dirName));
                }else{
                    Picasso.with(getApplicationContext()).load(url).into(sub2);
                    imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
                }
                if(hanger.skills.subs.size()>2&&hanger.skills.subs.get(2)!=null){
                    dirName = hanger.skills.subs.get(2).name.toLowerCase().replaceAll(" ","_");
                    url = "https://app.splatoon2.nintendo.net"+hanger.skills.subs.get(2).url;

                    if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
                        sub3.setImageBitmap(imageHandler.loadImage("ability",dirName));
                    }else{
                        Picasso.with(getApplicationContext()).load(url).into(sub3);
                        imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
                    }
                }else{
                    sub3.setVisibility(View.GONE);
                }
            }else{
                sub2.setVisibility(View.GONE);
                sub3.setVisibility(View.GONE);
            }
        }else{
            sub1.setVisibility(View.GONE);
            sub2.setVisibility(View.GONE);
            sub3.setVisibility(View.GONE);
        }

        //Handle gear image
        imageHandler = new ImageHandler();

        dirName = hanger.gear.name.toLowerCase().replaceAll(" ","_");
        url = "https://app.splatoon2.nintendo.net"+hanger.gear.url;

        if(imageHandler.imageExists("gear",dirName,getApplicationContext())){
            gear.setImageBitmap(imageHandler.loadImage("gear",dirName));
        }else{
            Picasso.with(getApplicationContext()).load(url).into(gear);
            imageHandler.downloadImage("gear",dirName,url,getApplicationContext());
        }



        editGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClosetDetail.this,AddGear.class);
                Bundle bundle = new Bundle();
                bundle.putString("type","edit");
                bundle.putParcelable("hanger",hanger);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

        RelativeLayout generalStats = findViewById(R.id.generalStats);
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

        //Handle Colors
        switch(hanger.gear.kind){
            case "head":
                generalStats.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                noStatsCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                generalStats.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                noStatsCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                generalStats.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                noStatsCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                break;
        }

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();
        float total = hanger.wins+hanger.losses;
        float width = hanger.wins/total;

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = hanger.losses/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);

        boolean hasStats = false;

        //Ink card
        float range = hanger.inkStats[4] - hanger.inkStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",hanger.inkStats);
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
        range = hanger.killStats[4] - hanger.killStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",hanger.killStats);
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

        range = hanger.deathStats[4] - hanger.deathStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",hanger.deathStats);
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

        range = hanger.specialStats[4] - hanger.specialStats[0];
        if(range!=0) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("stats",hanger.specialStats);
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

