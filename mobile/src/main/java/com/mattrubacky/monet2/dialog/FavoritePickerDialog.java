package com.mattrubacky.monet2.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/28/2017.
 */

public class FavoritePickerDialog extends Dialog {
    String toSay;
    public FavoritePickerDialog(Activity activity, String text) {
        super(activity);
        toSay = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_favorite_picker);


    }
}
