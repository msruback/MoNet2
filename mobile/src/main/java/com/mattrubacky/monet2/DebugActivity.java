package com.mattrubacky.monet2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_battle_info);
        RelativeLayout meter = (RelativeLayout) findViewById(R.id.meter);
        meter.setClipToOutline(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        TextView mode = (TextView) findViewById(R.id.Rule);
        TextView allyTitle = (TextView) findViewById(R.id.allyTitle);
        TextView foeTitle = (TextView) findViewById(R.id.foeTitle);
        mode.setTypeface(fontTitle);
        allyTitle.setTypeface(font);
        foeTitle.setTypeface(font);

    }
}
