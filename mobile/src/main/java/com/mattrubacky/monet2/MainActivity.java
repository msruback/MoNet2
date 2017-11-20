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
import com.mattrubacky.monet2.dialog.AlertDialog;
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
    Fragment rotation,shop,battleList,settingsFrag,weaponLocker,closet,stagePostcards,chunkBag,splatfestStats;
    FragmentManager fragmentManager;
    ArrayList<String> backStack;
    TextView addButton;
    Record record;
    PastSplatfest pastSplatfests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        fragmentManager = getSupportFragmentManager();
        backStack = new ArrayList<>();

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
        children.add("Ability Chunks");
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
        closet = new ClosetFragment();
        stagePostcards = new StagePostcardsFragment();
        chunkBag = new ChunkBagFragment();
        splatfestStats = new SplatfestStatsFragment();

        addButton = (TextView) findViewById(R.id.AddButton);

        DataUpdateAlarm dataUpdateAlarm = new DataUpdateAlarm();
        if(settings.getBoolean("autoUpdate",false)){
            int lastUpdate = settings.getInt("last_update",0);
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            switch(settings.getInt("updateInterval",0)){
                case 0:
                    lastUpdate += 1;
                    break;
                case 1:
                    lastUpdate += 2;
                    break;
                case 2:
                    lastUpdate += 4;
                    break;
                case 3:
                    lastUpdate += 6;;
                    break;
                case 4:
                    lastUpdate += 8;
                    break;
                case 5:
                    lastUpdate += 10;
                    break;
                case 6:
                    lastUpdate += 12;
                    break;
                case 7:
                    lastUpdate += 24;
                    break;
                default:
                    lastUpdate += 1;
                    break;
            }
            SharedPreferences.Editor edit = settings.edit();
            if(hour>lastUpdate){
                dataUpdateAlarm.setAlarm(MainActivity.this);
                edit.putInt("last_update",hour);
                edit.commit();
            }else{
                dataUpdateAlarm.setAlarmDelayed(MainActivity.this);
                edit.putInt("last_update",lastUpdate);
                edit.commit();
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

        Intent intent = getIntent();
        switch(intent.getIntExtra("fragment",0)){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, rotation)
                        .commit();
                backStack.add(0,"rotation");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, shop)
                        .commit();
                backStack.add(0,"shop");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
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
                        backStack.add(0,"weaponlocker");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,closet)
                                .commit();
                        backStack.add(0,"closet");
                        addButton.setVisibility(View.VISIBLE);
                        addButton.setOnClickListener(new addGearClickListener());
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,stagePostcards)
                                .commit();
                        backStack.add(0,"stagepostcards");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,chunkBag)
                                .commit();
                        backStack.add(0,"chunkbag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 5:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,splatfestStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"splatfestStats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                }
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,battleList)
                        .commit();
                backStack.add(0,"battlelist");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,settingsFrag)
                        .commit();
                backStack.add(0,"settings");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
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
                switch(position){
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, rotation)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"rotation");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, shop)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"shop");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 2://Stats expands
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,battleList)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"battlelist");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,settingsFrag)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"settingsfrag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
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
                        backStack.add(0,"weaponlocker");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,closet)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"closet");
                        addButton.setVisibility(View.VISIBLE);
                        addButton.setOnClickListener(new addGearClickListener());
                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,stagePostcards)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"stagepostcards");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,chunkBag)
                            .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"chunkbag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,splatfestStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"splatfestStats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        break;
                }
                return false;
            }
        });


    }

    class addGearClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,AddGear.class);
            Bundle bundle = new Bundle();
            bundle.putString("type","add");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(backStack==null){
            backStack = new ArrayList<>();
        }
    }

    @Override
    public void onBackPressed() {
        if(backStack.size()>1){
            String back = backStack.get(1);
            backStack.remove(0);
            backStack.remove(0);
            switch(back){
                case "rotation":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, rotation)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "shop":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, shop)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                //Stats fragments go here
                case "weaponlocker":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,weaponLocker)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "closet":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,closet)
                            .commit();
                    addButton.setVisibility(View.VISIBLE);
                    addButton.setOnClickListener(new addGearClickListener());
                    break;
                case "stagepostcards":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,stagePostcards)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "chunkbag":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,chunkBag)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "splatfeststats":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,splatfestStats)
                            .commit();
                    drawerLayout.closeDrawer(drawerList);
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "battlelist":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,battleList)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
                case "settings":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,settingsFrag)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    break;
            }
        }else {
            super.onBackPressed();
        }
    }

    private Runnable updateTimeline = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cookie = settings.getString("cookie","");
                String uniqueId = settings.getString("unique_id","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                SplatnetSQLManager database = new SplatnetSQLManager(getApplicationContext());


                Call<Timeline> getTimeline = splatnet.getTimeline(cookie,uniqueId);
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
                    Call<Record> getRecords = splatnet.getRecords(cookie,uniqueId);
                    response = getRecords.execute();

                    if(response.isSuccessful()){
                        record = (Record) response.body();
                        edit = settings.edit();
                        Gson gson = new Gson();
                        edit.putString("unique_id",record.records.unique_id);
                        edit.putString("name",record.records.user.name);
                        edit.commit();

                        Call<PastSplatfest> getSplatfests = splatnet.getPastSplatfests(cookie,uniqueId);
                        response = getSplatfests.execute();
                        if(response.isSuccessful()){
                            pastSplatfests = (PastSplatfest) response.body();
                            database.insertSplatfests(pastSplatfests.splatfests,pastSplatfests.results);
                        }else if(response.code()==403){
                            AlertDialog alertDialog = new AlertDialog(MainActivity.this,"Error: Cookie is invalid, please obtain a new cookie");
                            alertDialog.show();
                        }

                    }else if(response.code()==403){
                        AlertDialog alertDialog = new AlertDialog(MainActivity.this,"Error: Cookie is invalid, please obtain a new cookie");
                        alertDialog.show();
                    }

                }else if(response.code()==403){
                    AlertDialog alertDialog = new AlertDialog(MainActivity.this,"Error: Cookie is invalid, please obtain a new cookie");
                    alertDialog.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                AlertDialog alertDialog = new AlertDialog(MainActivity.this,"Error: Could not reach Splatnet");
                alertDialog.show();
            }
        }
    };




}
