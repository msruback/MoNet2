package com.mattrubacky.monet2;

import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        TextView prompt = (TextView) findViewById(R.id.SwitchPrompt);
        EditText loginUrl = (EditText) findViewById(R.id.LoginUrl);
        Button submit = (Button) findViewById(R.id.UrlSubmit);

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");



        prompt.setTypeface(font);
        loginUrl.setTypeface(font);
        submit.setTypeface(font);

    }
}
