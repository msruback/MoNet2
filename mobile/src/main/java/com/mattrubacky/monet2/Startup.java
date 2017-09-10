package com.mattrubacky.monet2;

import android.content.Intent;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        final EditText loginUrl = (EditText) findViewById(R.id.LoginUrl);
        Button submit = (Button) findViewById(R.id.UrlSubmit);

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                intent.putExtra("URL",loginUrl.getText());
                startActivity(intent);
            }
        });



        prompt.setTypeface(font);
        loginUrl.setTypeface(font);
        submit.setTypeface(font);

    }
}
