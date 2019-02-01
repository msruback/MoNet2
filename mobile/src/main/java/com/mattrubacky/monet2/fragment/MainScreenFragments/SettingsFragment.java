package com.mattrubacky.monet2.fragment.MainScreenFragments;


import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.mattrubacky.monet2.reciever.BootReciever;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.dialog.*;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    DataUpdateAlarm dataUpdateAlarm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        final RelativeLayout frequencyLayout = rootView.findViewById(R.id.FrequencyLayout);
        final RelativeLayout dataLayout = rootView.findViewById(R.id.DataLayout);
        final RelativeLayout cookieLayout = rootView.findViewById(R.id.cookie);
        final RelativeLayout modeLayout = rootView.findViewById(R.id.modeAlert);
        final RelativeLayout gearLayout = rootView.findViewById(R.id.gearAlert);

        final TextView autoTitle = rootView.findViewById(R.id.autoUpdateTitle);
        final TextView autoText = rootView.findViewById(R.id.AutoText);
        final TextView frequencyText = rootView.findViewById(R.id.FrequencyText);
        final TextView dataText = rootView.findViewById(R.id.DataText);
        TextView cookieTitle = rootView.findViewById(R.id.cookieTitle);
        TextView cookieText = rootView.findViewById(R.id.CookieText);
        TextView notificationTitle = rootView.findViewById(R.id.alertTitle);
        TextView stageNotificationText = rootView.findViewById(R.id.ModeAlertText);
        TextView gearNotificationText = rootView.findViewById(R.id.GearAlertText);
        TextView salmonNotificationText = rootView.findViewById(R.id.SalmonText);

        final Spinner frequencySpinner = rootView.findViewById(R.id.FrequencySpinner);



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

        final Switch autoSwitch = rootView.findViewById(R.id.AutoSwitch);
        final Switch dataSwitch = rootView.findViewById(R.id.DataSwitch);
        Switch salmonSwitch = rootView.findViewById(R.id.SalmonSwitch);

        Boolean checked = settings.getBoolean("autoUpdate",false);
        autoSwitch.setChecked(checked);
        dataSwitch.setChecked(settings.getBoolean("updateData",false));
        frequencySpinner.setSelection(settings.getInt("updateInterval",0));
        salmonSwitch.setChecked(settings.getBoolean("salmonNotifications",false));

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

        cookieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieDialog dialog = new CookieDialog(getActivity());
                dialog.show();
            }
        });

        modeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StageNotificationPickerDialog dialog = new StageNotificationPickerDialog(getActivity());
                dialog.show();
            }
        });

        gearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GearNotificationPickerDialog dialog = new GearNotificationPickerDialog(getActivity());
                dialog.show();
            }
        });
        salmonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("salmonNotifications",isChecked);
                edit.commit();
            }
        });

        return rootView;

    }

}
