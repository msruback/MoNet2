package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.SalmonRunDetail;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/28/2017.
 */

public class SalmonAdapter extends ArrayAdapter<SalmonRunDetail> {
    public SalmonAdapter(Context context, ArrayList<SalmonRunDetail> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SalmonRunDetail detail = getItem(position);
        if(detail!=null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_salmon, parent, false);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
            TextView weapons = (TextView) convertView.findViewById(R.id.Weapons);

            time.setTypeface(font);
            stageA.setTypeface(font);
            weapons.setTypeface(font);

            Date startTime = new Date((detail.start * 1000));
            SimpleDateFormat sdf = new SimpleDateFormat("h a");
            String startText = sdf.format(startTime);
            Date endTime = new Date((detail.end * 1000));
            String endText = sdf.format(endTime);

            time.setText(startText + " to " + endText);
            stageA.setText(detail.stage.name);
            StringBuilder builder = new StringBuilder();
            builder.append(detail.weapons.get(0).name);
            builder.append(", ");
            builder.append(detail.weapons.get(1).name);
            builder.append(",\n");
            builder.append(detail.weapons.get(2).name);
            builder.append(", and ");
            builder.append(detail.weapons.get(3).name);
            weapons.setText(builder.toString());
        }else{
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_empty, parent, false);
        }
        return convertView;
    }
}
