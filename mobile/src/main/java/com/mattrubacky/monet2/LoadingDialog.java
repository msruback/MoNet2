package com.mattrubacky.monet2;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by mattr on 9/20/2017.
 */

public class LoadingDialog extends Dialog {
    String toSay;
    public LoadingDialog(Activity activity,String text) {
        super(activity);
        toSay = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        setContentView(R.layout.dialog_loading);
        TextView loadingText = (TextView) findViewById(R.id.LoadingText);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        loadingText.setText(toSay);
        loadingText.setTypeface(titleFont);
    }
}
