package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.data.stats.StageStats;

import java.text.SimpleDateFormat;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/25/2017.
 */

public class StageGeneralStatViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout card;
    private RelativeLayout turfMeter, turfWinLossMeter,turfWins,turfLosses;
    private RelativeLayout zoneMeter, zoneWinLossMeter,zoneWins,zoneLosses;
    private RelativeLayout rainmakerMeter,rainmakerWinLossMeter,rainmakerWins,rainmakerLosses;
    private RelativeLayout towerMeter,towerWinLossMeter,towerWins,towerLosses;
    private RelativeLayout clamMeter,clamWinLossMeter,clamWins,clamLosses;
    private TextView turfTitle, zoneTitle, rainmakerTitle, towerTitle, clamTitle,lastPlayedTitle;
    private TextView turfWinText,turfLossText,zoneWinText,zoneLossText,rainmakerWinText,rainmakerLossText,towerWinText,towerLossText,clamWinText,clamLossText, lastPlayedText;
    private Context context;

    public StageGeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_stage_general_stats, parent, false));

        this.context = context;

        card = itemView.findViewById(R.id.card);

        turfMeter = itemView.findViewById(R.id.turfMeter);
        turfWinLossMeter = itemView.findViewById(R.id.TurfWinLossMeter);
        turfWins = itemView.findViewById(R.id.TurfWins);
        turfLosses = itemView.findViewById(R.id.TurfLosses);

        zoneMeter = itemView.findViewById(R.id.zoneMeter);
        zoneWinLossMeter = itemView.findViewById(R.id.ZoneWinLossMeter);
        zoneWins = itemView.findViewById(R.id.ZoneWins);
        zoneLosses = itemView.findViewById(R.id.ZoneLosses);

        rainmakerMeter = itemView.findViewById(R.id.rainmakerMeter);
        rainmakerWinLossMeter = itemView.findViewById(R.id.RainmakerWinLossMeter);
        rainmakerWins = itemView.findViewById(R.id.RainmakerWins);
        rainmakerLosses = itemView.findViewById(R.id.RainmakerLosses);

        towerMeter = itemView.findViewById(R.id.towerMeter);
        towerWinLossMeter = itemView.findViewById(R.id.TowerWinLossMeter);
        towerWins = itemView.findViewById(R.id.TowerWins);
        towerLosses = itemView.findViewById(R.id.TowerLosses);

        clamMeter = itemView.findViewById(R.id.clamMeter);
        clamWinLossMeter = itemView.findViewById(R.id.ClamWinLossMeter);
        clamWins = itemView.findViewById(R.id.ClamWins);
        clamLosses = itemView.findViewById(R.id.ClamLosses);

        turfTitle = itemView.findViewById(R.id.TurfTitle);
        zoneTitle = itemView.findViewById(R.id.ZoneTitle);
        rainmakerTitle = itemView.findViewById(R.id.RainmakerTitle);
        towerTitle = itemView.findViewById(R.id.TowerTitle);
        clamTitle = itemView.findViewById(R.id.ClamTitle);
        lastPlayedTitle = itemView.findViewById(R.id.LastTitleText);

        turfWinText = itemView.findViewById(R.id.TurfWinText);
        turfLossText = itemView.findViewById(R.id.TurfLossText);
        zoneWinText = itemView.findViewById(R.id.ZoneWinText);
        zoneLossText = itemView.findViewById(R.id.ZoneLossText);
        rainmakerWinText = itemView.findViewById(R.id.RainmakerWinText);
        rainmakerLossText = itemView.findViewById(R.id.RainmakerLossText);
        towerWinText = itemView.findViewById(R.id.TowerWinText);
        towerLossText = itemView.findViewById(R.id.TowerLossText);
        clamWinText = itemView.findViewById(R.id.ClamWinText);
        clamLossText = itemView.findViewById(R.id.ClamLossText);

        lastPlayedText = itemView.findViewById(R.id.LastText);
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
