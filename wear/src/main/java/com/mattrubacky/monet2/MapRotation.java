package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapRotation extends Activity implements DataApi.DataListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    private GoogleApiClient googleApiClient;
    WatchViewStub stub;
    UpdateRotationData updateRotationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        updateRotationData = new UpdateRotationData();

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Gson gson = new Gson();
                if(settings.contains("rotationState")) {
                    schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
                    if(schedules == null){
                        schedules = new Schedules();
                        schedules.regular = new ArrayList<TimePeriod>();
                        schedules.ranked = new ArrayList<TimePeriod>();
                        schedules.league = new ArrayList<TimePeriod>();
                    }
                }else{
                    schedules = new Schedules();
                    schedules.regular = new ArrayList<TimePeriod>();
                    schedules.ranked = new ArrayList<TimePeriod>();
                    schedules.league = new ArrayList<TimePeriod>();
                }
                salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedule\":[]}"),SalmonSchedule.class);

                Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");


                TextView title = (TextView) stub.findViewById(R.id.Title);

                RelativeLayout TurfWar = (RelativeLayout) stub.findViewById(R.id.TurfWar);
                TextView TurfMode = (TextView) stub.findViewById(R.id.TurfMode);
                TextView TurfStageA = (TextView) stub.findViewById(R.id.TurfStageA);
                TextView TurfStageB = (TextView) stub.findViewById(R.id.TurfStageB);

                RelativeLayout Ranked = (RelativeLayout) stub.findViewById(R.id.Ranked);
                TextView RankMode = (TextView) stub.findViewById(R.id.RankMode);
                TextView RankStageA = (TextView) stub.findViewById(R.id.RankStageA);
                TextView RankStageB = (TextView) stub.findViewById(R.id.RankStageB);

                RelativeLayout League = (RelativeLayout) stub.findViewById(R.id.League);
                TextView LeagueMode = (TextView) stub.findViewById(R.id.LeagueMode);
                TextView LeagueStageA = (TextView) stub.findViewById(R.id.LeagueStageA);
                TextView LeagueStageB = (TextView) stub.findViewById(R.id.LeagueStageB);

                TextView SalmonTitle = (TextView) stub.findViewById(R.id.SalmonTitle);
                TextView SalmonShift1 = (TextView) stub.findViewById(R.id.ShiftTime1);
                TextView SalmonShift2 = (TextView) stub.findViewById(R.id.ShiftTime2);

                title.setTypeface(fontTitle);

                TurfMode.setTypeface(fontTitle);
                TurfStageA.setTypeface(font);
                TurfStageB.setTypeface(font);

                RankMode.setTypeface(fontTitle);
                RankStageA.setTypeface(font);
                RankStageB.setTypeface(font);

                LeagueMode.setTypeface(fontTitle);
                LeagueStageA.setTypeface(font);
                LeagueStageB.setTypeface(font);

                SalmonTitle.setTypeface(fontTitle);
                SalmonShift1.setTypeface(font);
                SalmonShift2.setTypeface(font);

                TurfWar.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getBaseContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","regular");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return false;
                    }
                });

                Ranked.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getBaseContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","ranked");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return false;
                    }
                });

                League.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getBaseContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","league");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return false;
                    }
                });
                if(salmonSchedule.schedule.size()!=0){
                    if(salmonSchedule.schedule.get(0).endTime<new Date().getTime()){
                        salmonSchedule.schedule.remove(0);
                    }
                }
                if(schedules.regular.size()!=0&&((schedules.regular.get(0).end * 1000) < new Date().getTime())) {
                    do{
                        schedules.dequeue();
                    }while(schedules.regular.size()!=0&&((schedules.regular.get(0).end * 1000) < new Date().getTime()));
                }
                updateUI();
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
        edit.putString("salmonRunSchedule",json);
        edit.commit();
        googleApiClient.disconnect();
        Wearable.DataApi.removeListener(googleApiClient, this);
        updateRotationData.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedule\":[]}"),SalmonSchedule.class);
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
        if(schedules.regular.size()==0||((schedules.regular.get(0).end*1000)<(new Date().getTime()))) {
            updateRotationData =new UpdateRotationData();
            updateRotationData.execute();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/schedules") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    saveSchedules(dataMap.getString("schedule"));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }

    }
    private void saveSchedules(String schedules){
        Gson gson = new Gson();
        this.schedules = gson.fromJson(schedules,Schedules.class);
        if(schedules!=null){
            updateUI();
        }
    }


    private void updateUI(){
        RelativeLayout TurfWar = (RelativeLayout) stub.findViewById(R.id.TurfWar);
        TextView TurfStageA = (TextView) stub.findViewById(R.id.TurfStageA);
        TextView TurfStageB = (TextView) stub.findViewById(R.id.TurfStageB);

        RelativeLayout Ranked = (RelativeLayout) stub.findViewById(R.id.Ranked);
        TextView RankMode = (TextView) stub.findViewById(R.id.RankMode);
        TextView RankStageA = (TextView) stub.findViewById(R.id.RankStageA);
        TextView RankStageB = (TextView) stub.findViewById(R.id.RankStageB);

        RelativeLayout League = (RelativeLayout) stub.findViewById(R.id.League);
        TextView LeagueMode = (TextView) stub.findViewById(R.id.LeagueMode);
        TextView LeagueStageA = (TextView) stub.findViewById(R.id.LeagueStageA);
        TextView LeagueStageB = (TextView) stub.findViewById(R.id.LeagueStageB);

        RelativeLayout SalmonRun = (RelativeLayout) stub.findViewById(R.id.SalmonRun);
        TextView SalmonShift1 = (TextView) stub.findViewById(R.id.ShiftTime1);
        TextView SalmonShift2 = (TextView) stub.findViewById(R.id.ShiftTime2);

        if(schedules.regular.size()>0) {
            TurfWar.setVisibility(View.VISIBLE);
            TurfStageA.setText(schedules.regular.get(0).a.name);
            TurfStageB.setText(schedules.regular.get(0).b.name);
        }else{
            TurfWar.setVisibility(View.GONE);
        }

        if(schedules.ranked.size()>0) {
            Ranked.setVisibility(View.VISIBLE);
            RankMode.setText(schedules.ranked.get(0).rule.name);
            RankStageA.setText(schedules.ranked.get(0).a.name);
            RankStageB.setText(schedules.ranked.get(0).b.name);
        }else{
            Ranked.setVisibility(View.GONE);
        }

        if(schedules.league.size()>0) {
            League.setVisibility(View.VISIBLE);
            LeagueMode.setText(schedules.league.get(0).rule.name);
            LeagueStageA.setText(schedules.league.get(0).a.name);
            LeagueStageB.setText(schedules.league.get(0).b.name);
        }else{
            League.setVisibility(View.GONE);
        }

        if(salmonSchedule!=null) {
            if (salmonSchedule.schedule.size() > 0) {
                SalmonRun.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");
                String startText = sdf.format(salmonSchedule.schedule.get(0).startTime);
                String endText = sdf.format(salmonSchedule.schedule.get(0).endTime);
                SalmonShift1.setText(startText + " to " + endText);
                if(salmonSchedule.schedule.size()>1){
                    startText = sdf.format(salmonSchedule.schedule.get(1).startTime);
                    endText = sdf.format(salmonSchedule.schedule.get(1).endTime);
                    SalmonShift2.setText(startText + " to " + endText);
                }
            } else {
                SalmonRun.setVisibility(View.GONE);
            }
        }else{
            SalmonRun.setVisibility(View.GONE);
        }


    }

    private class UpdateRotationData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            Uri uri = new Uri.Builder().scheme(PutDataRequest.WEAR_URI_SCHEME).authority(Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes().get(0).getId()).path("/schedules").build();
            DataItem item = Wearable.DataApi.getDataItem(googleApiClient,uri).await().getDataItem();
            DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
            Gson gson = new Gson();
            schedules = gson.fromJson(dataMap.getString("schedule"),Schedules.class);
            salmonSchedule = gson.fromJson(dataMap.getString("salmonRunSchedule"),SalmonSchedule.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUI();
        }

    }
}
