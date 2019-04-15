package com.mattrubacky.monet2.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.SalmonJobAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.CoopResult;
import com.mattrubacky.monet2.data.deserialized_entities.RewardGear;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/6/2018.
 */

public class SalmonRunShiftDetail extends AppCompatActivity {
    RelativeLayout jobCard,spots;
    RecyclerView jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_results);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        SplatnetSQLManager database = new SplatnetSQLManager(this);

        SalmonRunDetail shift = bundle.getParcelable("shift");
        RewardGear gear = database.getRewardGear(shift.start);
        ArrayList<CoopResult> results  = database.getJobs(shift.start);

        ImageView stageImage = findViewById(R.id.StageImage);

        ImageView weapon1Image = findViewById(R.id.Weapon1);
        ImageView weapon2Image = findViewById(R.id.Weapon2);
        ImageView weapon3Image = findViewById(R.id.Weapon3);
        ImageView weapon4Image = findViewById(R.id.Weapon4);

        jobs = findViewById(R.id.Jobs);

        spots = findViewById(R.id.spots);
        jobCard = findViewById(R.id.jobCard);
        jobCard.setClipToOutline(true);

        TextView stageName = findViewById(R.id.StageName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE M/d");
        String startText = sdf.format(shift.start*1000);
        String endText = sdf.format(shift.end*1000);
        title.setText(startText +"-"+endText);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        stageName.setTypeface(font);
        stageName.setText(shift.stage.name);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(jobs);

        SalmonJobAdapter jobAdapter = new SalmonJobAdapter(this,results,gear);

        jobs.setAdapter(jobAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        jobs.setLayoutManager(linearLayoutManager);

        ImageHandler imageHandler = new ImageHandler();
        String imageDirName = shift.stage.name.toLowerCase().replace(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+shift.stage.url;

        if(imageHandler.imageExists("salmon_stage",imageDirName,this)){
            stageImage.setImageBitmap(imageHandler.loadImage("salmon_stage",imageDirName));
        }else{
            Picasso.with(this).load(url).resize(1280,720).into(stageImage);
            imageHandler.downloadImage("salmon_stage",imageDirName,url,this);
        }

        if(shift.weapons.get(0).id>=0){
            imageDirName = shift.weapons.get(0).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(0).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,this)){
                weapon1Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(this).load(url).into(weapon1Image);
                imageHandler.downloadImage("weapon",imageDirName,url,this);
            }
        }else if(shift.weapons.get(0).id==-1){
            weapon1Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(0).id==-2){
            weapon1Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(1).id>=0){
            imageDirName = shift.weapons.get(1).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(1).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,this)){
                weapon2Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(this).load(url).into(weapon2Image);
                imageHandler.downloadImage("weapon",imageDirName,url,this);
            }
        }else if(shift.weapons.get(1).id==-1){
            weapon2Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(1).id==-2){
            weapon2Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(2).id>=0){
            imageDirName = shift.weapons.get(2).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(2).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,this)){
                weapon3Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(this).load(url).into(weapon3Image);
                imageHandler.downloadImage("weapon",imageDirName,url,this);
            }
        }else if(shift.weapons.get(2).id==-1){
            weapon3Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(2).id==-2){
            weapon3Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        if(shift.weapons.get(3).id>=0){
            imageDirName = shift.weapons.get(3).weapon.name.toLowerCase().replace(" ","_");
            url = "https://app.splatoon2.nintendo.net"+shift.weapons.get(3).weapon.url;

            if(imageHandler.imageExists("weapon",imageDirName,this)){
                weapon4Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(this).load(url).into(weapon4Image);
                imageHandler.downloadImage("weapon",imageDirName,url,this);
            }
        }else if(shift.weapons.get(3).id==-1){
            weapon4Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery));
        }else if(shift.weapons.get(3).id==-2){
            weapon4Image.setImageDrawable(getResources().getDrawable(R.drawable.weapon_mystery_grizzco));
        }

        int height = getResources().getSystem().getDisplayMetrics().heightPixels;
        height -= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 84, getResources().getDisplayMetrics());
        spots.setMinimumHeight(height);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
