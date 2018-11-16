package com.mattrubacky.monet2.fragment.WelcomeFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.GenericCallback;

/**
 * Created by mattr on 9/14/2017.
 */

public class WelcomeFragment extends Fragment{
    ViewGroup rootView;
    GenericCallback genericCallback;

    public void setGenericCallback(GenericCallback genericCallback) {
        this.genericCallback = genericCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_firststart_welcome, container, false);

        TextView welcomeBlurb = (TextView) rootView.findViewById(R.id.welcomeBlurb);
        TextView nextText = (TextView) rootView.findViewById(R.id.nextText);

        RelativeLayout nextButton = (RelativeLayout) rootView.findViewById(R.id.NextButton);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

        welcomeBlurb.setTypeface(fontTitle);
        nextText.setTypeface(font);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genericCallback.callback();
            }
        });
        return rootView;
    }

}