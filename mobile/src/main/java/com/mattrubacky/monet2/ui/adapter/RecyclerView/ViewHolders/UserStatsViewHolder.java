package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class UserStatsViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout generalCard,inkCard,killCard,deathCard,specialCard,noCard;
    private RelativeLayout winLossMeter,wins,losses,inkMeter,killMeter,deathMeter,specialMeter;
    private TextView totalInkTitle,firstPlayedTitle,lastPlayedTitle,inkTitle,killTitle,deathTitle,specialTitle,noStats;
    private TextView winText,lossText,inkedText,firstPlayedText,lastPlayedText;
    public UserStatsViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_user_stats, parent, false));

        generalCard = itemView.findViewById(R.id.generalStats);
        inkCard = itemView.findViewById(R.id.inkStats);
        killCard = itemView.findViewById(R.id.killStats);
        deathCard = itemView.findViewById(R.id.deathStats);
        specialCard = itemView.findViewById(R.id.specialStats);
        noCard = itemView.findViewById(R.id.noStats);

        winLossMeter = itemView.findViewById(R.id.WinLossMeter);
        wins = itemView.findViewById(R.id.Wins);
        losses = itemView.findViewById(R.id.Losses);
        inkMeter = itemView.findViewById(R.id.InkMeter);
        killMeter = itemView.findViewById(R.id.KillMeter);
        deathMeter = itemView.findViewById(R.id.DeathMeter);
        specialMeter = itemView.findViewById(R.id.SpecialMeter);

        totalInkTitle = itemView.findViewById(R.id.InkedTitleText);
        firstPlayedTitle = itemView.findViewById(R.id.FirstTitleText);
        lastPlayedTitle = itemView.findViewById(R.id.LastTitleText);
        inkTitle = itemView.findViewById(R.id.InkTitle);
        killTitle = itemView.findViewById(R.id.KillTitle);
        deathTitle = itemView.findViewById(R.id.DeathTitle);
        specialTitle = itemView.findViewById(R.id.SpecialTitle);
        noStats = itemView.findViewById(R.id.NoStatsText);

        winText = itemView.findViewById(R.id.WinText);
        lossText = itemView.findViewById(R.id.LossText);
        inkedText = itemView.findViewById(R.id.InkedText);
        firstPlayedText = itemView.findViewById(R.id.FirstText);
        lastPlayedText = itemView.findViewById(R.id.LastText);
    }
}
