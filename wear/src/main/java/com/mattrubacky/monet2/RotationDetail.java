package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.ui.adapter.CompetitiveAdapter;
import com.mattrubacky.monet2.ui.adapter.FestivalAdapter;
import com.mattrubacky.monet2.ui.adapter.RegularAdapter;
import com.mattrubacky.monet2.ui.adapter.SalmonAdapter;
import com.mattrubacky.monet2.connection.WatchConnected;
import com.mattrubacky.monet2.connection.WatchConnector;
import com.mattrubacky.monet2.data.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.data.deserialized.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.data.deserialized.Schedules;
import com.mattrubacky.monet2.data.deserialized.TimePeriod;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class RotationDetail extends Activity implements WatchConnected{

    private Schedules schedules;
    private SalmonSchedule salmonSchedule;
    private CurrentSplatfest currentSplatfest;
    private WatchViewStub stub;
    private ListView times;
    private RelativeLayout titleLayout,titleZigZag;
    private String type;
    private WatchConnector watchConnector;

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_detail);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        watchConnector = new WatchConnector(getApplicationContext(),this);
        watchConnector.execute();

        stub = findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                times = stub.findViewById(R.id.times);
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

                titleLayout = stub.findViewById(R.id.titleLayout);
                titleZigZag = stub.findViewById(R.id.titleZigZag);

                TextView title = stub.findViewById(R.id.Title);
                title.setTypeface(fontTitle);
                switch(type){
                    case "regular":
                        title.setText("Turf War");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.turf));
                        break;
                    case "ranked":
                        title.setText("Ranked");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.ranked));
                        break;
                    case "league":
                        title.setText("League");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.league));
                        break;
                    case "fes":
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Gson gson = new Gson();
                        title.setText("Splatfest");
                        titleZigZag.setBackground(getResources().getDrawable(R.drawable.repeat_zigzag_splatfest));
                        break;
                    case "salmon":
                        title.setText("Grizz Co.");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.salmonAccent));
                        break;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonSchedule",json);
        edit.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void updateUI(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        Schedules schedules = gson.fromJson(settings.getString("rotationState","{\"regular\":[],\"gachi\":[],\"league\":[],\"fes\":[]}"),Schedules.class);
        SalmonSchedule salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedules\":[],\"details\":[]}"),SalmonSchedule.class);
        CurrentSplatfest currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);

        switch(type){
            case "regular":
                ArrayList<TimePeriod> regular = schedules.regular;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getResources().getConfiguration().isScreenRound()){
                        regular.add(null);
                    }
                }

                RegularAdapter regularAdapter = new RegularAdapter(getApplicationContext(),regular);
                times.setAdapter(regularAdapter);
                break;
            case "ranked":
                ArrayList<TimePeriod> ranked = schedules.ranked;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getResources().getConfiguration().isScreenRound()){
                        ranked.add(null);
                    }
                }

                CompetitiveAdapter rankedAdapter = new CompetitiveAdapter(getApplicationContext(),ranked);
                times.setAdapter(rankedAdapter);
                break;
            case "league":
                ArrayList<TimePeriod> league = schedules.league;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getResources().getConfiguration().isScreenRound()){
                        league.add(null);
                    }
                }

                CompetitiveAdapter leagueAdapter = new CompetitiveAdapter(getApplicationContext(),league);
                times.setAdapter(leagueAdapter);
                break;
            case "fes":
                ArrayList<TimePeriod> splatfest = schedules.splatfest;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getResources().getConfiguration().isScreenRound()){
                        splatfest.add(null);
                    }
                }

                titleZigZag.setBackground(getResources().getDrawable(R.drawable.repeat_zigzag_splatfest));

                String alphaColor = currentSplatfest.splatfests.get(0).colors.alpha.getColor();

                String bravoColor = currentSplatfest.splatfests.get(0).colors.bravo.getColor();

                FestivalAdapter festivalAdapter = new FestivalAdapter(getApplicationContext(),splatfest,currentSplatfest.splatfests.get(0));
                times.setAdapter(festivalAdapter);

                titleLayout.setBackgroundColor(Color.parseColor(alphaColor));
                titleZigZag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));
                break;
            case "salmon":
                ArrayList<SalmonRunDetail> details = salmonSchedule.details;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getResources().getConfiguration().isScreenRound()){
                        details.add(null);
                    }
                }
                SalmonAdapter salmonAdapter = new SalmonAdapter(getApplicationContext(),details);
                times.setAdapter(salmonAdapter);
                break;
        }
    }

    @Override
    public void update(Bundle bundle) {
        schedules = bundle.getParcelable("rotation");
        salmonSchedule = bundle.getParcelable("salmon");
        currentSplatfest = bundle.getParcelable("splatfest");
        updateUI();
    }
}
