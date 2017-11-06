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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.adapter.NavAdapter;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.fragment.*;
import com.mattrubacky.monet2.reciever.BootReciever;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.reciever.SalmonAlarm;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    ExpandableListView drawerList;
    ArrayList<String> titles,children;
    Fragment rotation,shop,battleList,settingsFrag,weaponLocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        //Add titles
        titles = new ArrayList<String>();
        titles.add("Rotation");
        titles.add("Shop");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        titles.add(settings.getString("name","User"));
        titles.add("Battles");
        titles.add("Settings");

        children = new ArrayList<String>();
        children.add("Weapon Locker");
        children.add("Closet");
        children.add("Stages");
        children.add("Splatfests");
        children.add("Campaign");

        //Just when I am too lazy to enter the token by hand
        //SharedPreferences.Editor edit = settings.edit();
        //edit.putString("cookie","iksm_session=");
        //edit.commit();

        //Add fragments
        rotation = new RotationFragment();
        shop = new ShopFragment();
        battleList = new BattleListFragment();
        settingsFrag = new SettingsFragment();

        weaponLocker = new WeaponLockerFragment();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        DataUpdateAlarm dataUpdateAlarm = new DataUpdateAlarm();
        if(settings.getBoolean("autoUpdate",false)){
            Long lastUpdate = settings.getLong("lastUpdate",0);
            Calendar calendar = Calendar.getInstance();
            int hour;
            switch(settings.getInt("updateInterval",0)){
                case 0:
                    lastUpdate += Long.valueOf(1000*60*60);
                    break;
                case 1:
                    lastUpdate += Long.valueOf(1000*60*60*2);
                    break;
                case 2:
                    lastUpdate += Long.valueOf(1000*60*60*4);
                    break;
                case 3:
                    lastUpdate += Long.valueOf(1000*60*60*6);;
                    break;
                case 4:
                    lastUpdate += Long.valueOf(1000*60*60*8);
                    break;
                case 5:
                    lastUpdate += Long.valueOf(1000*60*60*10);
                    break;
                case 6:
                    lastUpdate += Long.valueOf(1000*60*60*12);
                    break;
                case 7:
                    lastUpdate += Long.valueOf(1000*60*60*24);
                    break;
                default:
                    lastUpdate += Long.valueOf(1000*60*60);
                    break;
            }
            if(lastUpdate<calendar.getTimeInMillis()){
                dataUpdateAlarm.setAlarm(MainActivity.this);
            }else{
                dataUpdateAlarm.setAlarmDelayed(MainActivity.this);
            }
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

        final FragmentManager fragmentManager = getSupportFragmentManager();
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
                switch (intent.getIntExtra("stats",0)){
                    case 0://Player page reserved
                        break;
                    case 1://
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,weaponLocker)
                                .commit();
                        break;
                }
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

        drawerList.setAdapter(new NavAdapter(getApplicationContext(),titles,children));


        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), R.color.transparentgrey2));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch(position){
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, rotation)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, shop)
                                .commit();

                        drawerLayout.closeDrawer(drawerList);
                        break;
                    case 2://Stats expands
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,battleList)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,settingsFrag)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        break;
                }

                // Insert the fragment by replacing any existing fragment

                // Highlight the selected item, update the title, and close the drawer
                drawerList.setItemChecked(position, true);
                return false;
            }
        });

        //Handle stat children clicks
        drawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                System.out.println(childPosition);
                switch(childPosition){
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,weaponLocker)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        break;
                }
                return false;
            }
        });


    }
    private Runnable updateTimeline = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cookie = settings.getString("cookie","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
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
                        ArrayList<Gear> gear = new ArrayList<>();
                        gear.add(timeline.currentRun.rewardGear.gear);
                        database.insertGear(gear);
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
                    database.insertSplatfests(pastSplatfests.splatfests,pastSplatfests.results);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };




}
