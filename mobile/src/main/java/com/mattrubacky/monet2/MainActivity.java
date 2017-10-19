package com.mattrubacky.monet2;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.fragment.*;
import com.mattrubacky.monet2.reciever.BootReciever;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.reciever.SalmonAlarm;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ArrayList<String> titles;
    Fragment rotation,shop,battleList,settingsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        //Add titles
        titles = new ArrayList<String>();
        titles.add("Rotation");
        titles.add("Shop");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        titles.add(settings.getString("name","User"));
        titles.add("Battles");
        titles.add("Settings");

        //Just when I am too lazy to enter the token by hand
        //SharedPreferences.Editor edit = settings.edit();
        //edit.putString("cookie","iksm_session=");
        //edit.commit();

        //Add fragments
        rotation = new RotationFragment();
        shop = new ShopFragment();
        battleList = new BattleListFragment();
        settingsFrag = new SettingsFragment();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        DataUpdateAlarm dataUpdateAlarm = new DataUpdateAlarm();
        if(settings.getBoolean("autoUpdate",false)){
            dataUpdateAlarm.setAlarm(MainActivity.this);
            ComponentName receiver = new ComponentName(MainActivity.this, BootReciever.class);
            PackageManager pm = getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            if(settings.getBoolean("salmonNotifications",false)){
                SalmonAlarm salmonAlarm = new SalmonAlarm();
                salmonAlarm.setAlarm(MainActivity.this);
            }
        }else{
            ComponentName receiver = new ComponentName(MainActivity.this, BootReciever.class);
            PackageManager pm = getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        switch(intent.getIntExtra("fragment",0)){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, rotation)
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, shop)
                        .commit();
                break;
            //Stats fragments go here
            case 2:
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,battleList)
                        .commit();
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,settingsFrag)
                        .commit();
                break;
        }

        //Get the timeline
        Thread t = new Thread(updateTimeline);
        t.start();

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        title.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerList.setAdapter(new NavAdapter(getApplicationContext(),titles));


        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), R.color.transparentgrey2));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    /** Swaps fragments*/
    private void selectItem(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, rotation)
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, shop)
                        .commit();
                break;
            //Stats fragments go here
            case 2:
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,battleList)
                        .commit();
                break;
            case 4:
                fragmentManager.beginTransaction()
                    .replace(R.id.frame_container,settingsFrag)
                    .commit();
                break;
        }

        // Insert the fragment by replacing any existing fragment

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }
    private class NavAdapter extends ArrayAdapter<String> {
        public NavAdapter(Context context, ArrayList<String> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nav, parent, false);
            }
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");


            TextView title = (TextView) convertView.findViewById(R.id.Title);
            title.setText(getItem(position));
            title.setTypeface(font);
            return convertView;
        }
    }
    private Runnable updateTimeline = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cookie = settings.getString("cookie","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                SplatnetSQLManager database = new SplatnetSQLManager(getApplicationContext());


                Call<Timeline> getTimeline = splatnet.getTimeline(cookie);
                Response response = getTimeline.execute();

                if(response.isSuccessful()){
                    Timeline timeline = (Timeline) response.body();
                    SharedPreferences.Editor edit = settings.edit();
                    if(timeline.currentRun.rewardGear!=null) {
                        Gson gson = new Gson();
                        String json = gson.toJson(timeline.currentRun.rewardGear.gear);
                        edit.putString("reward_gear", json);
                        edit.commit();
                        if (!database.existsIn(SplatnetContract.Gear.TABLE_NAME, SplatnetContract.Gear._ID, timeline.currentRun.rewardGear.gear.id)) {
                            database.insertGear(timeline.currentRun.rewardGear.gear);
                        }
                    }
                }else{
                }
                Call<Record> getRecords = splatnet.getRecords(cookie);
                response = getRecords.execute();
                if(response.isSuccessful()){
                    Record record = (Record) response.body();
                    SharedPreferences.Editor edit = settings.edit();
                    Gson gson = new Gson();
                    edit.putString("unique_id",record.records.unique_id);
                    edit.putString("name",record.records.user.name);
                    edit.commit();
                }else{

                }
                Call<PastSplatfest> getSplatfests = splatnet.getPastSplatfests(cookie);
                response = getSplatfests.execute();
                if(response.isSuccessful()){
                    PastSplatfest pastSplatfests = (PastSplatfest) response.body();
                    Splatfest splatfest;
                    SplatfestResult splatfestResult;
                    boolean done;
                    for(int i=0;i<pastSplatfests.splatfests.size();i++){
                        done = false;
                        splatfest = pastSplatfests.splatfests.get(i);
                        if(!database.existsIn(SplatnetContract.Splatfest.TABLE_NAME, SplatnetContract.Splatfest._ID,splatfest.id)||!database.isSplatfestUpdated(splatfest.id)) {
                            for (int j = 0; (!done) && j < pastSplatfests.splatfests.size(); j++) {
                                splatfestResult = pastSplatfests.results.get(j);
                                if (splatfest.id == splatfestResult.id) {
                                    done = true;
                                    if(!database.existsIn(SplatnetContract.Splatfest.TABLE_NAME, SplatnetContract.Splatfest._ID,splatfest.id)) {
                                        database.insertSplatfest(splatfest, splatfestResult);
                                    }else{
                                        database.updateSplatfest(splatfest,splatfestResult);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };




}
