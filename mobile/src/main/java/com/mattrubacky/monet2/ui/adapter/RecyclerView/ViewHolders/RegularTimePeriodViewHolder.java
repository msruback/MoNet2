package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

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
import com.mattrubacky.monet2.ui.activities.StagePostcardsDetail;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.data.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.data.stats.StageStats;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattr on 1/14/2018.
 */

public class RegularTimePeriodViewHolder extends RecyclerView.ViewHolder{

    private TextView time,title1,title2;
    private ImageView image1,image2;
    private Context context;

    public RegularTimePeriodViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_regular_rotation, parent, false));

        this.context = context;

        time = itemView.findViewById(R.id.time);
        title1 = itemView.findViewById(R.id.stageName1);
        title2 = itemView.findViewById(R.id.stageName2);
        image1 = itemView.findViewById(R.id.stageImage1);
        image2 = itemView.findViewById(R.id.stageImage2);
    }

    public void manageHolder(TimePeriod timePeriod){

        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        time.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);

        final Stage a = timePeriod.a;
        final Stage b = timePeriod.b;
        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        title1.setText(a.name);
        title2.setText(b.name);
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.url;
        String url2 = "https://app.splatoon2.nintendo.net"+b.url;

        ImageHandler imageHandler = new ImageHandler();
        String image1DirName = a.name.toLowerCase().replace(" ","_");
        String image2DirName = b.name.toLowerCase().replace(" ","_");

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
    }
}
