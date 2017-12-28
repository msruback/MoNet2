package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.mattrubacky.monet2.adapter.CompetitiveAdapter;
import com.mattrubacky.monet2.adapter.FestivalAdapter;
import com.mattrubacky.monet2.adapter.RegularAdapter;
import com.mattrubacky.monet2.adapter.SalmonAdapter;
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RotationDetail extends Activity implements DataApi.DataListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    Schedules schedules;
    SalmonSchedule salmonSchedule;
    CurrentSplatfest currentSplatfest;
    private GoogleApiClient googleApiClient;
    WatchViewStub stub;
    String type;
    UpdateRotationData updateRotationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_detail);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        updateRotationData = new UpdateRotationData();

        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {


                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

                RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
                RelativeLayout titleZigZag = (RelativeLayout) findViewById(R.id.titleZigZag);

                TextView title = (TextView) findViewById(R.id.Title);
                title.setTypeface(fontTitle);

                ListView times = (ListView) findViewById(R.id.times);
                switch(type){
                    case "regular":
                        title.setText("Turf War");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.turf));
                        RegularAdapter regularAdapter = new RegularAdapter(getApplicationContext(),schedules.regular);
                        times.setAdapter(regularAdapter);
                        break;
                    case "ranked":
                        title.setText("Ranked");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.ranked));
                        CompetitiveAdapter rankedAdapter = new CompetitiveAdapter(getApplicationContext(),schedules.ranked);
                        times.setAdapter(rankedAdapter);
                        break;
                    case "league":
                        title.setText("League");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.league));
                        CompetitiveAdapter leagueAdapter = new CompetitiveAdapter(getApplicationContext(),schedules.league);
                        times.setAdapter(leagueAdapter);
                        break;
                    case "fes":
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Gson gson = new Gson();
                        title.setText("Splatfest");
                        titleZigZag.setBackground(getResources().getDrawable(R.drawable.repeat_zigzag_splatfest));

                        String alphaColor = currentSplatfest.splatfests.get(0).colors.alpha.getColor();

                        String bravoColor = currentSplatfest.splatfests.get(0).colors.bravo.getColor();

                        FestivalAdapter festivalAdapter = new FestivalAdapter(getApplicationContext(),schedules.splatfest,currentSplatfest.splatfests.get(0));
                        times.setAdapter(festivalAdapter);

                        titleLayout.setBackgroundColor(Color.parseColor(alphaColor));
                        titleZigZag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));
                        break;
                    case "salmon":
                        title.setText("Grizz Co.");
                        titleLayout.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.salmonAccent));
                        SalmonAdapter salmonAdapter = new SalmonAdapter(getApplicationContext(),salmonSchedule.details);
                        times.setAdapter(salmonAdapter);
                        break;
                }
                if(schedules.regular.size()!=0) {
                    while ((schedules.regular.get(0).end * 1000) < new Date().getTime()) {
                        schedules.dequeue();
                    }
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
        salmonSchedule = gson.fromJson(settings.getString("salmonRun",""),SalmonSchedule.class);

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
        if(schedules.regular.size()==0||(schedules.regular.get(0).end*1000)<(new Date().getTime())) {
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
                    Wearable.DataApi.deleteDataItems(googleApiClient, item.getUri());
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
        ListView times = (ListView) findViewById(R.id.times);
        switch(type){

        }
    }

    private class UpdateRotationData extends AsyncTask<Void,Void,Void>{

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
            currentSplatfest = gson.fromJson(dataMap.getString("currentSplatfest"),CurrentSplatfest.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUI();
        }

    }
}
