package com.mattrubacky.monet2;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.adapter.GearPickerAdapter;
import com.mattrubacky.monet2.adapter.SkillPickerAdapter;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.Skill;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

public class AddGear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_gear);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
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

            SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getApplicationContext());
            gearList = splatnetSQLManager.getGear();

            final GearPickerAdapter gearAdapter = new GearPickerAdapter(getApplicationContext(),gearList);

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

            SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getApplicationContext());
            skills = new ArrayList<>();
            Skill anySkill = new Skill();
            anySkill.id = -1;
            anySkill.name = "Any";
            skills.add(anySkill);
            skills.addAll(splatnetSQLManager.getSkills());

            final SkillPickerAdapter skillAdapter = new SkillPickerAdapter(getApplicationContext(),skills);

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
}
