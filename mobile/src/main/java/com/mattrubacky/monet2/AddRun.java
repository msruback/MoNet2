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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRun extends AppCompatActivity {
    SalmonRun salmonRun;
    TextView timeStartInputText,timeEndInputText;
    ImageView weapon1Image,weapon2Image,weapon3Image,weapon4Image;
    Weapon weapon1,weapon2,weapon3,weapon4;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_run);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("type");

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");

        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RelativeLayout timeStartInput = (RelativeLayout) findViewById(R.id.TimeStartInput);
        RelativeLayout timeEndInput = (RelativeLayout) findViewById(R.id.TimeEndInput);
        RelativeLayout weapon1Layout = (RelativeLayout) findViewById(R.id.Weapon1);
        RelativeLayout weapon2Layout = (RelativeLayout) findViewById(R.id.Weapon2);
        RelativeLayout weapon3Layout = (RelativeLayout) findViewById(R.id.Weapon3);
        RelativeLayout weapon4Layout = (RelativeLayout) findViewById(R.id.Weapon4);
        RelativeLayout submit = (RelativeLayout) findViewById(R.id.Submit);
        RelativeLayout delete = (RelativeLayout) findViewById(R.id.Delete);

        TextView timeStartText = (TextView) findViewById(R.id.TimeStartText);
        TextView timeEndText = (TextView) findViewById(R.id.TimeEndText);
        TextView stageText = (TextView) findViewById(R.id.StageText);
        TextView weaponsText = (TextView) findViewById(R.id.WeaponText);

        TextView submitText = (TextView) findViewById(R.id.SubmitText);
        TextView deleteText = (TextView) findViewById(R.id.DeleteText);

        final EditText stageInput = (EditText) findViewById(R.id.StageInput);

        weapon1Image = (ImageView) findViewById(R.id.WeaponImage1);
        weapon2Image = (ImageView) findViewById(R.id.WeaponImage2);
        weapon3Image = (ImageView) findViewById(R.id.WeaponImage3);
        weapon4Image = (ImageView) findViewById(R.id.WeaponImage4);

        timeStartInputText = (TextView) findViewById(R.id.TimeStartInputText);
        timeEndInputText = (TextView) findViewById(R.id.TimeEndInputText);

        switch (type){
            case "new":
                title.setText("Add Shift");
                delete.setVisibility(View.GONE);
                salmonRun = new SalmonRun();
                salmonRun.weapons = new ArrayList<>();
                break;
            case "edit":
                title.setText("Edit Shift");
                salmonRun = bundle.getParcelable("run");

                SimpleDateFormat sdf = new SimpleDateFormat("M/d/y h a");

                String timeText = sdf.format(salmonRun.startTime);
                timeStartInputText.setText(timeText);

                timeText = sdf.format(salmonRun.endTime);
                timeEndInputText.setText(timeText);

                stageInput.setText(salmonRun.stage);

                ImageHandler imageHandler = new ImageHandler();

                if(salmonRun.weapons.get(0)!=null) {
                    String imageDirName = salmonRun.weapons.get(0).name.toLowerCase().replace(" ", "_");
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(0).url;
                    weapon1 = salmonRun.weapons.get(0);
                    if (imageHandler.imageExists("weapon", imageDirName, getBaseContext())) {
                        weapon1Image.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getBaseContext()).load(url).into(weapon1Image);
                        imageHandler.downloadImage("weapon", imageDirName, url, getBaseContext());
                    }
                }

                if(salmonRun.weapons.get(1)!=null) {
                    String imageDirName = salmonRun.weapons.get(1).name.toLowerCase().replace(" ", "_");
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(1).url;
                    weapon2 = salmonRun.weapons.get(1);
                    if (imageHandler.imageExists("weapon", imageDirName, getBaseContext())) {
                        weapon2Image.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getBaseContext()).load(url).into(weapon2Image);
                        imageHandler.downloadImage("weapon", imageDirName, url, getBaseContext());
                    }
                }

                if(salmonRun.weapons.get(2)!=null) {
                    String imageDirName = salmonRun.weapons.get(2).name.toLowerCase().replace(" ", "_");
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(2).url;
                    weapon3 = salmonRun.weapons.get(2);
                    if (imageHandler.imageExists("weapon", imageDirName, getBaseContext())) {
                        weapon3Image.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getBaseContext()).load(url).into(weapon3Image);
                        imageHandler.downloadImage("weapon", imageDirName, url, getBaseContext());
                    }
                }

                if(salmonRun.weapons.get(3)!=null) {
                    String imageDirName = salmonRun.weapons.get(3).name.toLowerCase().replace(" ", "_");
                    String url = "https://app.splatoon2.nintendo.net" + salmonRun.weapons.get(3).url;
                    weapon4 = salmonRun.weapons.get(3);
                    if (imageHandler.imageExists("weapon", imageDirName, getBaseContext())) {
                        weapon4Image.setImageBitmap(imageHandler.loadImage("weapon", imageDirName));
                    } else {
                        Picasso.with(getBaseContext()).load(url).into(weapon4Image);
                        imageHandler.downloadImage("weapon", imageDirName, url, getBaseContext());
                    }
                }

                break;
        }


        timeStartText.setTypeface(font);
        timeEndText.setTypeface(font);
        stageText.setTypeface(font);
        weaponsText.setTypeface(font);
        submitText.setTypeface(font);
        deleteText.setTypeface(font);

        stageText.setTypeface(font);

        timeStartInputText.setTypeface(font);
        timeEndInputText.setTypeface(font);

        timeStartInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRun.this,true);
                datePickerDialog.show();
            }
        });

        timeEndInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRun.this,false);
                datePickerDialog.show();
            }
        });

        weapon1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeaponPickerDialog weaponPickerDialog = new WeaponPickerDialog(AddRun.this,1);
                weaponPickerDialog.show();
            }
        });
        weapon2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeaponPickerDialog weaponPickerDialog = new WeaponPickerDialog(AddRun.this,2);
                weaponPickerDialog.show();
            }
        });
        weapon3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeaponPickerDialog weaponPickerDialog = new WeaponPickerDialog(AddRun.this,3);
                weaponPickerDialog.show();
            }
        });
        weapon4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeaponPickerDialog weaponPickerDialog = new WeaponPickerDialog(AddRun.this,4);
                weaponPickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salmonRun.weapons = new ArrayList<Weapon>();
                salmonRun.weapons.add(weapon1);
                salmonRun.weapons.add(weapon2);
                salmonRun.weapons.add(weapon3);
                salmonRun.weapons.add(weapon4);
                salmonRun.stage = stageInput.getText().toString();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                Gson gson = new Gson();
                SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule","{\"schedule\":[]}"),SalmonSchedule.class);
                if(schedule.schedule==null){
                    schedule.schedule = new ArrayList<SalmonRun>();
                }
                if(type.equals("edit")){
                    Intent intent = getIntent();
                    int replacementPosition = intent.getExtras().getInt("position");
                    schedule.schedule.remove(replacementPosition);
                }
                boolean isInserted = false;
                for(int i = 0; i<schedule.schedule.size()&&!isInserted;i++){
                    if(schedule.schedule.get(i).startTime>salmonRun.startTime){
                        schedule.schedule.add(i,salmonRun);
                        isInserted = true;
                    }
                }
                if(!isInserted){
                    schedule.schedule.add(salmonRun);
                }

                SharedPreferences.Editor edit = settings.edit();
                String json = gson.toJson(schedule);
                edit.putString("salmonRunSchedule",json);
                edit.commit();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fragment",0);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                Gson gson = new Gson();
                SalmonSchedule schedule = gson.fromJson(settings.getString("salmonRunSchedule",""),SalmonSchedule.class);
                Intent callingIntent = getIntent();
                int replacementPosition = callingIntent.getExtras().getInt("position");
                schedule.schedule.remove(replacementPosition);

                SharedPreferences.Editor edit = settings.edit();
                String json = gson.toJson(schedule);
                edit.putString("salmonRunSchedule",json);
                edit.commit();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    class DatePickerDialog extends Dialog {
        Boolean isStart;
        public DatePickerDialog(Activity activity, boolean isStart) {
            super(activity);
            this.isStart = isStart;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_date_picker);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");
            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            card.setClipToOutline(true);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isStart) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),0,0,0);
                        salmonRun.startTime = calendar.getTimeInMillis();
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddRun.this,true);
                        timePickerDialog.show();
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),0,0,0);
                        salmonRun.endTime = calendar.getTimeInMillis();
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddRun.this,false);
                        timePickerDialog.show();
                    }
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

    class TimePickerDialog extends Dialog {
        Boolean isStart;
        public TimePickerDialog(Activity activity, boolean isStart) {
            super(activity);
            this.isStart = isStart;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setContentView(R.layout.dialog_time_picker);
            Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

            RelativeLayout card = (RelativeLayout) findViewById(R.id.dialogCard);
            final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            card.setClipToOutline(true);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/y h a");
                    if(isStart) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(salmonRun.startTime);
                        calendar.add(Calendar.HOUR,timePicker.getCurrentHour());
                        int hour = timePicker.getCurrentHour();
                        salmonRun.startTime = calendar.getTimeInMillis();
                        String startText = sdf.format(salmonRun.startTime);
                        timeStartInputText.setText(startText);
                    }else{
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(salmonRun.endTime);
                        calendar.add(Calendar.HOUR,timePicker.getCurrentHour());
                        int hour = timePicker.getCurrentHour();
                        salmonRun.endTime = calendar.getTimeInMillis();
                        String endText = sdf.format(salmonRun.endTime);
                        timeEndInputText.setText(endText);
                    }
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

    class WeaponPickerDialog extends Dialog {
        int weaponNum;
        int selected;
        ArrayList<Weapon> weapons;
        public WeaponPickerDialog(Activity activity, int weaponNum) {
            super(activity);
            this.weaponNum = weaponNum;
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
            final ListView weaponList = (ListView) findViewById(R.id.ItemList);
            Button submit = (Button) findViewById(R.id.Submit);
            Button cancel = (Button) findViewById(R.id.Cancel);

            title.setText("Pick Weapon");

            title.setTypeface(titleFont);

            SplatnetSQL splatnetSQL = new SplatnetSQL(getApplicationContext());
            weapons = splatnetSQL.getWeapons();

            final WeaponAdapter weaponAdapter = new WeaponAdapter(getApplicationContext(),weapons);

            weaponList.setAdapter(weaponAdapter);

            card.setClipToOutline(true);
            weaponList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selected = position;
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Weapon weapon = weapons.get(selected);
                    String url = "https://app.splatoon2.nintendo.net"+weapon.url;

                    ImageHandler imageHandler = new ImageHandler();
                    String imageDirName = weapon.name.toLowerCase().replace(" ","_");

                    switch(weaponNum){
                        case 1:
                            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                                weapon1Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
                            }else{
                                Picasso.with(getContext()).load(url).into(weapon1Image);
                                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
                            }
                            weapon1 = weapon;
                            break;
                        case 2:
                            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                                weapon2Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
                            }else{
                                Picasso.with(getContext()).load(url).into(weapon2Image);
                                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
                            }
                            weapon2 = weapon;
                            break;
                        case 3:
                            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                                weapon3Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
                            }else{
                                Picasso.with(getContext()).load(url).into(weapon3Image);
                                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
                            }
                            weapon3 = weapon;
                            break;
                        case 4:
                            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                                weapon4Image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
                            }else{
                                Picasso.with(getContext()).load(url).into(weapon4Image);
                                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
                            }
                            weapon4 = weapon;
                            break;
                    }
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
    private class WeaponAdapter extends ArrayAdapter<Weapon> {
        public WeaponAdapter(Context context, ArrayList<Weapon> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_weapon_picker, parent, false);
            }
            Weapon weapon = getItem(position);

            ImageView image = (ImageView) convertView.findViewById(R.id.Image);
            TextView weaponName = (TextView) convertView.findViewById(R.id.WeaponName);

            weaponName.setText(weapon.name);

            String url = "https://app.splatoon2.nintendo.net"+weapon.url;

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = weapon.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                image.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(image);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }

            return convertView;
        }
    }
}
