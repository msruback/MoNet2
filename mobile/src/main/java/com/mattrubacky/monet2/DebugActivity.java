package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_stats);
        SplatnetSQLManager database = new SplatnetSQLManager(this);
        ArrayList<Battle> battles = database.getBattles();
        ArrayList<ClosetHanger> gear = new ArrayList<>();
        Battle battle;
        ClosetHanger hanger;
        for(int i=0;i<battles.size();i++){
            battle = battles.get(i);
            if(battle.user.user.head!=null) {
                hanger = new ClosetHanger();
                hanger.gear = battle.user.user.head;
                hanger.skills = battle.user.user.headSkills;
                hanger.time = battle.start;
                gear.add(hanger);
            }
            if(battle.user.user.clothes!=null) {
                hanger = new ClosetHanger();
                hanger.gear = battle.user.user.clothes;
                hanger.skills = battle.user.user.clothesSkills;
                hanger.time = battle.start;
                gear.add(hanger);
            }
            if(battle.user.user.shoes!=null) {
                hanger = new ClosetHanger();
                hanger.gear = battle.user.user.shoes;
                hanger.skills = battle.user.user.shoeSkills;
                hanger.time = battle.start;
                gear.add(hanger);
            }
        }
        database.insertCloset(gear);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
