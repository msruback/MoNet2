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
import com.mattrubacky.monet2.adapter.GearPickerAdapter;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class GearPickerDialog extends Dialog {
    int selected;
    ArrayList<Gear> gearList;
    Gear result;

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

        SplatnetSQLManager splatnetSQLManager = new SplatnetSQLManager(getContext());
        gearList = splatnetSQLManager.getGear();

        final GearPickerAdapter gearAdapter = new GearPickerAdapter(getContext(),gearList);

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
                result = gearList.get(selected);
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

    public Gear getResult(){
        return result;
    }
}