package com.mattrubacky.monet2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNotification extends AppCompatActivity {

    private boolean isGear,isEdit;
    private GearNotification gearNotification;
    private StageNotification stageNotification;
    TextView gearInput,abilityInput,stageInput;

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
                    GearPickerDialog gearPickerDialog = new GearPickerDialog(AddNotification.this);
                    gearPickerDialog.show();
                }
            });

            abilityInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SkillPickerDialog skillPickerDialog = new SkillPickerDialog(AddNotification.this);
                    skillPickerDialog.show();
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

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("fragment", 4);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Select a Gear",Toast.LENGTH_SHORT);
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

    class GearPickerDialog extends Dialog {
        int selected;
        ArrayList<Gear> gearList;
        public GearPickerDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_item_picker);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            selected =-1;

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            TextView title = (TextView) findViewById(R.id.title);
            final ListView gearListView = (ListView) findViewById(R.id.ItemList);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            title.setText("Pick Gear");

            title.setTypeface(titleFont);

            SplatnetSQL splatnetSQL = new SplatnetSQL(getApplicationContext());
            gearList = splatnetSQL.getGear();

            final GearAdapter gearAdapter = new GearAdapter(getApplicationContext(),gearList);

            gearListView.setAdapter(gearAdapter);

            card.setClipToOutline(true);
            gearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected = position;
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gearNotification.gear = gearList.get(selected);
                    gearInput.setText(gearNotification.gear.name);
                    dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    class SkillPickerDialog extends Dialog {
        int selected;
        ArrayList<Skill> skills;
        public SkillPickerDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_item_picker);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            selected =-1;

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            TextView title = (TextView) findViewById(R.id.title);
            final ListView skillList = (ListView) findViewById(R.id.ItemList);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            title.setText("Pick Ability");

            title.setTypeface(titleFont);

            SplatnetSQL splatnetSQL = new SplatnetSQL(getApplicationContext());
            skills = new ArrayList<>();
            Skill anySkill = new Skill();
            anySkill.id = -1;
            anySkill.name = "Any";
            skills.add(anySkill);
            skills.addAll(splatnetSQL.getSkills());

            final SkillAdapter skillAdapter = new SkillAdapter(getApplicationContext(),skills);

            skillList.setAdapter(skillAdapter);

            card.setClipToOutline(true);
            skillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected = position;
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gearNotification.skill = skills.get(selected);
                    abilityInput.setText(gearNotification.skill.name);
                    dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    class StagePickerDialog extends Dialog {
        int selected;
        ArrayList<Stage> stages;
        public StagePickerDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_item_picker);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            selected =-1;

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            TextView title = (TextView) findViewById(R.id.title);
            final ListView stageList = (ListView) findViewById(R.id.ItemList);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            title.setText("Pick Stage");

            title.setTypeface(titleFont);

            SplatnetSQL splatnetSQL = new SplatnetSQL(getApplicationContext());
            stages = new ArrayList<>();
            Stage anyStage = new Stage();
            anyStage.id = -1;
            anyStage.name = "Any";
            stages.add(anyStage);
            stages.addAll(splatnetSQL.getStages());

            final StageAdapter stageAdapter = new StageAdapter(getApplicationContext(),stages);

            stageList.setAdapter(stageAdapter);

            card.setClipToOutline(true);
            stageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected = position;
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stageNotification.stage = stages.get(selected);
                    stageInput.setText(stageNotification.stage.name);
                    dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private class GearAdapter extends ArrayAdapter<Gear> {
        public GearAdapter(Context context, ArrayList<Gear> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_weapon_picker, parent, false);
            }
            Gear gear = getItem(position);

            RelativeLayout imageBackground = (RelativeLayout) convertView.findViewById(R.id.image);

            switch (gear.kind) {
                case "head":
                    imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    imageBackground.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    break;
            }

            ImageView image = (ImageView) convertView.findViewById(R.id.Image);
            TextView weaponName = (TextView) convertView.findViewById(R.id.WeaponName);

            weaponName.setText(gear.name);

            String url = "https://app.splatoon2.nintendo.net"+gear.url;

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = gear.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("gear",imageDirName,getContext())){
                image.setImageBitmap(imageHandler.loadImage("gear",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(image);
                imageHandler.downloadImage("gear",imageDirName,url,getContext());
            }

            return convertView;
        }
    }

    private class SkillAdapter extends ArrayAdapter<Skill> {
        public SkillAdapter(Context context, ArrayList<Skill> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_skill_picker, parent, false);
            }
            Skill skill = getItem(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.Image);
            TextView weaponName = (TextView) convertView.findViewById(R.id.SkillName);
            if(skill.id ==-1) {
                weaponName.setText("Any");
                image.setImageDrawable(getResources().getDrawable(R.drawable.skill_blank));
            }else{
                weaponName.setText(skill.name);

                String url = "https://app.splatoon2.nintendo.net" + skill.url;

                ImageHandler imageHandler = new ImageHandler();
                String imageDirName = skill.name.toLowerCase().replace(" ", "_");
                if (imageHandler.imageExists("ability", imageDirName, getContext())) {
                    image.setImageBitmap(imageHandler.loadImage("ability", imageDirName));
                } else {
                    Picasso.with(getContext()).load(url).into(image);
                    imageHandler.downloadImage("ability", imageDirName, url, getContext());
                }
            }

            return convertView;
        }
    }

    private class StageAdapter extends ArrayAdapter<Stage> {
        public StageAdapter(Context context, ArrayList<Stage> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stage_picker, parent, false);
            }
            Stage stage = getItem(position);

            TextView stageName = (TextView) convertView.findViewById(R.id.StageName);

            stageName.setText(stage.name);

            return convertView;
        }
    }
}
