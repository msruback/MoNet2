package com.mattrubacky.monet2.ui.fragment.WelcomeFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.backend.GenericCallback;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by mattr on 9/14/2017.
 */

public class WelcomeFragment extends Fragment {
    private ViewGroup rootView;
    private GenericCallback genericCallback;

    public void setGenericCallback(GenericCallback genericCallback) {
        this.genericCallback = genericCallback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_firststart_welcome, container, false);

        TextView welcomeBlurb = rootView.findViewById(R.id.welcomeBlurb);
        TextView nextText = rootView.findViewById(R.id.nextText);

        RelativeLayout nextButton = rootView.findViewById(R.id.NextButton);

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