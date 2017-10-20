package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.WearLink;
import com.mattrubacky.monet2.reciever.*;
import com.mattrubacky.monet2.fragment.schedule.*;
import com.mattrubacky.monet2.deserialized.*;

import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/14/2017.
 */

public class RotationFragment extends Fragment {
    Schedules schedules;
    android.os.Handler customHandler;
    ViewGroup rootView;
    WearLink wearLink;
    UpdateRotationData updateRotationData;
    SalmonSchedule salmonSchedule;
    Gear monthlyGear;
    CurrentSplatfest currentSplatfest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");
        updateRotationData = new UpdateRotationData();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        if(settings.contains("rotationState")) {
            schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
            if(schedules == null){
                schedules = new Schedules();
                schedules.regular = new ArrayList<TimePeriod>();
                schedules.ranked = new ArrayList<TimePeriod>();
                schedules.league = new ArrayList<TimePeriod>();
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
        }else{
            schedules = new Schedules();
            schedules.regular = new ArrayList<TimePeriod>();
            schedules.ranked = new ArrayList<TimePeriod>();
            schedules.league = new ArrayList<TimePeriod>();
        }
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedule\":[]}"),SalmonSchedule.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);


        wearLink = new WearLink(getContext());

        customHandler = new android.os.Handler();
        updateUi();

        if(salmonSchedule.schedule!=null&&salmonSchedule.schedule.size()!=0){
            if(salmonSchedule.schedule.get(0).endTime< new Date().getTime()){
                salmonSchedule.schedule.remove(0);
                SharedPreferences.Editor edit = settings.edit();
                String json = gson.toJson(salmonSchedule);
                edit.putString("salmonRunSchedule",json);
                edit.commit();
                SalmonAlarm salmonAlarm = new SalmonAlarm();
                salmonAlarm.cancelAlarm(getContext());
                salmonAlarm.setAlarm(getContext());
            }
        }

        //if(schedules.regular.size()==0){
            customHandler.post(update2Hours);
        /*}else {
            if ((schedules.regular.get(0).end * 1000) < new Date().getTime()) {
                do{
                    schedules.dequeue();
                }while(schedules.regular.size()>0&&(schedules.regular.get(0).end * 1000)< new Date().getTime());

                updateUi();
                customHandler.post(update2Hours);
            }else{
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());
                Calendar nextUpdate = Calendar.getInstance();
                nextUpdate.setTimeInMillis(now.getTimeInMillis());
                int hour = now.get(Calendar.HOUR);
                if(now.get(Calendar.HOUR)%2==0){
                    hour+=2;
                }else{
                    hour+=1;
                }
                nextUpdate.set(Calendar.HOUR,hour);
                nextUpdate.set(Calendar.MINUTE,0);
                nextUpdate.set(Calendar.SECOND,0);
                nextUpdate.set(Calendar.MILLISECOND,0);
                Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
                customHandler.postDelayed(update2Hours, nextUpdateTime);
            }
        }*/

        return rootView;
    }



    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(schedules);
        edit.putString("rotationState",json);
        json = gson.toJson(salmonSchedule);
        edit.putString("salmonRunSchedule",json);
        json = gson.toJson(currentSplatfest);
        edit.putString("currentSplatfest",json);
        edit.commit();
        wearLink.closeConnection();
        updateRotationData.cancel(true);
        customHandler.removeCallbacks(update2Hours);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        schedules = gson.fromJson(settings.getString("rotationState",""),Schedules.class);
        monthlyGear = gson.fromJson(settings.getString("reward_gear",""),Gear.class);
        salmonSchedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
        currentSplatfest = gson.fromJson(settings.getString("currentSplatfest","{\"festivals\":[]}"),CurrentSplatfest.class);
        wearLink.openConnection();
    }


    //Get Rotation Data

    private void updateUi(){
        ListView scheduleList = (ListView) rootView.findViewById(R.id.ScheduleList);

        if(schedules==null){
            schedules = new Schedules();
        }
        if(salmonSchedule==null){
            salmonSchedule= new SalmonSchedule();
        }
        if(salmonSchedule.schedule==null){
            salmonSchedule.schedule = new ArrayList<>();
        }

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
        if(schedules.splatfest!=null&&currentSplatfest.splatfests.size()>0){
            if(schedules.regular.size()==0||currentSplatfest.splatfests.get(0).times.start<schedules.regular.get(0).start){
                rotation.add(0,"fes");
            }else{
                rotation.add("fes");
            }
        }
        rotation.add("salmon");


        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),rotation);
        scheduleList.setAdapter(scheduleAdapter);

        wearLink.sendRotation(schedules);
        wearLink.sendSalmon(salmonSchedule);
    }

    private class UpdateRotationData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String cookie;

                //Create Splatnet manager
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                cookie = settings.getString("cookie","");
                Call<Schedules> rotationGet = splatnet.getSchedules(cookie);
                Response response = rotationGet.execute();
                if(response.isSuccessful()){
                    schedules = (Schedules) response.body();
                    SplatnetSQLManager database = new SplatnetSQLManager(getContext());
                    for(int i=0;i<schedules.regular.size();i++){
                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.regular.get(i).a.id)){
                            database.insertStage(schedules.regular.get(i).a);
                        }
                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.regular.get(i).b.id)){
                            database.insertStage(schedules.regular.get(i).b);
                        }

                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.ranked.get(i).a.id)){
                            database.insertStage(schedules.ranked.get(i).a);
                        }
                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.ranked.get(i).b.id)){
                            database.insertStage(schedules.ranked.get(i).b);
                        }

                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.league.get(i).a.id)){
                            database.insertStage(schedules.league.get(i).a);
                        }
                        if(!database.existsIn(SplatnetContract.Stage.TABLE_NAME, SplatnetContract.Stage._ID,schedules.league.get(i).b.id)){
                            database.insertStage(schedules.league.get(i).b);
                        }
                    }
                    Call<CurrentSplatfest> getSplatfest = splatnet.getActiveSplatfests(cookie);
                    response = getSplatfest.execute();
                    if(response.isSuccessful()){
                        currentSplatfest = (CurrentSplatfest) response.body();
                        if(currentSplatfest.splatfests.size()>0){
                            schedules.setSplatfest(currentSplatfest.splatfests.get(0));
                            if(!database.existsIn(SplatnetContract.Splatfest.TABLE_NAME, SplatnetContract.Splatfest._ID,currentSplatfest.splatfests.get(0).id)){
                                database.insertSplatfest(currentSplatfest.splatfests.get(0));
                            }
                        }
                    }else{

                    }
                }else{

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
        }

    }

    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            updateRotationData = new UpdateRotationData();
            updateRotationData.execute();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdate = Calendar.getInstance();
            nextUpdate.setTimeInMillis(now.getTimeInMillis());
            int hour = now.get(Calendar.HOUR);
            if(now.get(Calendar.HOUR)%2==0){
                hour+=2;
            }else{
                hour+=1;
            }
            int zero = 0;
            nextUpdate.set(Calendar.HOUR,hour);
            nextUpdate.set(Calendar.MINUTE,0);
            nextUpdate.set(Calendar.SECOND,0);
            nextUpdate.set(Calendar.MILLISECOND,0);
            Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };






    //Adapters
    private class ScheduleAdapter extends ArrayAdapter<String> {
        public ScheduleAdapter(Context context, ArrayList<String> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
            Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

            switch(getItem(position)){
                case "regular":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_regular, parent, false);

                    TextView turfWarTitle = (TextView) convertView.findViewById(R.id.turfWarName);
                    turfWarTitle.setTypeface(fontTitle);

                    ViewPager TurfPager = (ViewPager) convertView.findViewById(R.id.TurfPager);
                    TabLayout turfDots = (TabLayout) convertView.findViewById(R.id.TurfDots);

                    turfDots.setupWithViewPager(TurfPager, true);
                    PagerAdapter turfAdapter = new TurfAdapter(getChildFragmentManager(), schedules.regular);
                    TurfPager.setAdapter(turfAdapter);

                    break;
                case "ranked":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_ranked, parent, false);

                    TextView rankedTitle = (TextView) convertView.findViewById(R.id.rankedName);
                    rankedTitle.setTypeface(fontTitle);

                    ViewPager RankPager = (ViewPager) convertView.findViewById(R.id.RankedPager);
                    TabLayout rankDots = (TabLayout) convertView.findViewById(R.id.RankDots);
                    rankDots.setupWithViewPager(RankPager, true);
                    PagerAdapter rankAdapter = new RankAdapter(getChildFragmentManager(), schedules.ranked);
                    RankPager.setAdapter(rankAdapter);


                    break;
                case "league":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_league, parent, false);

                    TextView leagueTitle = (TextView) convertView.findViewById(R.id.leagueName);
                    leagueTitle.setTypeface(fontTitle);

                    ViewPager LeaguePager = (ViewPager) convertView.findViewById(R.id.LeaguePager);
                    TabLayout leagueDots = (TabLayout) convertView.findViewById(R.id.LeagueDots);
                    leagueDots.setupWithViewPager(LeaguePager, true);
                    PagerAdapter leagueAdapter = new LeagueAdapter(getChildFragmentManager(), schedules.league);
                    LeaguePager.setAdapter(leagueAdapter);


                    break;
                case "fes":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_festival, parent, false);

                    RelativeLayout fesCard = (RelativeLayout) convertView.findViewById(R.id.Festival);
                    RelativeLayout fesBanner = (RelativeLayout) convertView.findViewById(R.id.fesModeBanner);
                    RelativeLayout Alpha = (RelativeLayout) convertView.findViewById(R.id.Alpha);
                    RelativeLayout Bravo = (RelativeLayout) convertView.findViewById(R.id.Bravo);

                    fesCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.alpha.getColor())));
                    fesBanner.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.bravo.getColor())));

                    Alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.alpha.getColor())));
                    Bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(currentSplatfest.splatfests.get(0).colors.bravo.getColor())));

                    TextView fesTitle = (TextView) convertView.findViewById(R.id.fesName);
                    fesTitle.setTypeface(fontTitle);

                    ViewPager FesPager = (ViewPager) convertView.findViewById(R.id.FesPager);
                    TabLayout fesDots = (TabLayout) convertView.findViewById(R.id.FesDots);

                    fesDots.setupWithViewPager(FesPager, true);
                    PagerAdapter fesAdapter = new FesAdapter(getChildFragmentManager(), schedules.splatfest,currentSplatfest.splatfests.get(0));
                    FesPager.setAdapter(fesAdapter);

                    break;
                case "salmon":
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_salmon, parent, false);

                    RelativeLayout salmonRun = (RelativeLayout) convertView.findViewById(R.id.SalmonRun);
                    salmonRun.setClipToOutline(true);

                    TextView salmonTitle = (TextView) convertView.findViewById(R.id.salmonName);
                    salmonTitle.setTypeface(fontTitle);

                    Button addRun = (Button) convertView.findViewById(R.id.AddRun);
                    addRun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), AddRun.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type","new");
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    ListView SalmonTimes = (ListView) convertView.findViewById(R.id.SalmonTimes);
                    ImageView currentGear = (ImageView) convertView.findViewById(R.id.monthlyGear);

                    if(monthlyGear!=null) {
                        String url = "https://app.splatoon2.nintendo.net" + monthlyGear.url;
                        ImageHandler imageHandler = new ImageHandler();
                        String imageDirName = monthlyGear.name.toLowerCase().replace(" ", "_");
                        if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                            currentGear.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                        } else {
                            Picasso.with(getContext()).load(url).into(currentGear);
                            imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                        }
                    }
                    if(salmonSchedule == null){
                        salmonSchedule = new SalmonSchedule();
                    }
                    if(salmonSchedule.schedule==null){
                        salmonSchedule.schedule = new ArrayList<>();
                    }
                    SalmonRunAdapter salmonRunAdapter = new SalmonRunAdapter(getContext(),salmonSchedule.schedule);
                    SalmonTimes.setAdapter(salmonRunAdapter);

                    break;
            }

            return convertView;
        }
    }


    private class TurfAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public TurfAdapter(FragmentManager fm, ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment turf = new TurfRotation();
            turf.setArguments(bundle);
            return turf;
        }

        @Override
        public int getCount() {
            return input.size();
        }
        @Override
        public Parcelable saveState() {
            // Do Nothing
            return null;
        }

    }
    private class RankAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public RankAdapter(FragmentManager fm,ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment rank = new RankedRotation();
            rank.setArguments(bundle);
            return rank;
        }

        @Override
        public int getCount() {
            return input.size();
        }
        @Override
        public Parcelable saveState() {
            // Do Nothing
            return null;
        }

    }
    private class LeagueAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        public LeagueAdapter(FragmentManager fm,ArrayList<TimePeriod> input) {
            super(fm);
            this.input = input;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            Fragment league = new LeagueRotation();
            league.setArguments(bundle);
            return league;
        }

        @Override
        public int getCount() {
            return input.size();
        }
        @Override
        public Parcelable saveState() {
            // Do Nothing
            return null;
        }

    }
    private class FesAdapter extends FragmentStatePagerAdapter {
        ArrayList<TimePeriod> input;
        Splatfest splatfest;
        public FesAdapter(FragmentManager fm, ArrayList<TimePeriod> input,Splatfest splatfest) {
            super(fm);
            this.input = input;
            this.splatfest = splatfest;
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putParcelable("timePeriod",input.get(position));
            bundle.putParcelable("splatfest",splatfest);
            Fragment fes = new SplatfestRotation();
            fes.setArguments(bundle);
            return fes;
        }

        @Override
        public int getCount() {
            return input.size();
        }
        @Override
        public Parcelable saveState() {
            // Do Nothing
            return null;
        }

    }
    private class SalmonRunAdapter extends ArrayAdapter<SalmonRun> {
        public SalmonRunAdapter(Context context, ArrayList<SalmonRun> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_salmon_run, parent, false);
            }

            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

            final SalmonRun salmonRun = getItem(position);

            RelativeLayout weapon1Layout = (RelativeLayout) convertView.findViewById(R.id.weapon1);
            RelativeLayout weapon2Layout = (RelativeLayout) convertView.findViewById(R.id.weapon2);
            RelativeLayout weapon3Layout = (RelativeLayout) convertView.findViewById(R.id.weapon3);
            RelativeLayout weapon4Layout = (RelativeLayout) convertView.findViewById(R.id.weapon4);


            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView stage = (TextView) convertView.findViewById(R.id.stage);

            time.setTypeface(font);
            stage.setTypeface(font);

            ImageView weapon1 = (ImageView) convertView.findViewById(R.id.Weapon1);
            ImageView weapon2 = (ImageView) convertView.findViewById(R.id.Weapon2);
            ImageView weapon3 = (ImageView) convertView.findViewById(R.id.Weapon3);
            ImageView weapon4 = (ImageView) convertView.findViewById(R.id.Weapon4);
            ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

            SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");


            String startText = sdf.format(salmonRun.startTime);
            String endText = sdf.format(salmonRun.endTime);

            time.setText(startText + " to " + endText);
            stage.setText(salmonRun.stage);
            if(salmonRun.stage.equals("")){
                stage.setVisibility(View.GONE);
            }else{
                stage.setVisibility(View.VISIBLE);
            }


            ImageHandler imageHandler = new ImageHandler();
            if(salmonRun.weapons.get(0)!=null) {
                if(salmonRun.weapons.get(0).id==-1){
                    weapon1.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
                }else {
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(0).url;
                    String imageDirName = salmonRun.weapons.get(0).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                        weapon1.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getContext()).load(url).into(weapon1);
                        imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                    }
                }
            }
            if(salmonRun.weapons.get(1)!=null) {
                if(salmonRun.weapons.get(1).id==-1){
                    weapon2.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
                }else {
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(1).url;
                    String imageDirName = salmonRun.weapons.get(1).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                        weapon2.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getContext()).load(url).into(weapon2);
                        imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                    }
                }
            }

            if(salmonRun.weapons.get(2)!=null) {
                if(salmonRun.weapons.get(2).id==-1){
                    weapon3.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
                }else {
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(2).url;
                    String imageDirName = salmonRun.weapons.get(2).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                        weapon3.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getContext()).load(url).into(weapon3);
                        imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                    }
                }
            }

            if(salmonRun.weapons.get(3)!=null) {
                if(salmonRun.weapons.get(3).id==-1){
                    weapon4.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
                }else {
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(3).url;
                    String imageDirName = salmonRun.weapons.get(3).name.toLowerCase().replace(" ", "_");
                    if (imageHandler.imageExists("weapon", imageDirName, getContext())) {
                        weapon4.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getContext()).load(url).into(weapon4);
                        imageHandler.downloadImage("weapon", imageDirName, url, getContext());
                    }
                }
            }
            if(salmonRun.weapons.get(0)==null&&salmonRun.weapons.get(1)==null&&salmonRun.weapons.get(2)==null&&salmonRun.weapons.get(3)==null){
                weapon1Layout.setVisibility(View.GONE);
                weapon2Layout.setVisibility(View.GONE);
                weapon3Layout.setVisibility(View.GONE);
                weapon4Layout.setVisibility(View.GONE);
            }else{
                weapon1Layout.setVisibility(View.VISIBLE);
                weapon2Layout.setVisibility(View.VISIBLE);
                weapon3Layout.setVisibility(View.VISIBLE);
                weapon4Layout.setVisibility(View.VISIBLE);
            }

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AddRun.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type","edit");
                    bundle.putInt("position",position);
                    bundle.putParcelable("run",salmonSchedule.schedule.get(position));
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });


            return convertView;
        }
    }
}
