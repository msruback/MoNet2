package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.RotationDetail;
import com.mattrubacky.monet2.deserialized.CurrentSplatfest;
import com.mattrubacky.monet2.deserialized.SalmonSchedule;
import com.mattrubacky.monet2.deserialized.Schedules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mattr on 12/29/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<String> {
    Schedules schedules;
    SalmonSchedule salmonSchedule;
    CurrentSplatfest currentSplatfest;
    public ScheduleAdapter(Context context, ArrayList<String> input, Schedules schedules, SalmonSchedule salmonSchedule, CurrentSplatfest splatfest) {
        super(context, 0, input);
        this.schedules = schedules;
        this.salmonSchedule = salmonSchedule;
        this.currentSplatfest = splatfest;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getContext().getAssets(), "Paintball.otf");

        switch(getItem(position)){
            case "regular":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_regular, parent, false);
                TextView TurfMode = convertView.findViewById(R.id.TurfMode);
                TextView TurfStageA = convertView.findViewById(R.id.TurfStageA);
                TextView TurfStageB = convertView.findViewById(R.id.TurfStageB);

                TurfMode.setTypeface(fontTitle);
                TurfStageA.setTypeface(font);
                TurfStageB.setTypeface(font);

                TurfStageA.setText(schedules.regular.get(0).a.name);
                TurfStageB.setText(schedules.regular.get(0).b.name);

                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","regular");
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        return false;
                    }
                });
                break;
            case "ranked":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_ranked, parent, false);
                TextView RankMode = convertView.findViewById(R.id.RankMode);
                TextView RankStageA = convertView.findViewById(R.id.RankStageA);
                TextView RankStageB = convertView.findViewById(R.id.RankStageB);

                RankMode.setTypeface(fontTitle);
                RankStageA.setTypeface(font);
                RankStageB.setTypeface(font);

                RankMode.setText(schedules.ranked.get(0).rule.name);
                RankStageA.setText(schedules.ranked.get(0).a.name);
                RankStageB.setText(schedules.ranked.get(0).b.name);
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","ranked");
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        return false;
                    }
                });
                break;
            case "league":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_league, parent, false);

                TextView LeagueMode = convertView.findViewById(R.id.LeagueMode);
                TextView LeagueStageA = convertView.findViewById(R.id.LeagueStageA);
                TextView LeagueStageB = convertView.findViewById(R.id.LeagueStageB);

                LeagueMode.setTypeface(fontTitle);
                LeagueStageA.setTypeface(font);
                LeagueStageB.setTypeface(font);

                LeagueMode.setText(schedules.league.get(0).rule.name);
                LeagueStageA.setText(schedules.league.get(0).a.name);
                LeagueStageB.setText(schedules.league.get(0).b.name);

                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","league");
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        return false;
                    }
                });
                break;
            case "fes":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_festival, parent, false);
                RelativeLayout SplatfestCard = convertView.findViewById(R.id.FesCard);
                RelativeLayout SplatfestZigZag = convertView.findViewById(R.id.FesZigZag);
                RelativeLayout Alpha = convertView.findViewById(R.id.Alpha);
                RelativeLayout Bravo = convertView.findViewById(R.id.Bravo);


                TextView SplatfestMode = convertView.findViewById(R.id.FesMode);
                TextView SplatfestStageA = convertView.findViewById(R.id.FesStageA);
                TextView SplatfestStageB = convertView.findViewById(R.id.FesStageB);
                TextView SplatfestStageC = convertView.findViewById(R.id.FesStageC);

                SplatfestMode.setTypeface(font);
                SplatfestStageA.setTypeface(font);
                SplatfestStageB.setTypeface(font);
                SplatfestStageC.setTypeface(font);

                String alphaColor = currentSplatfest.splatfests.get(0).colors.alpha.getColor();

                String bravoColor = currentSplatfest.splatfests.get(0).colors.bravo.getColor();

                SplatfestCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(alphaColor)));
                SplatfestZigZag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));

                Alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(alphaColor)));
                Bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bravoColor)));

                SplatfestStageA.setText(schedules.splatfest.get(0).a.name);
                SplatfestStageB.setText(schedules.splatfest.get(0).b.name);
                SplatfestStageC.setText(currentSplatfest.splatfests.get(0).stage.name);

                SplatfestCard.setClipToOutline(true);

                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","fes");
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        return false;
                    }
                });
                break;
            case "salmon":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_salmon, parent, false);

                TextView salmonTitle = convertView.findViewById(R.id.SalmonTitle);
                TextView SalmonShift1 = convertView.findViewById(R.id.ShiftTime1);
                TextView SalmonShift2 = convertView.findViewById(R.id.ShiftTime2);

                salmonTitle.setTypeface(fontTitle);
                SalmonShift1.setTypeface(font);
                SalmonShift2.setTypeface(font);

                SimpleDateFormat sdf = new SimpleDateFormat("M/d h a");
                String startText = sdf.format(salmonSchedule.details.get(0).start*1000);
                String endText = sdf.format(salmonSchedule.details.get(0).end*1000);
                SalmonShift1.setText(startText + " to " + endText);
                if(salmonSchedule.details.size()>1){
                    startText = sdf.format(salmonSchedule.details.get(1).start*1000);
                    endText = sdf.format(salmonSchedule.details.get(1).end*1000);
                    SalmonShift2.setText(startText + " to " + endText);
                }
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getContext(), RotationDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type","salmon");
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        return false;
                    }
                });
                break;
            case "empty":
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_empty, parent, false);
                break;
        }

        return convertView;
    }
}