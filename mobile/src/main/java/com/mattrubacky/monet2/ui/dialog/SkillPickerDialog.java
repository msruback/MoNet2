package com.mattrubacky.monet2.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.ListView.SkillPickerAdapter;
import com.mattrubacky.monet2.data.deserialized_entities.Skill;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class SkillPickerDialog extends Dialog {
    int selected;
    ArrayList<Skill> skills;
    Skill result;
    public SkillPickerDialog(Activity activity) {
        super(activity);
        result = new Skill();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_item_picker);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

        selected =-1;

        RelativeLayout card = findViewById(R.id.dialogCard);
        TextView title = findViewById(R.id.title);
        final ListView skillList = findViewById(R.id.ItemList);
        Button submit = findViewById(R.id.Submit);
        Button cancel = findViewById(R.id.Cancel);

        submit.setTypeface(titleFont);
        cancel.setTypeface(titleFont);

        title.setText("Pick Ability");

        title.setTypeface(titleFont);

        SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getContext());
        skills = new ArrayList<>();
        Skill anySkill = new Skill();
        anySkill.id = -1;
        anySkill.name = "Any";
        skills.add(anySkill);
        skills.addAll(splatnetSQLManager.getSkills());

        final SkillPickerAdapter skillAdapter = new SkillPickerAdapter(getContext(),skills);

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
                result = skills.get(selected);
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
    public Skill getResult(){
        return  result;
    }
}
