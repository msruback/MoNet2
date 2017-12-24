package com.mattrubacky.monet2.adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class WeaponViewHolder extends RecyclerView.ViewHolder{

    public ImageView weapon;
    public TextView name;

    public WeaponViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_weapon, parent, false));

        weapon = (ImageView) itemView.findViewById(R.id.WeaponImage);
        name = (TextView) itemView.findViewById(R.id.Name);
    }
}
