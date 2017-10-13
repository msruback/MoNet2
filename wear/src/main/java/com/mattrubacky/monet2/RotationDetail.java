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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RotationDetail extends Activity implements DataApi.DataListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    Schedules schedules;
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
                        CurrentSplatfest currentSplatfest = gson.fromJson(settings.getString("currentSplatfest",""),CurrentSplatfest.class);
                        Splatfest splatfest = currentSplatfest.splatfests.get(0);
                        title.setText("Splatfest");
                        titleZigZag.setBackground(getResources().getDrawable(R.drawable.repeat_zigzag_splatfest));

                        String alphaColor = currentSplatfest.splatfests.get(0).colors.alpha.getColor();

                        String bravoColor = currentSplatfest.splatfests.get(0).colors.bravo.getColor();

                        titleLayout.setBackgroundColor(Color.parseColor(alphaColor));
                        titleZigZag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));
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

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
        if((schedules.regular.get(0).end*1000)<(new Date().getTime())) {
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
    private class RegularAdapter extends ArrayAdapter<TimePeriod> {
        public RegularAdapter(Context context, ArrayList<TimePeriod> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_regular, parent, false);
            }
            TimePeriod timePeriod = getItem(position);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
            TextView stageB = (TextView) convertView.findViewById(R.id.StageB);

            time.setTypeface(font);
            stageA.setTypeface(font);
            stageB.setTypeface(font);

            Date startTime = new Date((timePeriod.start*1000));
            SimpleDateFormat sdf = new SimpleDateFormat("h a");
            String startText = sdf.format(startTime);
            Date endTime = new Date((timePeriod.end*1000));
            String endText = sdf.format(endTime);

            time.setText(startText+" to "+endText);
            stageA.setText(timePeriod.a.name);
            stageB.setText(timePeriod.b.name);

            return convertView;
        }
    }
    private class CompetitiveAdapter extends ArrayAdapter<TimePeriod> {
        public CompetitiveAdapter(Context context, ArrayList<TimePeriod> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_competitive, parent, false);
            }
            TimePeriod timePeriod = getItem(position);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView mode = (TextView) convertView.findViewById(R.id.Mode);
            TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
            TextView stageB = (TextView) convertView.findViewById(R.id.StageB);

            time.setTypeface(font);
            mode.setTypeface(font);
            stageA.setTypeface(font);
            stageB.setTypeface(font);

            Date startTime = new Date((timePeriod.start*1000));
            SimpleDateFormat sdf = new SimpleDateFormat("h a");
            String startText = sdf.format(startTime);
            Date endTime = new Date((timePeriod.end*1000));
            String endText = sdf.format(endTime);

            time.setText(startText+" to "+endText);
            mode.setText(timePeriod.rule.name);
            stageA.setText(timePeriod.a.name);
            stageB.setText(timePeriod.b.name);

            return convertView;
        }
    }

    private class FestivalAdapter extends ArrayAdapter<TimePeriod> {
        Splatfest splatfest;
        public FestivalAdapter(Context context, ArrayList<TimePeriod> input,Splatfest splatfest) {
            super(context, 0, input);
            this.splatfest = splatfest;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_festival, parent, false);
            }
            TimePeriod timePeriod = getItem(position);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
            TextView stageB = (TextView) convertView.findViewById(R.id.StageB);
            TextView stageC = (TextView) convertView.findViewById(R.id.StageC);

            time.setTypeface(font);
            stageA.setTypeface(font);
            stageB.setTypeface(font);
            stageC.setTypeface(font);

            Date startTime = new Date((timePeriod.start*1000));
            SimpleDateFormat sdf = new SimpleDateFormat("h a");
            String startText = sdf.format(startTime);
            Date endTime = new Date((timePeriod.end*1000));
            String endText = sdf.format(endTime);

            time.setText(startText+" to "+endText);
            stageA.setText(timePeriod.a.name);
            stageB.setText(timePeriod.b.name);
            stageC.setText(splatfest.stage.name);

            return convertView;
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUI();
        }

    }
}
