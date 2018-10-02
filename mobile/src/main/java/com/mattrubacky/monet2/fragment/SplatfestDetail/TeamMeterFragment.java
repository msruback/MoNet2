package com.mattrubacky.monet2.fragment.SplatfestDetail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.splatoon.SplatfestColors;

/**
 * Created by mattr on 11/17/2017.
 */

public class TeamMeterFragment extends Fragment {
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_team_meter, container, false);
        Bundle bundle = this.getArguments();

        int[] stats = bundle.getIntArray("stats");
        float average = bundle.getFloat("average");
        SplatfestColors colors = bundle.getParcelable("colors");

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout LowerWhisker = (RelativeLayout) rootView.findViewById(R.id.LowerWhisker);
        RelativeLayout Box = (RelativeLayout) rootView.findViewById(R.id.Box);
        RelativeLayout LowerBox = (RelativeLayout) rootView.findViewById(R.id.LowerBox);
        RelativeLayout UpperBox = (RelativeLayout) rootView.findViewById(R.id.UpperBox);
        RelativeLayout UpperWhisker = (RelativeLayout) rootView.findViewById(R.id.UpperWhisker);
        RelativeLayout Player = (RelativeLayout) rootView.findViewById(R.id.Player);

        Box.setClipToOutline(true);

        UpperBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors.bravo.getColor())));
        LowerBox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors.alpha.getColor())));

        TextView Minimum = (TextView) rootView.findViewById(R.id.Minimum);
        TextView LowerQuartile = (TextView) rootView.findViewById(R.id.LowerQuartile);
        TextView Median = (TextView) rootView.findViewById(R.id.Median);
        TextView UpperQuartile = (TextView) rootView.findViewById(R.id.UpperQuartile);
        TextView Maximum = (TextView) rootView.findViewById(R.id.Maximum);

        Minimum.setTypeface(font);
        LowerQuartile.setTypeface(font);
        Median.setTypeface(font);
        UpperQuartile.setTypeface(font);
        Maximum.setTypeface(font);

        Minimum.setText(String.valueOf(stats[0]));
        LowerQuartile.setText(String.valueOf(stats[1]));
        Median.setText(String.valueOf(stats[2]));
        UpperQuartile.setText(String.valueOf(stats[3]));
        Maximum.setText(String.valueOf(stats[4]));

        if(stats[1]==stats[0]){
            LowerQuartile.setVisibility(View.GONE);
        }
        if(stats[2]==stats[1]){
            LowerQuartile.setVisibility(View.GONE);
        }
        if(stats[2]==stats[0]){
            Median.setVisibility(View.GONE);
        }
        if(stats[3]==stats[2]){
            Median.setVisibility(View.GONE);
        }
        if(stats[4]==stats[3]){
            UpperQuartile.setVisibility(View.GONE);
        }
        if(stats[4]==stats[2]){
            Median.setVisibility(View.GONE);
        }

        float range = stats[4] - stats[0];

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) Median.getLayoutParams();

        //Need to position the inkMedian TextView so as to line it up with the center line
        float width = (((stats[2] - stats[1])/range) * (270));
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        Median.setLayoutParams(marginLayoutParams);

        ViewGroup.LayoutParams layoutParams = LowerWhisker.getLayoutParams();
        width = ((stats[1] - stats[0])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        LowerWhisker.setLayoutParams(layoutParams);

        layoutParams = Box.getLayoutParams();
        width = ((stats[3] - stats[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        Box.setLayoutParams(layoutParams);

        layoutParams = LowerBox.getLayoutParams();
        width = ((stats[2] - stats[1])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        LowerBox.setLayoutParams(layoutParams);

        layoutParams = UpperBox.getLayoutParams();
        width = ((stats[3] - stats[2])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        UpperBox.setLayoutParams(layoutParams);

        layoutParams = UpperWhisker.getLayoutParams();
        width = ((stats[4] - stats[3])/range) * (270);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        UpperWhisker.setLayoutParams(layoutParams);

        marginLayoutParams = (ViewGroup.MarginLayoutParams) Player.getLayoutParams();
        width = (float) ((average - stats[0])/range) * (270);
        marginLayoutParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        Player.setLayoutParams(marginLayoutParams);

        return rootView;
    }
}
