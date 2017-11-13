package com.mattrubacky.monet2.dialog;

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
import com.mattrubacky.monet2.adapter.StagePickerAdapter;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class StagePickerDialog extends Dialog {
    int selected;
    ArrayList<Stage> stages;
    Stage result;
    public StagePickerDialog(Activity activity) {
        super(activity);
        result = new Stage();
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

        SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getContext());
        stages = new ArrayList<>();
        Stage anyStage = new Stage();
        anyStage.id = -1;
        anyStage.name = "Any";
        stages.add(anyStage);
        stages.addAll(splatnetSQLManager.getStages());

        final StagePickerAdapter stageAdapter = new StagePickerAdapter(getContext(),stages);

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
                result = stages.get(selected);
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

    public Stage getResult(){
        return result;
    }
}
