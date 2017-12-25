package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.helper.StageStats;

import java.text.SimpleDateFormat;

/**
 * Created by mattr on 12/25/2017.
 */

public class StageGeneralStatViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout card;
    public RelativeLayout turfMeter, turfWinLossMeter,turfWins,turfLosses;
    public RelativeLayout zoneMeter, zoneWinLossMeter,zoneWins,zoneLosses;
    public RelativeLayout rainmakerMeter,rainmakerWinLossMeter,rainmakerWins,rainmakerLosses;
    public RelativeLayout towerMeter,towerWinLossMeter,towerWins,towerLosses;
    public RelativeLayout clamMeter,clamWinLossMeter,clamWins,clamLosses;
    public TextView turfTitle, zoneTitle, rainmakerTitle, towerTitle, clamTitle,lastPlayedTitle;
    public TextView turfWinText,turfLossText,zoneWinText,zoneLossText,rainmakerWinText,rainmakerLossText,towerWinText,towerLossText,clamWinText,clamLossText, lastPlayedText;
    private Context context;

    public StageGeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_stage_general_stats, parent, false));

        this.context = context;

        card = (RelativeLayout) itemView.findViewById(R.id.card);

        turfMeter = (RelativeLayout) itemView.findViewById(R.id.turfMeter);
        turfWinLossMeter = (RelativeLayout) itemView.findViewById(R.id.TurfWinLossMeter);
        turfWins = (RelativeLayout) itemView.findViewById(R.id.TurfWins);
        turfLosses = (RelativeLayout) itemView.findViewById(R.id.TurfLosses);

        zoneMeter = (RelativeLayout) itemView.findViewById(R.id.zoneMeter);
        zoneWinLossMeter = (RelativeLayout) itemView.findViewById(R.id.ZoneWinLossMeter);
        zoneWins = (RelativeLayout) itemView.findViewById(R.id.ZoneWins);
        zoneLosses = (RelativeLayout) itemView.findViewById(R.id.ZoneLosses);

        rainmakerMeter = (RelativeLayout) itemView.findViewById(R.id.rainmakerMeter);
        rainmakerWinLossMeter = (RelativeLayout) itemView.findViewById(R.id.RainmakerWinLossMeter);
        rainmakerWins = (RelativeLayout) itemView.findViewById(R.id.RainmakerWins);
        rainmakerLosses = (RelativeLayout) itemView.findViewById(R.id.RainmakerLosses);

        towerMeter = (RelativeLayout) itemView.findViewById(R.id.towerMeter);
        towerWinLossMeter = (RelativeLayout) itemView.findViewById(R.id.TowerWinLossMeter);
        towerWins = (RelativeLayout) itemView.findViewById(R.id.TowerWins);
        towerLosses = (RelativeLayout) itemView.findViewById(R.id.TowerLosses);

        clamMeter = (RelativeLayout) itemView.findViewById(R.id.clamMeter);
        clamWinLossMeter = (RelativeLayout) itemView.findViewById(R.id.ClamWinLossMeter);
        clamWins = (RelativeLayout) itemView.findViewById(R.id.ClamWins);
        clamLosses = (RelativeLayout) itemView.findViewById(R.id.ClamLosses);

        turfTitle = (TextView) itemView.findViewById(R.id.TurfTitle);
        zoneTitle = (TextView) itemView.findViewById(R.id.ZoneTitle);
        rainmakerTitle = (TextView) itemView.findViewById(R.id.RainmakerTitle);
        towerTitle = (TextView) itemView.findViewById(R.id.TowerTitle);
        clamTitle = (TextView) itemView.findViewById(R.id.ClamTitle);
        lastPlayedTitle = (TextView) itemView.findViewById(R.id.LastTitleText);

        turfWinText = (TextView) itemView.findViewById(R.id.TurfWinText);
        turfLossText = (TextView) itemView.findViewById(R.id.TurfLossText);
        zoneWinText = (TextView) itemView.findViewById(R.id.ZoneWinText);
        zoneLossText = (TextView) itemView.findViewById(R.id.ZoneLossText);
        rainmakerWinText = (TextView) itemView.findViewById(R.id.RainmakerWinText);
        rainmakerLossText = (TextView) itemView.findViewById(R.id.RainmakerLossText);
        towerWinText = (TextView) itemView.findViewById(R.id.TowerWinText);
        towerLossText = (TextView) itemView.findViewById(R.id.TowerLossText);
        clamWinText = (TextView) itemView.findViewById(R.id.ClamWinText);
        clamLossText = (TextView) itemView.findViewById(R.id.ClamLossText);

        lastPlayedText = (TextView) itemView.findViewById(R.id.LastText);
    }

    public void manageHolder(StageStats stageStats) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        card.setClipToOutline(true);

        turfWinLossMeter.setClipToOutline(true);
        if(stageStats.turfWin==0&&stageStats.turfLose==0){
            turfMeter.setVisibility(View.GONE);
        }

        zoneWinLossMeter.setClipToOutline(true);
        if(stageStats.splatzonesLose==0&&stageStats.splatzonesWin==0){
            zoneMeter.setVisibility(View.GONE);
        }

        rainmakerWinLossMeter.setClipToOutline(true);
        if(stageStats.rainmakerWin==0&&stageStats.rainmakerLose==0){
            rainmakerMeter.setVisibility(View.GONE);
        }

        towerWinLossMeter.setClipToOutline(true);
        if(stageStats.towerWin==0&&stageStats.towerLose==0){
            towerMeter.setVisibility(View.GONE);
        }

        clamWinLossMeter.setClipToOutline(true);
        if(stageStats.clamWin==0&&stageStats.clamLose==0){
            clamMeter.setVisibility(View.GONE);
        }


        turfTitle.setTypeface(fontTitle);
        zoneTitle.setTypeface(fontTitle);
        rainmakerTitle.setTypeface(fontTitle);
        towerTitle.setTypeface(fontTitle);
        clamTitle.setTypeface(fontTitle);
        lastPlayedTitle.setTypeface(fontTitle);

        turfWinText.setTypeface(font);
        turfLossText.setTypeface(font);
        zoneWinText.setTypeface(font);
        zoneLossText.setTypeface(font);
        rainmakerWinText.setTypeface(font);
        rainmakerLossText.setTypeface(font);
        towerWinText.setTypeface(font);
        towerLossText.setTypeface(font);
        clamWinText.setTypeface(font);
        clamLossText.setTypeface(font);
        lastPlayedText.setTypeface(font);

        turfWinText.setText(String.valueOf(stageStats.turfWin));
        turfLossText.setText(String.valueOf(stageStats.turfLose));
        zoneWinText.setText(String.valueOf(stageStats.splatzonesWin));
        zoneLossText.setText(String.valueOf(stageStats.splatzonesLose));
        rainmakerWinText.setText(String.valueOf(stageStats.rainmakerWin));
        rainmakerLossText.setText(String.valueOf(stageStats.rainmakerLose));
        towerWinText.setText(String.valueOf(stageStats.towerWin));
        towerLossText.setText(String.valueOf(stageStats.towerLose));
        clamWinText.setText(String.valueOf(stageStats.clamWin));
        clamLossText.setText(String.valueOf(stageStats.clamLose));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String last = format.format(stageStats.lastPlayed*1000);
        lastPlayedText.setText(last);

        ViewGroup.LayoutParams layoutParams = turfWins.getLayoutParams();

        float total = stageStats.turfWin+stageStats.turfLose;
        float width = stageStats.turfWin/total;

        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        turfWins.setLayoutParams(layoutParams);

        layoutParams = turfLosses.getLayoutParams();
        width = stageStats.turfLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        turfLosses.setLayoutParams(layoutParams);

        layoutParams = zoneWins.getLayoutParams();
        total = stageStats.splatzonesWin+stageStats.splatzonesLose;
        width = stageStats.splatzonesWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        zoneWins.setLayoutParams(layoutParams);

        layoutParams = zoneLosses.getLayoutParams();
        width = stageStats.splatzonesLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        zoneLosses.setLayoutParams(layoutParams);

        layoutParams = rainmakerWins.getLayoutParams();
        total = stageStats.rainmakerWin+stageStats.rainmakerLose;
        width = stageStats.rainmakerWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        rainmakerWins.setLayoutParams(layoutParams);

        layoutParams = rainmakerLosses.getLayoutParams();
        width = stageStats.rainmakerLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        rainmakerLosses.setLayoutParams(layoutParams);

        layoutParams = towerWins.getLayoutParams();
        total = stageStats.towerWin+stageStats.towerLose;
        width = stageStats.towerWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        towerWins.setLayoutParams(layoutParams);

        layoutParams = towerLosses.getLayoutParams();
        width = stageStats.towerLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        towerLosses.setLayoutParams(layoutParams);

        layoutParams = clamWins.getLayoutParams();
        total = stageStats.clamWin+stageStats.clamLose;
        width = stageStats.clamWin/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        clamWins.setLayoutParams(layoutParams);

        layoutParams = clamLosses.getLayoutParams();
        width = stageStats.clamLose/total;
        width *= 250;
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        clamLosses.setLayoutParams(layoutParams);
    }
}
