package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        ArrayList<String> hours = new ArrayList<>();
        hours.add("1 Hour");
        hours.add("2 Hours");
        hours.add("4 Hours");
        hours.add("6 Hours");
        hours.add("8 Hours");
        hours.add("10 Hours");
        hours.add("12 Hours");

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");

        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        TextView autoTitle = (TextView) findViewById(R.id.autoUpdateTitle);
        TextView autoText = (TextView) findViewById(R.id.AutoText);
        TextView frequencyText = (TextView) findViewById(R.id.FrequencyText);
        TextView dataText = (TextView) findViewById(R.id.DataText);

        Spinner frequencySpinner = (Spinner) findViewById(R.id.FrequencySpinner);


        autoTitle.setTypeface(fontTitle);
        autoText.setTypeface(font);
        frequencyText.setTypeface(font);
        dataText.setTypeface(font);
    }
}
