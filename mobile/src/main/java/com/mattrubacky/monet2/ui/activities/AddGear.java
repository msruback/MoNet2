package com.mattrubacky.monet2.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.data.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.data.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.ui.dialog.GearPickerDialog;
import com.mattrubacky.monet2.ui.dialog.SkillPickerDialog;
import com.mattrubacky.monet2.data.stats.GearStats;
import com.mattrubacky.monet2.backend.ImageHandler;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddGear extends AppCompatActivity {

    GearPickerDialog gearPickerDialog;
    SkillPickerDialog skillPickerDialog;
    GearStats hanger;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getIntent().getExtras();

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        setContentView(R.layout.activity_add_gear);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final TextView gearInput = findViewById(R.id.GearInput);
        TextView gearText = findViewById(R.id.GearText);
        TextView abilityText = findViewById(R.id.AbilityText);
        TextView submitText = findViewById(R.id.SubmitText);

        final ImageView main = findViewById(R.id.Main);
        final ImageView sub1 = findViewById(R.id.Sub1);
        final ImageView sub2 = findViewById(R.id.Sub2);
        final ImageView sub3 = findViewById(R.id.Sub3);

        gearText.setTypeface(font);
        gearInput.setTypeface(font);
        abilityText.setTypeface(font);
        submitText.setTypeface(font);

        if(bundle.getString("type").equals("add")) {
//            hanger = new GearStats();
//            hanger.skills = new GearSkills();
//
//            hanger.skills.subs = new ArrayList<>();
//            hanger.skills.subs.add(null);
//            hanger.skills.subs.add(null);
//            hanger.skills.subs.add(null);

            isEdit = false;

            title.setText("Add Gear");

            gearInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gearPickerDialog = new GearPickerDialog(AddGear.this);
                    gearPickerDialog.show();
                    gearPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Gear gear = gearPickerDialog.getResult();
                            gearInput.setText(gear.name);
                            hanger.gear = gear;
                        }
                    });
                }
            });
        }else{
            hanger = bundle.getParcelable("hanger");

            title.setText("Edit Gear");

            isEdit = true;

            gearInput.setText(hanger.gear.name);

            ImageHandler imageHandler = new ImageHandler();
            String dirName = hanger.getSkills().main.name.toLowerCase().replaceAll(" ","_");
            String url = "https://app.splatoon2.nintendo.net" +hanger.getSkills().main.url;
            if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
                main.setImageBitmap(imageHandler.loadImage("ability",dirName));
            }else{
                Picasso.with(getApplicationContext()).load(url).into(main);
                imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
            }

            if(hanger.getSkills().subs.size()>0&&hanger.getSkills().subs.get(0)!=null) {
                dirName = hanger.getSkills().subs.get(0).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.getSkills().main.url;
                if (imageHandler.imageExists("ability", dirName, getApplicationContext())) {
                    sub1.setImageBitmap(imageHandler.loadImage("ability", dirName));
                } else {
                    Picasso.with(getApplicationContext()).load(url).into(sub1);
                    imageHandler.downloadImage("ability", dirName, url, getApplicationContext());
                }
            }
            if(hanger.getSkills().subs.size()>1&&hanger.getSkills().subs.get(1)!=null) {
                dirName = hanger.getSkills().subs.get(1).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.getSkills().main.url;
                if (imageHandler.imageExists("ability", dirName, getApplicationContext())) {
                    sub2.setImageBitmap(imageHandler.loadImage("ability", dirName));
                } else {
                    Picasso.with(getApplicationContext()).load(url).into(sub2);
                    imageHandler.downloadImage("ability", dirName, url, getApplicationContext());
                }
            }
            if(hanger.getSkills().subs.size()>2&&hanger.getSkills().subs.get(2)!=null) {
                dirName = hanger.getSkills().subs.get(2).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.getSkills().main.url;
                if (imageHandler.imageExists("ability", dirName, getApplicationContext())) {
                    sub3.setImageBitmap(imageHandler.loadImage("ability", dirName));
                } else {
                    Picasso.with(getApplicationContext()).load(url).into(sub3);
                    imageHandler.downloadImage("ability", dirName, url, getApplicationContext());
                }
            }
        }

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPickerDialog = new SkillPickerDialog(AddGear.this);
                skillPickerDialog.show();
                skillPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Skill skill = skillPickerDialog.getResult();
                        hanger.getSkills().main = skill;
                        ImageHandler imageHandler = new ImageHandler();
                        String dirName = skill.name.toLowerCase().replaceAll(" ","_");
                        String url = "https://app.splatoon2.nintendo.net" +skill.url;
                        if(imageHandler.imageExists("stage",dirName,getApplicationContext())){
                            main.setImageBitmap(imageHandler.loadImage("stage",dirName));
                        }else{
                            Picasso.with(getApplicationContext()).load(url).into(main);
                            imageHandler.downloadImage("stage",dirName,url,getApplicationContext());
                        }
                    }
                });
            }
        });

        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPickerDialog = new SkillPickerDialog(AddGear.this);
                skillPickerDialog.show();
                skillPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Skill skill = skillPickerDialog.getResult();
                        hanger.getSkills().subs.remove(0);
                        hanger.getSkills().subs.add(0,skill);
                        ImageHandler imageHandler = new ImageHandler();
                        String dirName = skill.name.toLowerCase().replaceAll(" ","_");
                        String url = "https://app.splatoon2.nintendo.net" +skill.url;
                        if(imageHandler.imageExists("stage",dirName,getApplicationContext())){
                            sub1.setImageBitmap(imageHandler.loadImage("stage",dirName));
                        }else{
                            Picasso.with(getApplicationContext()).load(url).into(sub1);
                            imageHandler.downloadImage("stage",dirName,url,getApplicationContext());
                        }
                    }
                });
            }
        });

        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPickerDialog = new SkillPickerDialog(AddGear.this);
                skillPickerDialog.show();
                skillPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Skill skill = skillPickerDialog.getResult();
                        hanger.getSkills().subs.remove(1);
                        hanger.getSkills().subs.add(1,skill);
                        ImageHandler imageHandler = new ImageHandler();
                        String dirName = skill.name.toLowerCase().replaceAll(" ","_");
                        String url = "https://app.splatoon2.nintendo.net" +skill.url;
                        if(imageHandler.imageExists("stage",dirName,getApplicationContext())){
                            sub2.setImageBitmap(imageHandler.loadImage("stage",dirName));
                        }else{
                            Picasso.with(getApplicationContext()).load(url).into(sub2);
                            imageHandler.downloadImage("stage",dirName,url,getApplicationContext());
                        }
                    }
                });
            }
        });

        sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillPickerDialog = new SkillPickerDialog(AddGear.this);
                skillPickerDialog.show();
                skillPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Skill skill = skillPickerDialog.getResult();
                        hanger.getSkills().subs.remove(2);
                        hanger.getSkills().subs.add(2,skill);
                        ImageHandler imageHandler = new ImageHandler();
                        String dirName = skill.name.toLowerCase().replaceAll(" ","_");
                        String url = "https://app.splatoon2.nintendo.net" +skill.url;
                        if(imageHandler.imageExists("stage",dirName,getApplicationContext())){
                            sub3.setImageBitmap(imageHandler.loadImage("stage",dirName));
                        }else{
                            Picasso.with(getApplicationContext()).load(url).into(sub3);
                            imageHandler.downloadImage("stage",dirName,url,getApplicationContext());
                        }
                    }
                });
            }
        });

        submitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplatnetSQLManager database = new SplatnetSQLManager(getApplicationContext());
                ArrayList<GearStats> hangers = new ArrayList<GearStats>();
                hangers.add(hanger);
                database.insertCloset(hangers);
                if(isEdit){
                    Intent intent = new Intent(AddGear.this, ClosetDetail.class);
                    Bundle intentBundle = new Bundle();
                    hanger.calcStats(getApplicationContext());
                    intentBundle.putParcelable("stats",hanger);
                    intent.putExtras(intentBundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(AddGear.this, MainActivity.class);
                    intent.putExtra("fragment", 2);
                    intent.putExtra("stats", 2);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
