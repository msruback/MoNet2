package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Long sessionExpire = (settings.getLong("token_expire",0)*1000);
        Long now = new Date().getTime();
        if(sessionExpire<now){
            Intent intent = new Intent(getBaseContext(), Login.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getBaseContext(), Rotation.class);
            startActivity(intent);
        }


    }
}
