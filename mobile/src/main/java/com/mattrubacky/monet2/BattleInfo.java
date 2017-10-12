package com.mattrubacky.monet2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleInfo extends AppCompatActivity {
    Battle battle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        SplatnetSQL database = new SplatnetSQL(getApplicationContext());
        battle = database.selectBattle(id);


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
