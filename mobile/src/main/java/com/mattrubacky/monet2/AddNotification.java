package com.mattrubacky.monet2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mattrubacky.monet2.adapter.GearPickerAdapter;
import com.mattrubacky.monet2.adapter.SkillPickerAdapter;
import com.mattrubacky.monet2.adapter.StagePickerAdapter;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.dialog.GearPickerDialog;
import com.mattrubacky.monet2.dialog.SkillPickerDialog;
import com.mattrubacky.monet2.dialog.StagePickerDialog;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

public class AddNotification extends AppCompatActivity {

    private boolean isGear,isEdit;
    private GearNotification gearNotification;
    private StageNotification stageNotification;
    TextView gearInput,abilityInput,stageInput;

    StagePickerDialog stagePickerDialog;
    GearPickerDialog gearPickerDialog;
    SkillPickerDialog skillPickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getIntent().getExtras();
        isGear = bundle.getBoolean("isGear");
        isEdit = bundle.getBoolean("isEdit");

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");

        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");


        if(isGear){

            setContentView(R.layout.activity_add_gear_notification);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView title = (TextView) findViewById(R.id.title);
            title.setTypeface(fontTitle);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            RelativeLayout Submit = (RelativeLayout) findViewById(R.id.Submit);
            final RelativeLayout Delete = (RelativeLayout) findViewById(R.id.Delete);

            TextView gearText = (TextView) findViewById(R.id.GearText);
            gearInput = (TextView) findViewById(R.id.GearInput);
            TextView abilityText = (TextView) findViewById(R.id.AbilityText);
            abilityInput = (TextView) findViewById(R.id.AbilityInput);
            TextView submitText = (TextView) findViewById(R.id.SubmitText);
            TextView deleteText = (TextView) findViewById(R.id.DeleteText);

            gearText.setTypeface(font);
            gearInput.setTypeface(font);
            abilityText.setTypeface(font);
            abilityInput.setTypeface(font);
            submitText.setTypeface(font);
            deleteText.setTypeface(font);

            if(isEdit){
                title.setText("Edit Notification");
                gearNotification = bundle.getParcelable("notification");
                gearInput.setText(gearNotification.gear.name);
                if(gearNotification.skill!=null) {
                    abilityInput.setText(gearNotification.skill.name);
                }
            }else{
                title.setText("Add Notification");
                Delete.setVisibility(View.GONE);
                gearNotification = new GearNotification();
                gearNotification.skill = new Skill();
                gearNotification.skill.name = "Any";
                gearNotification.skill.id = -1;
            }


            gearInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gearPickerDialog = new GearPickerDialog(AddNotification.this);
                    gearPickerDialog.show();
                    gearPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Gear gear = gearPickerDialog.getResult();
                            if(gear!=null) {
                                gearInput.setText(gear.name);
                                gearNotification.gear = gear;
                            }
                        }
                    });
                }
            });

            abilityInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skillPickerDialog = new SkillPickerDialog(AddNotification.this);
                    skillPickerDialog.show();
                    skillPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Skill skill = skillPickerDialog.getResult();
                            if(skill!= null) {
                                abilityInput.setText(skill.name);
                                gearNotification.skill = skill;
                            }
                        }
                    });
                }
            });

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gearNotification.gear!=null) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        Gson gson = new Gson();
                        GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications", "{\"notifications\":[]}"), GearNotifications.class);
                        if (isEdit) {
                            gearNotifications.notifications.remove(bundle.getInt("position"));
                        }
                        gearNotifications.notifications.add(gearNotification);

                        SharedPreferences.Editor edit = settings.edit();
                        String json = gson.toJson(gearNotifications);
                        edit.putString("gearNotifications", json);
                        edit.commit();

                        Intent intent = new Intent(AddNotification.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("fragment", 4);
                        startActivity(intent);
                    }else{
                        Toast.makeText(AddNotification.this,"Please Select a Gear",Toast.LENGTH_SHORT);
                    }

                }
            });

            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    Gson gson = new Gson();
                    GearNotifications gearNotifications = gson.fromJson(settings.getString("gearNotifications","{\"notifications\":[]}"),GearNotifications.class);

                    gearNotifications.notifications.remove(bundle.getInt("position"));

                    SharedPreferences.Editor edit = settings.edit();
                    String json = gson.toJson(gearNotifications);
                    edit.putString("gearNotifications",json);
                    edit.commit();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("fragment",4);
                    startActivity(intent);


                }
            });


        }else{
            //Stage Notification
            setContentView(R.layout.activity_add_stage_notification);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView title = (TextView) findViewById(R.id.title);
            title.setTypeface(fontTitle);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            RelativeLayout Submit = (RelativeLayout) findViewById(R.id.Submit);
            final RelativeLayout Delete = (RelativeLayout) findViewById(R.id.Delete);

            final ArrayList<String> modes = new ArrayList<>();
            modes.add("Any");
            modes.add("Regular");
            modes.add("Ranked");
            modes.add("League");

            final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,modes) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    if (position <= modes.size()) {
                        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
                        ((TextView) v).setTypeface(typeface);
                    }

                    return v;
                }
            };

            final ArrayList<String> rules = new ArrayList<>();
            rules.add("Any");
            rules.add("Splat Zones");
            rules.add("Tower Control");
            rules.add("Rainmaker");

            final ArrayAdapter<String> ruleAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,rules) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    if (position <= rules.size()) {
                        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
                        ((TextView) v).setTypeface(typeface);
                    }

                    return v;
                }
            };


            TextView stageText = (TextView) findViewById(R.id.StageText);
            stageInput = (TextView) findViewById(R.id.StageInput);
            TextView modeText = (TextView) findViewById(R.id.TypeText);
            TextView ruleText = (TextView) findViewById(R.id.RuleText);
            TextView submitText = (TextView) findViewById(R.id.SubmitText);
            TextView deleteText = (TextView) findViewById(R.id.DeleteText);

            final Spinner modeSpinner = (Spinner) findViewById(R.id.TypeSpinner);
            final Spinner ruleSpinner = (Spinner) findViewById(R.id.RuleSpinner);

            modeSpinner.setAdapter(modeAdapter);
            ruleSpinner.setAdapter(ruleAdapter);

            stageText.setTypeface(font);
            stageInput.setTypeface(font);
            modeText.setTypeface(font);
            ruleText.setTypeface(font);
            submitText.setTypeface(font);
            deleteText.setTypeface(font);

            if(isEdit){
                title.setText("Edit Notification");
                stageNotification = bundle.getParcelable("notification");
                stageInput.setText(stageNotification.stage.name);
                switch (stageNotification.rule){
                    case "splat_zones":
                        ruleSpinner.setSelection(1);
                        break;
                    case "tower_control":
                        ruleSpinner.setSelection(2);
                        break;
                    case "rainmaker":
                        ruleSpinner.setSelection(3);
                        break;
                    default:
                        ruleSpinner.setSelection(0);
                        break;
                }
                switch (stageNotification.type){
                    case "regular":
                        modeSpinner.setSelection(1);
                        ruleSpinner.setEnabled(false);
                        ruleSpinner.setSelection(0);
                        stageNotification.rule = "any";
                        break;
                    case "gachi":
                        modeSpinner.setSelection(2);
                        break;
                    case "league":
                        modeSpinner.setSelection(3);
                        break;
                    default:
                        modeSpinner.setSelection(0);
                        break;
                }
            }else{
                title.setText("Add Notification");
                Delete.setVisibility(View.GONE);
                stageNotification = new StageNotification();
            }

            stageInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stagePickerDialog = new StagePickerDialog(AddNotification.this);
                    stagePickerDialog.show();
                    stagePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Stage stage = stagePickerDialog.getResult();
                            stageInput.setText(stage.name);
                        }
                    });
                }
            });


            modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==1){
                        ruleSpinner.setSelection(0);
                        ruleSpinner.setEnabled(false);
                    }else{
                        ruleSpinner.setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stageNotification.stage==null){
                        stageNotification.stage = new Stage();
                        stageNotification.stage.id = -1;
                        stageNotification.stage.name = "Any";
                    }
                    switch(modeSpinner.getSelectedItemPosition()){
                        case 0:
                            stageNotification.type = "any";
                            break;
                        case 1:
                            stageNotification.type = "regular";
                            break;
                        case 2:
                            stageNotification.type = "gachi";
                            break;
                        case 3:
                            stageNotification.type = "league";
                            break;

                    }
                    switch (ruleSpinner.getSelectedItemPosition()){
                        case 0:
                            stageNotification.rule = "any";
                            break;
                        case 1:
                            stageNotification.rule = "splat_zones";
                            break;
                        case 2:
                            stageNotification.rule = "tower_control";
                            break;
                        case 3:
                            stageNotification.rule = "rainmaker";
                    }

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    Gson gson = new Gson();
                    StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications","{\"notifications\":[]}"),StageNotifications.class);
                    if(isEdit){
                        stageNotifications.notifications.remove(bundle.getInt("position"));
                    }
                    stageNotifications.notifications.add(stageNotification);

                    SharedPreferences.Editor edit = settings.edit();
                    String json = gson.toJson(stageNotifications);
                    edit.putString("stageNotifications",json);
                    edit.commit();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("fragment",4);
                    startActivity(intent);
                }
            });

            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    Gson gson = new Gson();
                    StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications","{\"notifications\":[]}"),StageNotifications.class);
                    if(isEdit){
                        stageNotifications.notifications.remove(bundle.getInt("position"));
                    }

                    SharedPreferences.Editor edit = settings.edit();
                    String json = gson.toJson(stageNotifications);
                    edit.putString("stageNotifications",json);
                    edit.commit();

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("fragment",4);
                    startActivity(intent);
                }
            });

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
