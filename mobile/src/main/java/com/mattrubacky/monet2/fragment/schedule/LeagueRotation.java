package com.mattrubacky.monet2.fragment.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.StagePostcardsDetail;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.tables.Stage;
import com.mattrubacky.monet2.deserialized.splatoon.TimePeriod;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;

import com.mattrubacky.monet2.helper.StageStats;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LeagueRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_league_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.leagueTime);
        TextView mode = (TextView) rootView.findViewById(R.id.leagueMode);
        TextView title1 = (TextView) rootView.findViewById(R.id.leagueStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.leagueStageName2);
        ImageView image1 = (ImageView) rootView.findViewById(R.id.leagueStageImage1);
        ImageView image2 = (ImageView) rootView.findViewById(R.id.leagueStageImage2);

        time.setTypeface(font);
        mode.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);

        Bundle bundle = this.getArguments();
        TimePeriod timePeriod = bundle.getParcelable("timePeriod");

        final Stage a = timePeriod.a;
        final Stage b = timePeriod.b;
        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        title1.setText(a.name);
        title2.setText(b.name);
        mode.setText(timePeriod.rule.getName(getContext()));
        time.setText(startText+" - "+endText);

        String url1 = "https://app.splatoon2.nintendo.net"+a.url;
        String url2 = "https://app.splatoon2.nintendo.net"+b.url;

        ImageHandler imageHandler = new ImageHandler();
        String image1DirName = a.name.toLowerCase().replace(" ","_");
        String image2DirName = b.name.toLowerCase().replace(" ","_");

        if(imageHandler.imageExists("stage",image1DirName,getContext())){
            image1.setImageBitmap(imageHandler.loadImage("stage",image1DirName));
        }else{
            Picasso.with(getContext()).load(url1).resize(1280,720).into(image1);
            imageHandler.downloadImage("stage",image1DirName,url1,getContext());
        }

        if(imageHandler.imageExists("stage",image2DirName,getContext())){
            image2.setImageBitmap(imageHandler.loadImage("stage",image2DirName));
        }else{
            Picasso.with(getContext()).load(url2).resize(1280,720).into(image2);
            imageHandler.downloadImage("stage",image2DirName,url2,getContext());
        }

        image1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getActivity(),StagePostcardsDetail.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
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

                    stats.calcStats(getContext());

                    Bundle intentBundle = new Bundle();
                    intentBundle.putParcelable("stats", stats);
                    intent.putExtras(intentBundle);
                    startActivity(intent);
                }
                return false;
            }
        });

        image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getActivity(),StagePostcardsDetail.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
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
                    stats.calcStats(getContext());

                    Bundle intentBundle = new Bundle();
                    intentBundle.putParcelable("stats", stats);
                    intent.putExtras(intentBundle);
                    startActivity(intent);
                }
                return false;
            }
        });

        return rootView;
    }
}