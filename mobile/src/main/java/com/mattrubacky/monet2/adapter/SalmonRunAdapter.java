package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.AddRun;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 *
 * This adapter populates the list of Salmon Run times into the SalmonRotationFragment, which is the last fragment in the Salmon Run Card ViewPager
 */

public class SalmonRunAdapter extends ArrayAdapter<SalmonRun> {
    public SalmonRunAdapter(Context context, ArrayList<SalmonRun> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_salmon_run, parent, false);
        }

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");

        final SalmonRun salmonRun = getItem(position);

        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setTypeface(font);

        SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");

        String startText = sdf.format(salmonRun.start);
        String endText = sdf.format(salmonRun.end);

        time.setText(startText + " to " + endText);

        return convertView;
    }
}
