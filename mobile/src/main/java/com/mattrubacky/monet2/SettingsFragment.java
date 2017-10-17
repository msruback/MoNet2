package com.mattrubacky.monet2;


import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mattrubacky.monet2.deserialized.*;

import com.squareup.picasso.Picasso;

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
        final RelativeLayout cookieLayout = (RelativeLayout) rootView.findViewById(R.id.cookie);
        final RelativeLayout modeLayout = (RelativeLayout) rootView.findViewById(R.id.modeAlert);
        final RelativeLayout gearLayout = (RelativeLayout) rootView.findViewById(R.id.gearAlert);

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
        Switch salmonSwitch = (Switch) rootView.findViewById(R.id.SalmonSwitch);

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

                    SalmonAlarm salmonAlarm = new SalmonAlarm();
                    salmonAlarm.setAlarm(getContext());

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

                    SalmonAlarm salmonAlarm = new SalmonAlarm();
                    salmonAlarm.cancelAlarm(getContext());

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



    class GearNotificationPickerDialog extends Dialog {
        int selected;
        ArrayList<GearNotification> notificationList;
        public GearNotificationPickerDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_manage_notifications);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            selected =-1;

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            TextView title = (TextView) findViewById(R.id.title);
            final ListView notificationListView = (ListView) findViewById(R.id.ItemList);
            Button addNotification = (Button) findViewById(R.id.AddNotification);

            title.setText("Notifications");

            title.setTypeface(titleFont);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            Gson gson = new Gson();
            final GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications","{\"notifications\":[]}"),GearNotifications.class);

            notificationList = gearNotifications.notifications;

            final GearNotificationAdapter gearAdapter = new GearNotificationAdapter(getContext(),notificationList);

            notificationListView.setAdapter(gearAdapter);

            card.setClipToOutline(true);

            addNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    Intent intent = new Intent(getContext(), AddNotification.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isGear",true);
                    bundle.putBoolean("isEdit",false);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }
    }
    class StageNotificationPickerDialog extends Dialog {
        int selected;
        ArrayList<StageNotification> notificationList;
        public StageNotificationPickerDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_manage_notifications);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            selected =-1;

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            TextView title = (TextView) findViewById(R.id.title);
            final ListView notificationListView = (ListView) findViewById(R.id.ItemList);
            Button addNotification = (Button) findViewById(R.id.AddNotification);

            title.setText("Notifications");

            title.setTypeface(titleFont);

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            Gson gson = new Gson();
            final StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications","{\"notifications\":[]}"),StageNotifications.class);

            notificationList = stageNotifications.notifications;

            final StageNotificationAdapter stageAdapter = new StageNotificationAdapter(getContext(),notificationList);

            notificationListView.setAdapter(stageAdapter);

            card.setClipToOutline(true);

            addNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    Intent intent = new Intent(getContext(), AddNotification.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isGear",false);
                    bundle.putBoolean("isEdit",false);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }
    }

    private class GearNotificationAdapter extends ArrayAdapter<GearNotification> {
        public GearNotificationAdapter(Context context, ArrayList<GearNotification> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gear_notification, parent, false);
            }
            final GearNotification notification = getItem(position);
            final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            RelativeLayout skillLayout = (RelativeLayout) convertView.findViewById(R.id.skillImage);

            TextView gearName = (TextView) convertView.findViewById(R.id.GearName);

            ImageView gearImage = (ImageView) convertView.findViewById(R.id.GearImage);
            ImageView skillImage = (ImageView) convertView.findViewById(R.id.SkillImage);
            ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

            gearName.setTypeface(font);
            gearName.setText(notification.gear.name);

            ImageHandler imageHandler = new ImageHandler();

            String url = "https://app.splatoon2.nintendo.net"+notification.gear.url;
            String location = notification.gear.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("gear",location,getContext())){
                gearImage.setImageBitmap(imageHandler.loadImage("gear",location));
            }else{
                Picasso.with(getContext()).load(url).into(gearImage);
                imageHandler.downloadImage("gear",location,url,getContext());
            }
            if(notification.skill!=null&&notification.skill.id!=-1) {
                url = "https://app.splatoon2.nintendo.net" + notification.skill.url;
                location = notification.skill.name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", location, getContext())) {
                    skillImage.setImageBitmap(imageHandler.loadImage("ability", location));
                } else {
                    Picasso.with(getContext()).load(url).into(skillImage);
                    imageHandler.downloadImage("ability", location, url, getContext());
                }
            }else{
                skillImage.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
            }


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AddNotification.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isGear",true);
                    bundle.putBoolean("isEdit",true);
                    bundle.putParcelable("notification",notification);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            return convertView;
        }
    }
    private class StageNotificationAdapter extends ArrayAdapter<StageNotification> {
        public StageNotificationAdapter(Context context, ArrayList<StageNotification> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stage_notification, parent, false);
            }
            final StageNotification notification = getItem(position);

            final Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            ImageView type = (ImageView) convertView.findViewById(R.id.TypeImage);

            TextView rule = (TextView) convertView.findViewById(R.id.Rule);
            TextView stage = (TextView) convertView.findViewById(R.id.Stage);

            rule.setTypeface(font);
            stage.setTypeface(font);

            rule.setText(notification.rule);
            stage.setText(notification.stage.name);

            switch(notification.type){
                case "regular":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_regular));
                    break;
                case "gachi":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_ranked));
                    break;
                case "league":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_league));
                    break;
            }

            ImageView editButton = (ImageView) convertView.findViewById(R.id.EditButton);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AddNotification.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isGear",false);
                    bundle.putBoolean("isEdit",true);
                    bundle.putParcelable("notification",notification);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
