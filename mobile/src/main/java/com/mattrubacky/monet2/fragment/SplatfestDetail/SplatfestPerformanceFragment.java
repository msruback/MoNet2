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
import com.mattrubacky.monet2.deserialized.Splatfest;
import com.mattrubacky.monet2.deserialized.SplatfestColors;
import com.mattrubacky.monet2.deserialized.SplatfestStats;
import com.mattrubacky.monet2.sqlite.SplatnetContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mattr on 11/17/2017.
 */

public class SplatfestPerformanceFragment extends Fragment{
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_performance, container, false);
        Bundle bundle = this.getArguments();

        Splatfest splatfest = bundle.getParcelable("splatfest");
        SplatfestStats stats = bundle.getParcelable("stats");

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        RelativeLayout winLossMeter = (RelativeLayout) rootView.findViewById(R.id.WinLossMeter);
        RelativeLayout wins = (RelativeLayout) rootView.findViewById(R.id.Wins);
        RelativeLayout losses = (RelativeLayout) rootView.findViewById(R.id.Losses);
        RelativeLayout meterLayout = (RelativeLayout) rootView.findViewById(R.id.winOutline);
        RelativeLayout disconnects = (RelativeLayout) rootView.findViewById(R.id.disconnectLayout);
        RelativeLayout timePlayedLayout = (RelativeLayout) rootView.findViewById(R.id.timeLayout);

        wins.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.alpha.getColor())));
        losses.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        TextView winText = (TextView) rootView.findViewById(R.id.WinText);
        TextView lossText = (TextView) rootView.findViewById(R.id.LossText);
        TextView gradeTitle = (TextView) rootView.findViewById(R.id.GradeTitleText);
        TextView gradeText = (TextView) rootView.findViewById(R.id.GradeText);
        TextView disconnectTitle = (TextView) rootView.findViewById(R.id.DisconnectTitleText);
        TextView disconnectText = (TextView) rootView.findViewById(R.id.DisconnectText);
        TextView powerTitle = (TextView) rootView.findViewById(R.id.PowerTitleText);
        TextView powerText = (TextView) rootView.findViewById(R.id.PowerText);
        TextView playedTitle = (TextView) rootView.findViewById(R.id.TimeTitleText);
        TextView playedText = (TextView) rootView.findViewById(R.id.TimeText);

        winText.setTypeface(font);
        lossText.setTypeface(font);
        gradeTitle.setTypeface(fontTitle);
        gradeText.setTypeface(font);
        disconnectTitle.setTypeface(fontTitle);
        disconnectText.setTypeface(font);
        powerTitle.setTypeface(fontTitle);
        powerText.setTypeface(font);
        playedTitle.setTypeface(fontTitle);
        playedText.setTypeface(font);

        winText.setText(String.valueOf(stats.wins));
        lossText.setText(String.valueOf(stats.losses));
        gradeText.setText(stats.grade);
        disconnectText.setText(String.valueOf(stats.disconnects));
        powerText.setText(String.valueOf(stats.power));

        gradeTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        disconnectTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        powerTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));
        playedTitle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(splatfest.colors.bravo.getColor())));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        stats.timePlayed+=calendar.getTimeInMillis();
        SimpleDateFormat format = new SimpleDateFormat("h:mm");
        playedText.setText(format.format(stats.timePlayed));


        winLossMeter.setClipToOutline(true);

        ViewGroup.LayoutParams layoutParams = wins.getLayoutParams();

        float total = stats.wins+stats.losses+stats.disconnects;
        float width = stats.wins/total;

        if(total==0){
            meterLayout.setVisibility(View.GONE);
            disconnects.setVisibility(View.GONE);
            timePlayedLayout.setVisibility(View.GONE);
        }

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        wins.setLayoutParams(layoutParams);

        layoutParams = losses.getLayoutParams();
        width = stats.losses/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        losses.setLayoutParams(layoutParams);

        return rootView;
    }
}
