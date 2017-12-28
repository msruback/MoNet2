package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mattr on 12/28/2017.
 */

public class FestivalAdapter extends ArrayAdapter<TimePeriod> {
    Splatfest splatfest;
    public FestivalAdapter(Context context, ArrayList<TimePeriod> input, Splatfest splatfest) {
        super(context, 0, input);
        this.splatfest = splatfest;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_festival, parent, false);
        }
        TimePeriod timePeriod = getItem(position);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView stageA = (TextView) convertView.findViewById(R.id.StageA);
        TextView stageB = (TextView) convertView.findViewById(R.id.StageB);
        TextView stageC = (TextView) convertView.findViewById(R.id.StageC);

        time.setTypeface(font);
        stageA.setTypeface(font);
        stageB.setTypeface(font);
        stageC.setTypeface(font);

        Date startTime = new Date((timePeriod.start*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("h a");
        String startText = sdf.format(startTime);
        Date endTime = new Date((timePeriod.end*1000));
        String endText = sdf.format(endTime);

        time.setText(startText+" to "+endText);
        stageA.setText(timePeriod.a.name);
        stageB.setText(timePeriod.b.name);
        stageC.setText(splatfest.stage.name);

        return convertView;
    }
}
