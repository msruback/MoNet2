package com.mattrubacky.monet2;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    ViewGroup rootView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_settings, container, false);


        ArrayList<String> hours = new ArrayList<>();
        hours.add("1 Hour");
        hours.add("2 Hours");
        hours.add("4 Hours");
        hours.add("6 Hours");
        hours.add("8 Hours");
        hours.add("10 Hours");
        hours.add("12 Hours");

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        TextView autoTitle = (TextView) rootView.findViewById(R.id.autoUpdateTitle);
        TextView autoText = (TextView) rootView.findViewById(R.id.AutoText);
        TextView frequencyText = (TextView) rootView.findViewById(R.id.FrequencyText);
        TextView dataText = (TextView) rootView.findViewById(R.id.DataText);

        Spinner frequencySpinner = (Spinner) rootView.findViewById(R.id.FrequencySpinner);


        autoTitle.setTypeface(fontTitle);
        autoText.setTypeface(font);
        frequencyText.setTypeface(font);
        dataText.setTypeface(font);

        return rootView;

    }

}
