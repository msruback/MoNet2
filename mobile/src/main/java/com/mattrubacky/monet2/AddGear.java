package com.mattrubacky.monet2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.GearSkills;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.dialog.GearPickerDialog;
import com.mattrubacky.monet2.dialog.SkillPickerDialog;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddGear extends AppCompatActivity {

    GearPickerDialog gearPickerDialog;
    SkillPickerDialog skillPickerDialog;
    ClosetHanger hanger;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getIntent().getExtras();

        Typeface font = Typeface.createFromAsset(getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        setContentView(R.layout.activity_add_gear);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final TextView gearInput = (TextView) findViewById(R.id.GearInput);
        TextView gearText = (TextView) findViewById(R.id.GearText);
        TextView abilityText = (TextView) findViewById(R.id.AbilityText);
        TextView submitText = (TextView) findViewById(R.id.SubmitText);

        final ImageView main = (ImageView) findViewById(R.id.Main);
        final ImageView sub1 = (ImageView) findViewById(R.id.Sub1);
        final ImageView sub2 = (ImageView) findViewById(R.id.Sub2);
        final ImageView sub3 = (ImageView) findViewById(R.id.Sub3);

        gearText.setTypeface(font);
        gearInput.setTypeface(font);
        abilityText.setTypeface(font);
        submitText.setTypeface(font);

        if(bundle.getString("type").equals("add")) {
            hanger = new ClosetHanger();
            hanger.skills = new GearSkills();
            hanger.skills.subs = new ArrayList<>();
            hanger.skills.subs.add(null);
            hanger.skills.subs.add(null);
            hanger.skills.subs.add(null);

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
            String dirName = hanger.skills.main.name.toLowerCase().replaceAll(" ","_");
            String url = "https://app.splatoon2.nintendo.net" +hanger.skills.main.url;
            if(imageHandler.imageExists("ability",dirName,getApplicationContext())){
                main.setImageBitmap(imageHandler.loadImage("ability",dirName));
            }else{
                Picasso.with(getApplicationContext()).load(url).into(main);
                imageHandler.downloadImage("ability",dirName,url,getApplicationContext());
            }

            if(hanger.skills.subs.size()>0&&hanger.skills.subs.get(0)!=null) {
                dirName = hanger.skills.subs.get(0).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.skills.main.url;
                if (imageHandler.imageExists("ability", dirName, getApplicationContext())) {
                    sub1.setImageBitmap(imageHandler.loadImage("ability", dirName));
                } else {
                    Picasso.with(getApplicationContext()).load(url).into(sub1);
                    imageHandler.downloadImage("ability", dirName, url, getApplicationContext());
                }
            }
            if(hanger.skills.subs.size()>1&&hanger.skills.subs.get(1)!=null) {
                dirName = hanger.skills.subs.get(1).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.skills.main.url;
                if (imageHandler.imageExists("ability", dirName, getApplicationContext())) {
                    sub2.setImageBitmap(imageHandler.loadImage("ability", dirName));
                } else {
                    Picasso.with(getApplicationContext()).load(url).into(sub2);
                    imageHandler.downloadImage("ability", dirName, url, getApplicationContext());
                }
            }
            if(hanger.skills.subs.size()>2&&hanger.skills.subs.get(2)!=null) {
                dirName = hanger.skills.subs.get(2).name.toLowerCase().replaceAll(" ", "_");
                url = "https://app.splatoon2.nintendo.net" + hanger.skills.main.url;
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
                        hanger.skills.main = skill;
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
                        hanger.skills.subs.remove(0);
                        hanger.skills.subs.add(0,skill);
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
                        hanger.skills.subs.remove(1);
                        hanger.skills.subs.add(1,skill);
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
                        hanger.skills.subs.remove(2);
                        hanger.skills.subs.add(2,skill);
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
                ArrayList<ClosetHanger> hangers = new ArrayList<ClosetHanger>();
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
