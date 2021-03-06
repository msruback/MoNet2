package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/28/2017.
 */

public class RegularAdapter extends ArrayAdapter<TimePeriod> {
    public RegularAdapter(Context context, ArrayList<TimePeriod> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimePeriod timePeriod = getItem(position);
        if(timePeriod==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_empty, parent, false);
        }else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_regular, parent, false);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
            TextView stageB = (TextView) convertView.findViewById(R.id.StageB);

            time.setTypeface(font);
            stageA.setTypeface(font);
            stageB.setTypeface(font);

            Date startTime = new Date((timePeriod.start * 1000));
            SimpleDateFormat sdf = new SimpleDateFormat("h a");
            String startText = sdf.format(startTime);
            Date endTime = new Date((timePeriod.end * 1000));
            String endText = sdf.format(endTime);

            time.setText(startText + " to " + endText);
            stageA.setText(timePeriod.a.name);
            stageB.setText(timePeriod.b.name);
        }
        return convertView;
    }
}