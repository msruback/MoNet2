package com.mattrubacky.monet2;

import android.provider.ContactsContract;
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
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class ClosetDetail extends AppCompatActivity {

    ClosetHanger hanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_stats);

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


        ImageView gear = (ImageView) findViewById(R.id.GearImage);
        ImageView main = (ImageView) findViewById(R.id.Main);
        ImageView sub1 = (ImageView) findViewById(R.id.Sub1);
        ImageView sub2 = (ImageView) findViewById(R.id.Sub2);
        ImageView sub3 = (ImageView) findViewById(R.id.Sub3);

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

        //Handle Brand

        //Handle Subs
        ImageHandler imageHandler = new ImageHandler();

        String dirName = hanger.skills.main.name.toLowerCase().replaceAll(" ","_");
        String url = "https://app.splatoon2.nintendo.net"+hanger.skills.main.url;

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


        ViewGroup.LayoutParams layoutParams;


        //Ink card

        inkTitle.setTypeface(fontTitle);
        inkMinimum.setTypeface(font);
        inkLowerQuartile.setTypeface(font);
        inkMedian.setTypeface(font);
        inkUpperQuartile.setTypeface(font);
        inkMaximum.setTypeface(font);

        inkMinimum.setText(String.valueOf(hanger.inkStats[0]));
        inkLowerQuartile.setText(String.valueOf(hanger.inkStats[1]));
        inkMedian.setText(String.valueOf(hanger.inkStats[2]));
        inkUpperQuartile.setText(String.valueOf(hanger.inkStats[3]));
        inkMaximum.setText(String.valueOf(hanger.inkStats[4]));

        if(hanger.inkStats[1]==hanger.inkStats[0]){
            inkLowerQuartile.setVisibility(View.GONE);
        }
        if(hanger.inkStats[2]==hanger.inkStats[1]){
            inkMedian.setVisibility(View.GONE);
        }
        if(hanger.inkStats[3]==hanger.inkStats[2]){
            inkUpperQuartile.setVisibility(View.GONE);
        }
        if(hanger.inkStats[4]==hanger.inkStats[3]){
            inkMaximum.setVisibility(View.GONE);
        }

        float range = hanger.inkStats[4] - hanger.inkStats[0];
        float width;

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) inkMedian.getLayoutParams();

        if(range!=0) {
            //Need to position the inkMedian TextView so as to line it up with the center line
            width = (((hanger.inkStats[2] - hanger.inkStats[1])/range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkMedian.setLayoutParams(marginLayoutParams);

            layoutParams = inkLowerWhisker.getLayoutParams();
            width = ((hanger.inkStats[1] - hanger.inkStats[0])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = inkBox.getLayoutParams();
            width = ((hanger.inkStats[3] - hanger.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkBox.setLayoutParams(layoutParams);

            layoutParams = inkLowerBox.getLayoutParams();
            width = ((hanger.inkStats[2] - hanger.inkStats[1])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkLowerBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperBox.getLayoutParams();
            width = ((hanger.inkStats[3] - hanger.inkStats[2])/range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            inkUpperBox.setLayoutParams(layoutParams);

            layoutParams = inkUpperWhisker.getLayoutParams();
            width = ((hanger.inkStats[4] - hanger.inkStats[3])/range) * (270);
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

        killMinimum.setText(String.valueOf(hanger.killStats[0]));
        killLowerQuartile.setText(String.valueOf(hanger.killStats[1]));
        killMedian.setText(String.valueOf(hanger.killStats[2]));
        killUpperQuartile.setText(String.valueOf(hanger.killStats[3]));
        killMaximum.setText(String.valueOf(hanger.killStats[4]));

        if(hanger.killStats[1]==hanger.killStats[0]){
            killLowerQuartile.setVisibility(View.GONE);
        }
        if(hanger.killStats[2]==hanger.killStats[1]){
            killMedian.setVisibility(View.GONE);
        }
        if(hanger.killStats[3]==hanger.killStats[2]){
            killUpperQuartile.setVisibility(View.GONE);
        }
        if(hanger.killStats[4]==hanger.killStats[3]){
            killMaximum.setVisibility(View.GONE);
        }

        range = hanger.killStats[4] - hanger.killStats[0];
        layoutParams = killMeter.getLayoutParams();

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) killMedian.getLayoutParams();
            width = (((hanger.killStats[2] - hanger.killStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killMedian.setLayoutParams(marginLayoutParams);

            layoutParams = killLowerWhisker.getLayoutParams();
            width = ((hanger.killStats[1] - hanger.killStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = killBox.getLayoutParams();
            width = ((hanger.killStats[3] - hanger.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killBox.setLayoutParams(layoutParams);

            layoutParams = killLowerBox.getLayoutParams();
            width = ((hanger.killStats[2] - hanger.killStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killLowerBox.setLayoutParams(layoutParams);

            layoutParams = killUpperBox.getLayoutParams();
            width = ((hanger.killStats[3] - hanger.killStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            killUpperBox.setLayoutParams(layoutParams);

            layoutParams = killUpperWhisker.getLayoutParams();
            width = ((hanger.killStats[4] - hanger.killStats[3]) / range) * (270);
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

        deathMinimum.setText(String.valueOf(hanger.deathStats[0]));
        deathLowerQuartile.setText(String.valueOf(hanger.deathStats[1]));
        deathMedian.setText(String.valueOf(hanger.deathStats[2]));
        deathUpperQuartile.setText(String.valueOf(hanger.deathStats[3]));
        deathMaximum.setText(String.valueOf(hanger.deathStats[4]));

        if(hanger.deathStats[1]==hanger.deathStats[0]){
            deathLowerQuartile.setVisibility(View.GONE);
        }
        if(hanger.deathStats[2]==hanger.deathStats[1]){
            deathMedian.setVisibility(View.GONE);
        }
        if(hanger.deathStats[3]==hanger.deathStats[2]){
            deathUpperQuartile.setVisibility(View.GONE);
        }
        if(hanger.deathStats[4]==hanger.deathStats[3]){
            deathMaximum.setVisibility(View.GONE);
        }

        range = hanger.deathStats[4] - hanger.deathStats[0];
        layoutParams = deathMeter.getLayoutParams();

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) deathMedian.getLayoutParams();
            width = (((hanger.deathStats[2] - hanger.deathStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathMedian.setLayoutParams(marginLayoutParams);

            layoutParams = deathLowerWhisker.getLayoutParams();
            width = ((hanger.deathStats[1] - hanger.deathStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = deathBox.getLayoutParams();
            width = ((hanger.deathStats[3] - hanger.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathBox.setLayoutParams(layoutParams);

            layoutParams = deathLowerBox.getLayoutParams();
            width = ((hanger.deathStats[2] - hanger.deathStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathLowerBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperBox.getLayoutParams();
            width = ((hanger.deathStats[3] - hanger.deathStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            deathUpperBox.setLayoutParams(layoutParams);

            layoutParams = deathUpperWhisker.getLayoutParams();
            width = ((hanger.deathStats[4] - hanger.deathStats[3]) / range) * (270);
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

        specialMinimum.setText(String.valueOf(hanger.specialStats[0]));
        specialLowerQuartile.setText(String.valueOf(hanger.specialStats[1]));
        specialMedian.setText(String.valueOf(hanger.specialStats[2]));
        specialUpperQuartile.setText(String.valueOf(hanger.specialStats[3]));
        specialMaximum.setText(String.valueOf(hanger.specialStats[4]));

        if(hanger.specialStats[1]==hanger.specialStats[0]){
            specialLowerQuartile.setVisibility(View.GONE);
        }
        if(hanger.specialStats[2]==hanger.specialStats[1]){
            specialMedian.setVisibility(View.GONE);
        }
        if(hanger.specialStats[3]==hanger.specialStats[2]){
            specialUpperQuartile.setVisibility(View.GONE);
        }
        if(hanger.specialStats[4]==hanger.specialStats[3]){
            specialMaximum.setVisibility(View.GONE);
        }

        range = hanger.specialStats[4] - hanger.specialStats[0];
        layoutParams = specialMeter.getLayoutParams();

        if(range!=0) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) specialMedian.getLayoutParams();
            width = (((hanger.specialStats[2] - hanger.specialStats[1]) / range) * (270));
            marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialMedian.setLayoutParams(marginLayoutParams);

            layoutParams = specialLowerWhisker.getLayoutParams();
            width = ((hanger.specialStats[1] - hanger.specialStats[0]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerWhisker.setLayoutParams(layoutParams);

            layoutParams = specialBox.getLayoutParams();
            width = ((hanger.specialStats[3] - hanger.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialBox.setLayoutParams(layoutParams);

            layoutParams = specialLowerBox.getLayoutParams();
            width = ((hanger.specialStats[2] - hanger.specialStats[1]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialLowerBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperBox.getLayoutParams();
            width = ((hanger.specialStats[3] - hanger.specialStats[2]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialUpperBox.setLayoutParams(layoutParams);

            layoutParams = specialUpperWhisker.getLayoutParams();
            width = ((hanger.specialStats[4] - hanger.specialStats[3]) / range) * (270);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            specialUpperWhisker.setLayoutParams(layoutParams);
        }else{
            specialCard.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("fragment",2);
            intent.putExtra("stats",2);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}

