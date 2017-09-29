package com.mattrubacky.monet2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_battle);

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");

        RelativeLayout item = (RelativeLayout) findViewById(R.id.item);
        item.setClipToOutline(true);

        TextView mode = (TextView) findViewById(R.id.mode);
        TextView map = (TextView) findViewById(R.id.map);
        TextView result = (TextView) findViewById(R.id.result);

        ImageView weapon = (ImageView) findViewById(R.id.weapon);
        ImageView type = (ImageView) findViewById(R.id.type);
    }
}
