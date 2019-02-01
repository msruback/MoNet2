package com.mattrubacky.monet2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.adapter.ListView.NavAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.PastSplatfest;
import com.mattrubacky.monet2.deserialized.splatoon.Record;
import com.mattrubacky.monet2.deserialized.splatoon.Timeline;
import com.mattrubacky.monet2.dialog.GearNotificationPickerDialog;
import com.mattrubacky.monet2.dialog.StageNotificationPickerDialog;
import com.mattrubacky.monet2.fragment.MainScreenFragments.BattleListFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.CampaignStatsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.ChunkBagFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.ClosetFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.PlayerStatsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.RotationFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.SalmonRunResultsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.SettingsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.ShopFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.SplatfestStatsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.StagePostcardsFragment;
import com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments.WeaponLockerFragment;
import com.mattrubacky.monet2.notifications.GrizzCoRewardNotificationFactory;
import com.mattrubacky.monet2.notifications.StageNotificationFactory;
import com.mattrubacky.monet2.reciever.BootReciever;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.reciever.NotificationAlarm;
import com.mattrubacky.monet2.api.splatnet.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    ExpandableListView drawerList;
    ArrayList<String> titles,children;
    Fragment rotation,shop,playerFrag,battleList,coopList,settingsFrag,weaponLocker,closet,stagePostcards,chunkBag,splatfestStats,campaignStats;
    FragmentManager fragmentManager;
    ArrayList<String> backStack;
    TextView addButton;
    ImageView notificationButton;
    Record record;
    PastSplatfest pastSplatfests;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);
        fragmentManager = getSupportFragmentManager();
        backStack = new ArrayList<>();

        //Add titles
        titles = new ArrayList<>();
        titles.add("Rotation");
        titles.add("Shop");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        titles.add(settings.getString("name","User"));
        titles.add("Battles");
        titles.add("Salmon Run Shifts");
        titles.add("Leaderboards");
        titles.add("Settings");

        children = new ArrayList<>();
        children.add("Weapon Locker");
        children.add("Closet");
        children.add("Stages");
        children.add("Ability Chunks");
        children.add("Splatfests");
        children.add("Campaign");
        children.add("Ink");

        //Add fragments
        rotation = new RotationFragment();
        shop = new ShopFragment();
        playerFrag = new PlayerStatsFragment();
        battleList = new BattleListFragment();
        coopList = new SalmonRunResultsFragment();
        settingsFrag = new SettingsFragment();

        weaponLocker = new WeaponLockerFragment();
        closet = new ClosetFragment();
        stagePostcards = new StagePostcardsFragment();
        chunkBag = new ChunkBagFragment();
        splatfestStats = new SplatfestStatsFragment();
        campaignStats = new CampaignStatsFragment();

        addButton = findViewById(R.id.AddButton);
        notificationButton = findViewById(R.id.NotificationButton);

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
                    lastUpdate += 6;
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

            NotificationAlarm notificationAlarm = new NotificationAlarm();
            notificationAlarm.setAlarm(MainActivity.this);

            ComponentName receiver = new ComponentName(MainActivity.this, BootReciever.class);
            PackageManager pm = getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }else{
            ComponentName receiver = new ComponentName(MainActivity.this, BootReciever.class);
            PackageManager pm = getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
        new GrizzCoRewardNotificationFactory(this).manageNotifications();
        new StageNotificationFactory(this).manageNotifications();

        Intent intent = getIntent();
        switch(intent.getIntExtra("fragment",0)){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, rotation)
                        .commit();
                backStack.add(0,"rotation");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                notificationButton.setVisibility(View.GONE);
                notificationButton.setOnClickListener(null);
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, shop)
                        .commit();
                backStack.add(0,"shop");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                notificationButton.setVisibility(View.GONE);
                notificationButton.setOnClickListener(null);
                break;
            //Stats fragments go here
            case 2:
                switch (intent.getIntExtra("stats",0)){
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, playerFrag)
                                .commit();
                        backStack.add(0,"player");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 1://
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,weaponLocker)
                                .commit();
                        backStack.add(0,"weaponlocker");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,closet)
                                .commit();
                        backStack.add(0,"closet");
                        addButton.setVisibility(View.VISIBLE);
                        addButton.setOnClickListener(new addGearClickListener());
                        notificationButton.setVisibility(View.VISIBLE);
                        notificationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GearNotificationPickerDialog dialog = new GearNotificationPickerDialog(MainActivity.this);
                                dialog.show();
                            }
                        });
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,stagePostcards)
                                .commit();
                        backStack.add(0,"stagepostcards");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.VISIBLE);
                        notificationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StageNotificationPickerDialog dialog = new StageNotificationPickerDialog(MainActivity.this);
                                dialog.show();
                            }
                        });
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,chunkBag)
                                .commit();
                        backStack.add(0,"chunkbag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 5:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,splatfestStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"splatfeststats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 6:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,campaignStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"campaignstats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
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
                notificationButton.setVisibility(View.GONE);
                notificationButton.setOnClickListener(null);
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,coopList)
                        .commit();
                backStack.add(0,"cooplist");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                notificationButton.setVisibility(View.GONE);
                notificationButton.setOnClickListener(null);
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container,settingsFrag)
                        .commit();
                backStack.add(0,"settings");
                addButton.setVisibility(View.GONE);
                addButton.setOnClickListener(null);
                notificationButton.setVisibility(View.GONE);
                notificationButton.setOnClickListener(null);
                break;
        }

        //Get the timeline
        Thread t = new Thread(updateTimeline);
        t.start();

        Typeface font, fontTitle;
        if(settings.getBoolean("useInkling",false)){
            font = Typeface.createFromAsset(getAssets(),"Inkling.otf");
            fontTitle = Typeface.createFromAsset(getAssets(),"Inkling.otf");
        }else{
            font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
            fontTitle = Typeface.createFromAsset(getAssets(),"Paintball.otf");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
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
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, shop)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"shop");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 2:
                        //Stats expands
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,battleList)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"battlelist");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,coopList)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"cooplist");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 6:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,settingsFrag)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"settingsfrag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                }

                // Insert the fragment by replacing any existing fragment

                // Highlight the selected item, update the title, and close the drawer
                drawerList.setItemChecked(position, true);
                return false;
            }
        });

        drawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, playerFrag)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"player");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                }
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
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,closet)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"closet");
                        addButton.setVisibility(View.VISIBLE);
                        addButton.setOnClickListener(new addGearClickListener());
                        notificationButton.setVisibility(View.VISIBLE);
                        notificationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GearNotificationPickerDialog dialog = new GearNotificationPickerDialog(MainActivity.this);
                                dialog.show();
                            }
                        });
                        break;
                    case 2:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,stagePostcards)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"stagepostcards");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.VISIBLE);
                        notificationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StageNotificationPickerDialog dialog = new StageNotificationPickerDialog(MainActivity.this);
                                dialog.show();
                            }
                        });
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,chunkBag)
                            .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"chunkbag");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 4:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,splatfestStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"splatfeststats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
                        break;
                    case 5:
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container,campaignStats)
                                .commit();
                        drawerLayout.closeDrawer(drawerList);
                        backStack.add(0,"campaignstats");
                        addButton.setVisibility(View.GONE);
                        addButton.setOnClickListener(null);
                        notificationButton.setVisibility(View.GONE);
                        notificationButton.setOnClickListener(null);
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
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "shop":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, shop)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "player":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, playerFrag)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                //Stats fragments go here
                case "weaponlocker":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,weaponLocker)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "closet":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,closet)
                            .commit();
                    addButton.setVisibility(View.VISIBLE);
                    addButton.setOnClickListener(new addGearClickListener());
                    notificationButton.setVisibility(View.VISIBLE);
                    notificationButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GearNotificationPickerDialog dialog = new GearNotificationPickerDialog(MainActivity.this);
                            dialog.show();
                        }
                    });
                    break;
                case "stagepostcards":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,stagePostcards)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.VISIBLE);
                    notificationButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StageNotificationPickerDialog dialog = new StageNotificationPickerDialog(MainActivity.this);
                            dialog.show();
                        }
                    });
                    break;
                case "chunkbag":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,chunkBag)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "splatfeststats":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,splatfestStats)
                            .commit();
                    drawerLayout.closeDrawer(drawerList);
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "campaignstats":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,campaignStats)
                            .commit();
                    drawerLayout.closeDrawer(drawerList);
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "battlelist":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,battleList)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "cooplist":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,coopList)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
                    break;
                case "settings":
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container,settingsFrag)
                            .commit();
                    addButton.setVisibility(View.GONE);
                    addButton.setOnClickListener(null);
                    notificationButton.setVisibility(View.GONE);
                    notificationButton.setOnClickListener(null);
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
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net/").addConverterFactory(GsonConverterFactory.create()).build();
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
                        }

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };




}
