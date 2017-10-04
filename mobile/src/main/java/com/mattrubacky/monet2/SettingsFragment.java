package com.mattrubacky.monet2;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    DataUpdateAlarm dataUpdateAlarm;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_settings, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        dataUpdateAlarm = new DataUpdateAlarm();

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

        final RelativeLayout frequencyLayout = (RelativeLayout) rootView.findViewById(R.id.FrequencyLayout);
        final RelativeLayout dataLayout = (RelativeLayout) rootView.findViewById(R.id.DataLayout);

        TextView autoTitle = (TextView) rootView.findViewById(R.id.autoUpdateTitle);
        TextView autoText = (TextView) rootView.findViewById(R.id.AutoText);
        TextView frequencyText = (TextView) rootView.findViewById(R.id.FrequencyText);
        TextView dataText = (TextView) rootView.findViewById(R.id.DataText);

        final Spinner frequencySpinner = (Spinner) rootView.findViewById(R.id.FrequencySpinner);

        final Switch autoSwitch = (Switch) rootView.findViewById(R.id.AutoSwitch);
        final Switch dataSwitch = (Switch) rootView.findViewById(R.id.DataSwitch);

        Boolean checked = settings.getBoolean("autoUpdate",false);
        autoSwitch.setChecked(checked);
        dataSwitch.setChecked(settings.getBoolean("updateData",false));
        frequencySpinner.setSelection(settings.getInt("updateInterval",0));

        if(!checked){
            frequencyLayout.setAlpha((float) 0.5);
            dataLayout.setAlpha((float) 0.5);
            frequencySpinner.setEnabled(false);
            dataSwitch.setEnabled(false);
        }



        autoTitle.setTypeface(fontTitle);
        autoText.setTypeface(font);
        frequencyText.setTypeface(font);
        dataText.setTypeface(font);
        //Grab data as changed
        frequencySpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    SharedPreferences.Editor edit = settings.edit();
                    edit.putInt("updateInterval",frequencySpinner.getSelectedItemPosition());
                    edit.commit();
                }
            }
        });

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("autoUpdate",isChecked);
                edit.commit();
                if(isChecked){
                    frequencyLayout.setAlpha(1);
                    dataLayout.setAlpha(1);
                    frequencySpinner.setEnabled(true);
                    dataSwitch.setEnabled(true);
                    dataUpdateAlarm.setAlarm(getContext());
                }else{
                    frequencyLayout.setAlpha((float) 0.5);
                    dataLayout.setAlpha((float) 0.5);
                    frequencySpinner.setEnabled(false);
                    dataSwitch.setEnabled(false);
                    dataUpdateAlarm.cancelAlarm(getContext());
                }
            }
        });

        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("updateData",isChecked);
                edit.commit();
            }
        });

        return rootView;

    }

}
