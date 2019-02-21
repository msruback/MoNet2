package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class GeneralStatViewHolder extends RecyclerView.ViewHolder{

    private RelativeLayout card,winLossMeter,wins,losses;
    private TextView inkTitle,lastPlayedTitle;
    private TextView winText,lossText,inkedText,lastPlayedText;
    private Context context;

    public GeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_general_stats, parent, false));

        this.context = context;

        card = itemView.findViewById(R.id.card);
        winLossMeter = itemView.findViewById(R.id.WinLossMeter);
        wins = itemView.findViewById(R.id.Wins);
        losses = itemView.findViewById(R.id.Losses);

        inkTitle = itemView.findViewById(R.id.InkedTitleText);
        lastPlayedTitle = itemView.findViewById(R.id.LastTitleText);

        winText = itemView.findViewById(R.id.WinText);
        lossText = itemView.findViewById(R.id.LossText);
        inkedText = itemView.findViewById(R.id.InkedText);
        lastPlayedText = itemView.findViewById(R.id.LastText);
    }

    public void manageHolder(){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");

        card.setClipToOutline(true);

        inkTitle.setTypeface(fontTitle);
        lastPlayedTitle.setTypeface(fontTitle);

        winText.setTypeface(font);
        lossText.setTypeface(font);
        inkedText.setTypeface(font);
        lastPlayedText.setTypeface(font);
    }
}
