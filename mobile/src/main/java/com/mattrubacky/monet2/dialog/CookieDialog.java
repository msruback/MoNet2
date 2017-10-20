package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 10/14/2017.
 */

public class CookieDialog extends Dialog {
    public CookieDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_cookie);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        RelativeLayout Submit = (RelativeLayout) findViewById(R.id.Submit);

        TextView prompt = (TextView) findViewById(R.id.DialogPrompt);
        final TextView cookieInput = (TextView) findViewById(R.id.CookieInput);

        prompt.setTypeface(titleFont);
        cookieInput.setTypeface(font);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor edit = settings.edit();
                String cookie = "iksm_token="+cookieInput.getText().toString();
                edit.putString("cookie",cookie);
                edit.commit();
                dismiss();
            }
        });



    }
}