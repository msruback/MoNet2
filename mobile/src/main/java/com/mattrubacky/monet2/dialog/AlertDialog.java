package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 11/12/2017.
 */

public class AlertDialog extends Dialog {
    String toSay;
    public AlertDialog(Activity activity, String text) {
        super(activity);
        toSay = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_alert);
        TextView loadingText = findViewById(R.id.LoadingText);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        loadingText.setText(toSay);
        loadingText.setTypeface(titleFont);
    }
}
