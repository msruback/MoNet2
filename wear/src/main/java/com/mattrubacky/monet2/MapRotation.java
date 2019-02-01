package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.adapter.ScheduleAdapter;
import com.mattrubacky.monet2.connection.WatchConnected;
import com.mattrubacky.monet2.connection.WatchConnector;
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;

import java.util.ArrayList;

public class MapRotation extends Activity implements WatchConnected{

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    WatchConnector watchConnector;
    WatchViewStub stub;
    CurrentSplatfest currentSplatfest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stub = findViewById(R.id.watch_view_stub);

        watchConnector = new WatchConnector(getApplicationContext(),this);

        watchConnector.execute();


        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

                TextView title = stub.findViewById(R.id.Title);

                title.setTypeface(fontTitle);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void update(Bundle bundle){
        schedules = bundle.getParcelable("rotation");
        salmonSchedule = bundle.getParcelable("salmon");
        currentSplatfest = bundle.getParcelable("splatfest");
        updateUI();
    }

    private void updateUI(){
        ArrayList<String> rotation = new ArrayList<>();
        if(schedules==null){
            schedules = new Schedules();
        }
        if(schedules.regular!=null&&schedules.regular.size()>0){
            rotation.add("regular");
        }
        if(schedules.ranked!=null&&schedules.ranked.size()>0){
            rotation.add("ranked");
        }
        if(schedules.league!=null&&schedules.league.size()>0){
            rotation.add("league");
        }
        if(currentSplatfest!=null&&currentSplatfest.splatfests!=null&&currentSplatfest.splatfests.size()>0&&schedules.splatfest!=null&&schedules.splatfest.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }
        if(salmonSchedule!=null&&salmonSchedule.details!=null&&salmonSchedule.details.size()>0){
            rotation.add("salmon");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getResources().getConfiguration().isScreenRound()){
                rotation.add("empty");
            }
        }
        ListView rotationList = stub.findViewById(R.id.ScheduleList);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getApplicationContext(),rotation,schedules,salmonSchedule,currentSplatfest);
        rotationList.setAdapter(scheduleAdapter);
    }
}
