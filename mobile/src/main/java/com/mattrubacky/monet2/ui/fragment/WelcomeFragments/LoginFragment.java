package com.mattrubacky.monet2.ui.fragment.WelcomeFragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.dialog.CookieDialog;
import com.mattrubacky.monet2.backend.GenericCallback;

import androidx.fragment.app.Fragment;

/**
 * Created by mattr on 9/14/2017.
 */

public class LoginFragment extends Fragment {
    ViewGroup rootView;
    GenericCallback genericCallback;

    public void setGenericCallback(GenericCallback genericCallback) {
        this.genericCallback = genericCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_firststart_login, container, false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        TextView cookieText = rootView.findViewById(R.id.CookieText);
        TextView loginText = rootView.findViewById(R.id.LoginText);

        RelativeLayout cookieButton = rootView.findViewById(R.id.loginCookie);

        cookieText.setTypeface(font);
        loginText.setTypeface(font);

        cookieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieDialog dialog = new CookieDialog(getActivity());
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        genericCallback.callback();
                    }
                });
            }
        });

        return rootView;
    }

}