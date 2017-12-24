package com.mattrubacky.monet2.adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class UserStatsViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout generalCard,inkCard,killCard,deathCard,specialCard,noCard;
    public RelativeLayout winLossMeter,wins,losses,inkMeter,killMeter,deathMeter,specialMeter;
    public TextView totalInkTitle,firstPlayedTitle,lastPlayedTitle,inkTitle,killTitle,deathTitle,specialTitle,noStats;
    public TextView winText,lossText,inkedText,firstPlayedText,lastPlayedText;
    public UserStatsViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_user_stats, parent, false));

        generalCard = (RelativeLayout) itemView.findViewById(R.id.generalStats);
        inkCard = (RelativeLayout) itemView.findViewById(R.id.inkStats);
        killCard = (RelativeLayout) itemView.findViewById(R.id.killStats);
        deathCard = (RelativeLayout) itemView.findViewById(R.id.deathStats);
        specialCard = (RelativeLayout) itemView.findViewById(R.id.specialStats);
        noCard = (RelativeLayout) itemView.findViewById(R.id.noStats);

        winLossMeter = (RelativeLayout) itemView.findViewById(R.id.WinLossMeter);
        wins = (RelativeLayout) itemView.findViewById(R.id.Wins);
        losses = (RelativeLayout) itemView.findViewById(R.id.Losses);
        inkMeter = (RelativeLayout) itemView.findViewById(R.id.InkMeter);
        killMeter = (RelativeLayout) itemView.findViewById(R.id.KillMeter);
        deathMeter = (RelativeLayout) itemView.findViewById(R.id.DeathMeter);
        specialMeter = (RelativeLayout) itemView.findViewById(R.id.SpecialMeter);

        totalInkTitle = (TextView) itemView.findViewById(R.id.InkedTitleText);
        firstPlayedTitle = (TextView) itemView.findViewById(R.id.FirstTitleText);
        lastPlayedTitle = (TextView) itemView.findViewById(R.id.LastTitleText);
        inkTitle = (TextView) itemView.findViewById(R.id.InkTitle);
        killTitle = (TextView) itemView.findViewById(R.id.KillTitle);
        deathTitle = (TextView) itemView.findViewById(R.id.DeathTitle);
        specialTitle = (TextView) itemView.findViewById(R.id.SpecialTitle);
        noStats = (TextView) itemView.findViewById(R.id.NoStatsText);

        winText = (TextView) itemView.findViewById(R.id.WinText);
        lossText = (TextView) itemView.findViewById(R.id.LossText);
        inkedText = (TextView) itemView.findViewById(R.id.InkedText);
        firstPlayedText = (TextView) itemView.findViewById(R.id.FirstText);
        lastPlayedText = (TextView) itemView.findViewById(R.id.LastText);
    }
}
