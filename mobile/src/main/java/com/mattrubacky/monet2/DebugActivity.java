package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_campaign_stats);

        Typeface titleFont = Typeface.createFromAsset(getAssets(),"Paintball.otf");

        TextView honor = (TextView) findViewById(R.id.Honor);
        TextView percent = (TextView) findViewById(R.id.PercentComplete);

        honor.setTypeface(titleFont);
        percent.setTypeface(titleFont);

    }

}
