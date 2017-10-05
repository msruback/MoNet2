package com.mattrubacky.monet2;


import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        final ArrayList<String> hours = new ArrayList<>();
        hours.add("1 Hour");
        hours.add("2 Hours");
        hours.add("4 Hours");
        hours.add("6 Hours");
        hours.add("8 Hours");
        hours.add("10 Hours");
        hours.add("12 Hours");

        final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        final Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        final RelativeLayout frequencyLayout = (RelativeLayout) rootView.findViewById(R.id.FrequencyLayout);
        final RelativeLayout dataLayout = (RelativeLayout) rootView.findViewById(R.id.DataLayout);

        final TextView autoTitle = (TextView) rootView.findViewById(R.id.autoUpdateTitle);
        final TextView autoText = (TextView) rootView.findViewById(R.id.AutoText);
        final TextView frequencyText = (TextView) rootView.findViewById(R.id.FrequencyText);
        final TextView dataText = (TextView) rootView.findViewById(R.id.DataText);
        TextView cookieTitle = (TextView) rootView.findViewById(R.id.cookieTitle);
        TextView cookieText = (TextView) rootView.findViewById(R.id.CookieText);
        TextView notificationTitle = (TextView) rootView.findViewById(R.id.alertTitle);
        TextView stageNotificationText = (TextView) rootView.findViewById(R.id.ModeAlertText);
        TextView gearNotificationText = (TextView) rootView.findViewById(R.id.GearAlertText);
        TextView salmonNotificationText = (TextView) rootView.findViewById(R.id.SalmonText);

        final Spinner frequencySpinner = (Spinner) rootView.findViewById(R.id.FrequencySpinner);



        final ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,hours) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                if (position <= hours.size()) {
                    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
                    ((TextView) v).setTypeface(typeface);
                }

                return v;
            }
        };

        frequencySpinner.setAdapter(hourAdapter);

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

        cookieTitle.setTypeface(fontTitle);
        cookieText.setTypeface(font);

        notificationTitle.setTypeface(fontTitle);
        stageNotificationText.setTypeface(font);
        gearNotificationText.setTypeface(font);
        salmonNotificationText.setTypeface(font);

        //Grab data as changed
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor edit = settings.edit();
                edit.putInt("updateInterval", position);
                edit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("autoUpdate", isChecked);
                edit.commit();
                if (isChecked) {
                    frequencyLayout.setAlpha(1);
                    dataLayout.setAlpha(1);
                    frequencySpinner.setEnabled(true);
                    dataSwitch.setEnabled(true);
                    dataUpdateAlarm.setAlarm(getContext());

                    ComponentName receiver = new ComponentName(getContext(), BootReciever.class);
                    PackageManager pm = getContext().getPackageManager();

                    pm.setComponentEnabledSetting(receiver,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                } else {
                    frequencyLayout.setAlpha((float) 0.5);
                    dataLayout.setAlpha((float) 0.5);
                    frequencySpinner.setEnabled(false);
                    dataSwitch.setEnabled(false);
                    dataUpdateAlarm.cancelAlarm(getContext());
                    ComponentName receiver = new ComponentName(getContext(), BootReciever.class);
                    PackageManager pm = getContext().getPackageManager();

                    pm.setComponentEnabledSetting(receiver,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
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
    private class HourAdapter extends ArrayAdapter<String> {
        public HourAdapter(Context context, ArrayList<String> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            ((TextView)convertView).setTypeface(font);
            ((TextView)convertView).setText(getItem(position));

            return convertView;
        }
    }

}
