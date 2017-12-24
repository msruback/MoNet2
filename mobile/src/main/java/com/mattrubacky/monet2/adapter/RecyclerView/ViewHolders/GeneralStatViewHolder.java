package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class GeneralStatViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card,wins,losses;
    public TextView inkTitle,lastPlayedTitle;
    public TextView winText,lossText,inkedText,lastPlayedText;
    private Context context;

    public GeneralStatViewHolder(LayoutInflater inflater, ViewGroup parent,Context context) {
        super(inflater.inflate(R.layout.item_general_stats, parent, false));

        this.context = context;

        card = (RelativeLayout) itemView.findViewById(R.id.card);

        inkTitle = (TextView) itemView.findViewById(R.id.InkedTitleText);
        lastPlayedTitle = (TextView) itemView.findViewById(R.id.LastTitleText);

        winText = (TextView) itemView.findViewById(R.id.WinText);
        lossText = (TextView) itemView.findViewById(R.id.LossText);
        inkedText = (TextView) itemView.findViewById(R.id.InkedText);
        lastPlayedText = (TextView) itemView.findViewById(R.id.LastText);
    }

    public void manageHolder(){

    }
}
