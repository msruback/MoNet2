package com.mattrubacky.monet2.adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class BattleViewHolder extends RecyclerView.ViewHolder{
    public RelativeLayout item,fesMode,alpha,bravo,spots;
    public ImageView weapon,type;
    public TextView mode,map,result;


    public BattleViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_weapon, parent, false));

        item = (RelativeLayout) itemView.findViewById(R.id.item);

        fesMode = (RelativeLayout) itemView.findViewById(R.id.FesMode);
        alpha = (RelativeLayout) itemView.findViewById(R.id.Alpha);
        bravo = (RelativeLayout) itemView.findViewById(R.id.Bravo);
        spots = (RelativeLayout) itemView.findViewById(R.id.Spots);

        mode = (TextView) itemView.findViewById(R.id.mode);
        map = (TextView) itemView.findViewById(R.id.map);
        result = (TextView) itemView.findViewById(R.id.result);

        weapon = (ImageView) itemView.findViewById(R.id.weapon);
        type = (ImageView) itemView.findViewById(R.id.Type);
    }

}
