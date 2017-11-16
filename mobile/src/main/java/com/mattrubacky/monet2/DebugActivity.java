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
        setContentView(R.layout.activity_splatfest_stats);
    }

}
