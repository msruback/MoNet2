package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class NoStatViewHolder extends RecyclerView.ViewHolder{

    public RelativeLayout card;
    public TextView noStats;
    public ImageView product;
    private Context context;

    public NoStatViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_pager_list, parent, false));
        this.context = context;
        card = itemView.findViewById(R.id.noStats);
        noStats = itemView.findViewById(R.id.NoStatsText);
        product = itemView.findViewById(R.id.product);
    }
    public void manageHolder(){
        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(), "Paintball.otf");
        card.setClipToOutline(true);
        noStats.setTypeface(fontTitle);
    }
}
