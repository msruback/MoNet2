package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class PlayerGeneralStatViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,winLossMeter,wins,losses;
    public TextView inkTitle,firstPlayedTitle,lastPlayedTitle;
    public TextView winText,lossText,inkedText,firstPlayedText,lastPlayedText;
    private Context context;

    public PlayerGeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_player_general_stats, parent, false));

        this.context = context;

        card = (RelativeLayout) itemView.findViewById(R.id.card);
        winLossMeter = (RelativeLayout) itemView.findViewById(R.id.WinLossMeter);
        wins = (RelativeLayout) itemView.findViewById(R.id.Wins);
        losses = (RelativeLayout) itemView.findViewById(R.id.Losses);

        inkTitle = (TextView) itemView.findViewById(R.id.InkedTitleText);
        firstPlayedTitle = (TextView) itemView.findViewById(R.id.FirstTitleText);
        lastPlayedTitle = (TextView) itemView.findViewById(R.id.LastTitleText);

        winText = (TextView) itemView.findViewById(R.id.WinText);
        lossText = (TextView) itemView.findViewById(R.id.LossText);
        inkedText = (TextView) itemView.findViewById(R.id.InkedText);
        firstPlayedText = (TextView) itemView.findViewById(R.id.FirstText);
        lastPlayedText = (TextView) itemView.findViewById(R.id.LastText);
    }

    public void manageHolder(){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        card.setClipToOutline(true);

        inkTitle.setTypeface(fontTitle);
        firstPlayedTitle.setTypeface(fontTitle);
        lastPlayedTitle.setTypeface(fontTitle);

        winText.setTypeface(font);
        lossText.setTypeface(font);
        inkedText.setTypeface(font);
        firstPlayedText.setTypeface(font);
        lastPlayedText.setTypeface(font);
    }
}
