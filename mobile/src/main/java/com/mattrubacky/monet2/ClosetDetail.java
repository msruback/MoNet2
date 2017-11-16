package com.mattrubacky.monet2;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.fragment.SoloMeterFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RelativeLayout nameLayout = (RelativeLayout) findViewById(R.id.nameLayout);
        RelativeLayout moreInfoLayout = (RelativeLayout) findViewById(R.id.moreInfo);

        ImageView editGear = (ImageView) findViewById(R.id.EditGear);
        ImageView brand = (ImageView) findViewById(R.id.Brand);
        ImageView gear = (ImageView) findViewById(R.id.GearImage);
        ImageView main = (ImageView) findViewById(R.id.Main);
        ImageView sub1 = (ImageView) findViewById(R.id.Sub1);
        ImageView sub2 = (ImageView) findViewById(R.id.Sub2);
        ImageView sub3 = (ImageView) findViewById(R.id.Sub3);

        TextView name = (TextView) findViewById(R.id.Name);
        TextView number = (TextView) findViewById(R.id.Number);

        name.setTypeface(fontTitle);
        number.setTypeface(font);

        name.setText(hanger.gear.name);
        number.setText(String.valueOf(hanger.numGames));

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

        ViewGroup.LayoutParams layoutParams;

        //Handle Colors
        switch(hanger.gear.kind){
            case "head":
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.clothes));;
                break;
            case "shoes":
                inkCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                killCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                deathCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                specialCard.setBackgroundTintList(getResources().getColorStateList(R.color.shoes));
                break;
        }


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

