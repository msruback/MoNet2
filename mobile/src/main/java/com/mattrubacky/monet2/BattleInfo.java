package com.mattrubacky.monet2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BattleInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

    }
}
