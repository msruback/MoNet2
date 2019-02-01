package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.StagePostcardsDetail;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StageStats;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 1/14/2018.
 */

public class SplatfestTimePeriodViewHolder extends RecyclerView.ViewHolder{

    public TextView time,title1,title2,title3;
    public ImageView image1,image2,image3;
    private Context context;

    public SplatfestTimePeriodViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_splatfest_rotation, parent, false));

        this.context = context;

        time = itemView.findViewById(R.id.fesTime);
        title1 = itemView.findViewById(R.id.fesStageName1);
        title2 = itemView.findViewById(R.id.fesStageName2);
        title3 = itemView.findViewById(R.id.fesStageName3);
        image1 = itemView.findViewById(R.id.fesStageImage1);
        image2 = itemView.findViewById(R.id.fesStageImage2);
        image3 = itemView.findViewById(R.id.fesStageImage3);
    }

    public void manageHolder(TimePeriod timePeriod, final Splatfest splatfest){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        time.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);
        title3.setTypeface(font);

        final Stage a = timePeriod.a;
        final Stage b = timePeriod.b;
        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        title1.setText(a.name);
        title2.setText(b.name);
        title3.setText(splatfest.stage.name);
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.url;
        String url2 = "https://app.splatoon2.nintendo.net"+b.url;
        String url3 = "https://app.splatoon2.nintendo.net"+splatfest.stage.url;

        ImageHandler imageHandler = new ImageHandler();
        String image1DirName = a.name.toLowerCase().replace(" ","_");
        String image2DirName = b.name.toLowerCase().replace(" ","_");
        String image3DirName = splatfest.stage.name.toLowerCase().replace(" ","_");

        if(imageHandler.imageExists("stage",image1DirName,context)){
            image1.setImageBitmap(imageHandler.loadImage("stage",image1DirName));
        }else{
            Picasso.with(context).load(url1).resize(1280,720).into(image1);
            imageHandler.downloadImage("stage",image1DirName,url1,context);
        }

        if(imageHandler.imageExists("stage",image2DirName,context)){
            image2.setImageBitmap(imageHandler.loadImage("stage",image2DirName));
        }else{
            Picasso.with(context).load(url2).resize(1280,720).into(image2);
            imageHandler.downloadImage("stage",image2DirName,url2,context);
        }

        if(imageHandler.imageExists("stage",image3DirName,context)){
            image3.setImageBitmap(imageHandler.loadImage("stage",image2DirName));
        }else{
            Picasso.with(context).load(url3).resize(1280,720).into(image3);
            imageHandler.downloadImage("stage",image2DirName,url2,context);
        }

        image1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context,StagePostcardsDetail.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                Record records = gson.fromJson(settings.getString("records",""),Record.class);

                StageStats stats;

                if(records.records.stageStats.containsKey(a.id)){
                    stats = records.records.stageStats.get(a.id);
                    stats.isSplatnet = true;
                }else{
                    stats = new StageStats();
                    stats.stage = a;
                    stats.isSplatnet = false;
                }

                if(stats!=null) {

                    stats.calcStats(context);

                    Bundle intentBundle = new Bundle();
                    intentBundle.putParcelable("stats", stats);
                    intent.putExtras(intentBundle);
                    context.startActivity(intent);
                }
                return false;
            }
        });

        image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context,StagePostcardsDetail.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                Record records = gson.fromJson(settings.getString("records",""),Record.class);

                StageStats stats;

                if(records.records.stageStats.containsKey(b.id)){
                    stats = records.records.stageStats.get(b.id);
                    stats.isSplatnet = true;
                }else{
                    stats = new StageStats();
                    stats.stage = b;
                    stats.isSplatnet = false;
                }


                if(stats!=null) {
                    stats.calcStats(context);

                    Bundle intentBundle = new Bundle();
                    intentBundle.putParcelable("stats", stats);
                    intent.putExtras(intentBundle);
                    context.startActivity(intent);
                }
                return false;
            }
        });

        image3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context,StagePostcardsDetail.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                Record records = gson.fromJson(settings.getString("records",""),Record.class);

                StageStats stats;

                if(records.records.stageStats.containsKey(splatfest.stage.id)){
                    stats = records.records.stageStats.get(splatfest.stage.id);
                    stats.isSplatnet = true;
                }else{
                    stats = new StageStats();
                    stats.stage = splatfest.stage;
                    stats.isSplatnet = false;
                }


                if(stats!=null) {
                    stats.calcStats(context);

                    Bundle intentBundle = new Bundle();
                    intentBundle.putParcelable("stats", stats);
                    intent.putExtras(intentBundle);
                    context.startActivity(intent);
                }
                return false;
            }
        });
    }
}
