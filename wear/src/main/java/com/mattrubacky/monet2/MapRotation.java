package com.mattrubacky.monet2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;
import com.mattrubacky.monet2.deserialized.TimePeriod;

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
    CurrentSplatfest currentSplatfest;

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

                title.setTypeface(fontTitle);
                updateRotationData = new UpdateRotationData();
                updateRotationData.execute();
                if(salmonSchedule!=null&&salmonSchedule.details.size()!=0){
                    if((salmonSchedule.details.get(0).end*1000)<new Date().getTime()){
                        salmonSchedule.details.remove(0);
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
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
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
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
        if(schedules.regular==null||schedules.regular.size()==0||((schedules.regular.get(0).end*1000)<(new Date().getTime()))) {
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
        ArrayList<String> rotation = new ArrayList<>();
        if(schedules.regular!=null&&schedules.regular.size()>0){
            rotation.add("regular");
        }
        if(schedules.ranked!=null&&schedules.ranked.size()>0){
            rotation.add("ranked");
        }
        if(schedules.league!=null&&schedules.league.size()>0){
            rotation.add("league");
        }
        if(currentSplatfest.splatfests.size()>0&&schedules.splatfest!=null&&schedules.splatfest.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }
        if(salmonSchedule!=null&&salmonSchedule.details!=null&&salmonSchedule.details.size()>0){
            rotation.add("salmon");
        }
        ListView rotationList = (ListView) stub.findViewById(R.id.ScheduleList);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getApplicationContext(),rotation);
        rotationList.setAdapter(scheduleAdapter);
    }

    private class ScheduleAdapter extends ArrayAdapter<String> {
        public ScheduleAdapter(Context context, ArrayList<String> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

            switch(getItem(position)){
                case "regular":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_regular, parent, false);
                    TextView TurfMode = (TextView) convertView.findViewById(R.id.TurfMode);
                    TextView TurfStageA = (TextView) convertView.findViewById(R.id.TurfStageA);
                    TextView TurfStageB = (TextView) convertView.findViewById(R.id.TurfStageB);

                    TurfMode.setTypeface(fontTitle);
                    TurfStageA.setTypeface(font);
                    TurfStageB.setTypeface(font);

                    TurfStageA.setText(schedules.regular.get(0).a.name);
                    TurfStageB.setText(schedules.regular.get(0).b.name);

                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
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
                    break;
                case "ranked":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_ranked, parent, false);
                    TextView RankMode = (TextView) convertView.findViewById(R.id.RankMode);
                    TextView RankStageA = (TextView) convertView.findViewById(R.id.RankStageA);
                    TextView RankStageB = (TextView) convertView.findViewById(R.id.RankStageB);

                    RankMode.setTypeface(fontTitle);
                    RankStageA.setTypeface(font);
                    RankStageB.setTypeface(font);

                    RankMode.setText(schedules.ranked.get(0).rule.name);
                    RankStageA.setText(schedules.ranked.get(0).a.name);
                    RankStageB.setText(schedules.ranked.get(0).b.name);
                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
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
                    break;
                case "league":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_league, parent, false);

                    TextView LeagueMode = (TextView) convertView.findViewById(R.id.LeagueMode);
                    TextView LeagueStageA = (TextView) convertView.findViewById(R.id.LeagueStageA);
                    TextView LeagueStageB = (TextView) convertView.findViewById(R.id.LeagueStageB);

                    LeagueMode.setTypeface(fontTitle);
                    LeagueStageA.setTypeface(font);
                    LeagueStageB.setTypeface(font);

                    LeagueMode.setText(schedules.league.get(0).rule.name);
                    LeagueStageA.setText(schedules.league.get(0).a.name);
                    LeagueStageB.setText(schedules.league.get(0).b.name);

                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
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
                    break;
                case "fes":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_festival, parent, false);
                    RelativeLayout SplatfestCard = (RelativeLayout) convertView.findViewById(R.id.FesCard);
                    RelativeLayout SplatfestZigZag = (RelativeLayout) convertView.findViewById(R.id.FesZigZag);
                    RelativeLayout Alpha = (RelativeLayout) convertView.findViewById(R.id.Alpha);
                    RelativeLayout Bravo = (RelativeLayout) convertView.findViewById(R.id.Bravo);


                    TextView SplatfestMode = (TextView) convertView.findViewById(R.id.FesMode);
                    TextView SplatfestStageA = (TextView) convertView.findViewById(R.id.FesStageA);
                    TextView SplatfestStageB = (TextView) convertView.findViewById(R.id.FesStageB);
                    TextView SplatfestStageC = (TextView) convertView.findViewById(R.id.FesStageC);

                    SplatfestMode.setTypeface(font);
                    SplatfestStageA.setTypeface(font);
                    SplatfestStageB.setTypeface(font);
                    SplatfestStageC.setTypeface(font);

                    String alphaColor = currentSplatfest.splatfests.get(0).colors.alpha.getColor();

                    String bravoColor = currentSplatfest.splatfests.get(0).colors.bravo.getColor();

                    SplatfestCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(alphaColor)));
                    SplatfestZigZag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));

                    Alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(alphaColor)));
                    Bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));

                    SplatfestStageA.setText(schedules.splatfest.get(0).a.name);
                    SplatfestStageB.setText(schedules.splatfest.get(0).b.name);
                    SplatfestStageC.setText(currentSplatfest.splatfests.get(0).stage.name);

                    SplatfestCard.setClipToOutline(true);

                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Intent intent = new Intent(getBaseContext(), RotationDetail.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type","fes");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            return false;
                        }
                    });
                    break;
                case "salmon":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_salmon, parent, false);

                    TextView salmonTitle = (TextView) convertView.findViewById(R.id.SalmonTitle);
                    TextView SalmonShift1 = (TextView) convertView.findViewById(R.id.ShiftTime1);
                    TextView SalmonShift2 = (TextView) convertView.findViewById(R.id.ShiftTime2);

                    salmonTitle.setTypeface(fontTitle);
                    SalmonShift1.setTypeface(font);
                    SalmonShift2.setTypeface(font);

                    SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");
                    String startText = sdf.format(salmonSchedule.details.get(0).start);
                    String endText = sdf.format(salmonSchedule.details.get(0).end);
                    SalmonShift1.setText(startText + " to " + endText);
                    if(salmonSchedule.details.size()>1){
                        startText = sdf.format(salmonSchedule.details.get(1).start);
                        endText = sdf.format(salmonSchedule.details.get(1).end);
                        SalmonShift2.setText(startText + " to " + endText);
                    }
                    break;
            }

            return convertView;
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
            currentSplatfest = gson.fromJson(dataMap.getString("currentSplatfest"),CurrentSplatfest.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(schedules==null){
                schedules = new Schedules();
            }
            if(schedules.regular==null){
                schedules.regular = new ArrayList<>();
            }
            if(schedules.ranked==null){
                schedules.ranked = new ArrayList<>();
            }
            if(schedules.league==null){
                schedules.league = new ArrayList<>();
            }
            if(schedules.splatfest==null){
                schedules.splatfest = new ArrayList<>();
            }
            if(currentSplatfest==null){
                currentSplatfest = new CurrentSplatfest();
            }
            if(currentSplatfest.splatfests==null){
                currentSplatfest.splatfests = new ArrayList<>();
            }
            if(salmonSchedule==null){
                salmonSchedule = new SalmonSchedule();
            }
            if(salmonSchedule.details==null){
                salmonSchedule.details = new ArrayList<>();
            }
            updateUI();
        }

    }
}
